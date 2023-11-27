package io.xlorey.FluxLoader.utils.patch;

import io.xlorey.FluxLoader.utils.Constants;
import io.xlorey.FluxLoader.utils.Logger;
import lombok.experimental.UtilityClass;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A set of tools for embedding into game files
 */
@UtilityClass
public class PatchTools {
    /**
     * A map of classes for later saving changes
     */
    private static final Map<String, ClassNode> classNodeMap = new HashMap<>();

    /**
     * Checks whether the specified annotation is present in the .class file.
     * @param filePath Relative path to the .class file to check.
     * @exception Exception in cases where an annotation is detected
     */
    public static void checkAnnotation(String filePath) throws Exception {
        Path currentPath = Paths.get("").toAbsolutePath();
        Path classPath = Paths.get(currentPath.toString(), Constants.PATH_TO_GAME_CLASS_FOLDER, filePath);

        try (FileInputStream fileInputStream = new FileInputStream(classPath.toString())) {
            ClassReader reader = new ClassReader(fileInputStream);

            reader.accept(new ClassVisitor(Opcodes.ASM9) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                    return new MethodVisitor(Opcodes.ASM9, mv) {
                        @Override
                        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                            if (descriptor.equals("Lio/xlorey/annotations/Injected;")) {
                                throw new IllegalStateException(String.format("Annotation 'Injected' found in the file '%s'", filePath));
                            }
                            return super.visitAnnotation(descriptor, visible);
                        }
                    };
                }
            }, 0);
        }
    }

    /**
     * Adding the Injected annotation to a mutable method
     * @param classNode     Node of all bytecode classes
     * @param methodName    Name of the method being modified
     */
    private static void addInjectAnnotation(ClassNode classNode, String methodName) {
        for (MethodNode method : classNode.methods) {
            if (method.name.equals(methodName)) {
                if (method.visibleAnnotations != null) {
                    boolean alreadyAnnotated = method.visibleAnnotations.stream()
                            .anyMatch(ann -> ann.desc.equals("Lio/xlorey/annotations/Injected;"));
                    if (alreadyAnnotated) {
                        continue;
                    }
                } else {
                    method.visibleAnnotations = new LinkedList<>();
                }

                AnnotationNode annotationNode = new AnnotationNode("Lio/xlorey/annotations/Injected;");
                method.visibleAnnotations.add(annotationNode);
            }
        }
    }

    /**
     * Method for injecting annotations and making changes to a given class method.
     * Initially, it reads the bytecode of the class and converts it into a ClassNode for further processing.
     * Then adds an annotation to the specified method and uses the provided Consumer action
     * to make changes to this method. After making all changes, the method writes the changed bytecode
     * back to .class file.
     *
     * @param className     The name of the class to be injected into.
     * @param methodName    The name of the method to which the annotation will be added and changes made.
     * @param isStatic      Whether the method you are looking for is static or not
     * @param modifyMethod  The action that will be applied to the MethodNode to make changes to the method.
     *                      This action takes one parameter - MethodNode, representing the method in which
     *                      subject to change.
     * @exception Exception in cases of unsuccessful injection
     */
    public static void injectIntoClass(String className, String methodName, boolean isStatic, Consumer<MethodNode> modifyMethod) throws Exception {
        Logger.print(String.format("Injection into a game file '%s' in method: '%s'", className, methodName));

        checkAnnotation(className);

        if (!className.startsWith("zombie"))
            className = "zombie/" + className;

        if (className.endsWith(".class"))
            className = className.replace(".class", "");

        try {
            ClassNode classNode = classNodeMap.getOrDefault(className, new ClassNode());
            if (!classNodeMap.containsKey(className)) {
                ClassReader classReader = new ClassReader(className);
                classReader.accept(classNode, 0);
            }

            for (MethodNode method : classNode.methods) {
                if (method.name.equals(methodName) && Modifier.isStatic(method.access) == isStatic) {
                    addInjectAnnotation(classNode, methodName);
                    modifyMethod.accept(method);
                }
            }

            classNodeMap.put(className, classNode);

        } catch (IOException e) {
            Logger.print(String.format("An error occurred during injection into '%s'", className));
            throw e;
        }
    }

    /**
     * Injecting a custom event call at the beginning of the target method and passing its arguments
     * @param className     The name of the class to be injected into.
     * @param methodName    The name of the method to which the annotation will be added and changes made.
     * @param isStatic      Whether the method you are looking for is static or not
     * @param eventName     Custom event name
     * @throws Exception    in cases of unsuccessful injection
     */
    public static void injectEventInvoker(String className, String methodName, String eventName, boolean isStatic) throws Exception {
        injectIntoClass(className, methodName, isStatic, method -> {
            Type[] argumentTypes = Type.getArgumentTypes(method.desc);
            InsnList eventInvoker = createEventInvokerInsnList(eventName, argumentTypes, isStatic);
            method.instructions.insertBefore(method.instructions.getFirst(), eventInvoker);
        });
    }

    /**
     * Inserts a custom event call at the end of the target method, passing its arguments.
     * @param className     The name of the class to be injected into.
     * @param methodName    The name of the method to which the annotation will be added and changes made.
     * @param isStatic      Whether the method you are looking for is static or not
     * @param eventName     Custom event name
     * @throws Exception    in cases of unsuccessful injection
     */
    public static void injectEventInvokerAtEnd(String className, String methodName, String eventName, boolean isStatic) throws Exception {
        injectIntoClass(className, methodName, isStatic, method -> {
            Type[] argumentTypes = Type.getArgumentTypes(method.desc);
            InsnList eventInvoker = createEventInvokerInsnList(eventName, argumentTypes, isStatic);

            // Получаем последнюю инструкцию (обычно RETURN)
            AbstractInsnNode lastInsn = method.instructions.getLast();

            // Вставляем перед RETURN. Если в методе нет RETURN, добавляем в конец.
            if (lastInsn.getOpcode() >= Opcodes.IRETURN && lastInsn.getOpcode() <= Opcodes.RETURN) {
                method.instructions.insertBefore(lastInsn, eventInvoker);
            } else {
                method.instructions.add(eventInvoker);
            }
        });
    }


    /**
     * Creates a list of instructions for the event invoker.
     * This method generates instructions for dynamically injecting event calls
     * into class methods based on their signatures.
     *
     * @param eventName name of the event that will be raised
     * @param argumentTypes An array of argument types of the method into which the injection occurs
     * @param isStatic      Whether the method you are looking for is static or not
     * @return InsnList List of ASM statements that, when inserted into a class method
     */
    public static InsnList createEventInvokerInsnList(String eventName, Type[] argumentTypes, boolean isStatic) {
        InsnList eventInvoker = new InsnList();

        eventInvoker.add(new LdcInsnNode(eventName));
        eventInvoker.add(new IntInsnNode(Opcodes.BIPUSH, argumentTypes.length));
        eventInvoker.add(new TypeInsnNode(Opcodes.ANEWARRAY, "java/lang/Object"));

        int startIdx = isStatic ? 0 : 1;
        for (int i = 0; i < argumentTypes.length; i++) {
            eventInvoker.add(new InsnNode(Opcodes.DUP));
            eventInvoker.add(new IntInsnNode(Opcodes.BIPUSH, i));

            if (argumentTypes[i].getSort() == Type.OBJECT || argumentTypes[i].getSort() == Type.ARRAY) {
                eventInvoker.add(new VarInsnNode(Opcodes.ALOAD, i + startIdx));
            } else {
                wrapPrimitiveType(eventInvoker, argumentTypes[i], i + startIdx);
            }

            eventInvoker.add(new InsnNode(Opcodes.AASTORE));
        }

        eventInvoker.add(new MethodInsnNode(
                Opcodes.INVOKESTATIC,
                "io/xlorey/FluxLoader/shared/EventManager",
                "invokeEvent",
                "(Ljava/lang/String;[Ljava/lang/Object;)V",
                false));

        return eventInvoker;
    }

    /**
     * Wrapping primitive types into objects
     * @param instructions the injector instructions
     * @param type argument type
     * @param index index
     */
    private static void wrapPrimitiveType(InsnList instructions, Type type, int index) {
        switch (type.getSort()) {
            case Type.BOOLEAN -> {
                instructions.add(new VarInsnNode(Opcodes.ILOAD, index));
                instructions.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "java/lang/Boolean",
                        "valueOf",
                        "(Z)Ljava/lang/Boolean;",
                        false));
            }
            case Type.CHAR -> {
                instructions.add(new VarInsnNode(Opcodes.ILOAD, index));
                instructions.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "java/lang/Character",
                        "valueOf",
                        "(C)Ljava/lang/Character;",
                        false));
            }
            case Type.BYTE -> {
                instructions.add(new VarInsnNode(Opcodes.ILOAD, index));
                instructions.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "java/lang/Byte",
                        "valueOf",
                        "(B)Ljava/lang/Byte;",
                        false));
            }
            case Type.SHORT -> {
                instructions.add(new VarInsnNode(Opcodes.ILOAD, index));
                instructions.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "java/lang/Short",
                        "valueOf",
                        "(S)Ljava/lang/Short;",
                        false));
            }
            case Type.INT -> {
                instructions.add(new VarInsnNode(Opcodes.ILOAD, index));
                instructions.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "java/lang/Integer",
                        "valueOf",
                        "(I)Ljava/lang/Integer;",
                        false));
            }
            case Type.FLOAT -> {
                instructions.add(new VarInsnNode(Opcodes.FLOAD, index));
                instructions.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "java/lang/Float",
                        "valueOf",
                        "(F)Ljava/lang/Float;",
                        false));
            }
            case Type.LONG -> {
                instructions.add(new VarInsnNode(Opcodes.LLOAD, index));
                instructions.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "java/lang/Long",
                        "valueOf",
                        "(J)Ljava/lang/Long;",
                        false));
            }
            case Type.DOUBLE -> {
                instructions.add(new VarInsnNode(Opcodes.DLOAD, index));
                instructions.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "java/lang/Double",
                        "valueOf",
                        "(D)Ljava/lang/Double;",
                        false));
            }
        }
    }


    /**
     * Applying changes to .class files
     * @exception Exception in cases of unsuccessful saving of changes
     */
    public static void saveModifiedClasses() throws Exception {
        for (Map.Entry<String, ClassNode> entry : classNodeMap.entrySet()) {
            String className = entry.getKey();
            ClassNode classNode = entry.getValue();

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            byte[] modifiedClass = classWriter.toByteArray();

            try (FileOutputStream fos = new FileOutputStream(className + ".class")) {
                fos.write(modifiedClass);
            } catch (IOException e) {
                Logger.print(String.format("An error occurred while writing the modified class '%s'", className));
                throw e;
            }
        }
    }
}
