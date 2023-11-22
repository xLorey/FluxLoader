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
    private void addInjectAnnotation(ClassNode classNode, String methodName) {
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
     * Then adds an annotation to the specified method and uses the provided Consumer<MethodNode> action
     * to make changes to this method. After making all changes, the method writes the changed bytecode
     * back to .class file.
     *
     * @param className     The name of the class to be injected into.
     * @param methodName    The name of the method to which the annotation will be added and changes made.
     * @param isStatic      Whether the method you are looking for is static or not
     * @param modifyMethod  The action that will be applied to the MethodNode to make changes to the method.
     *                      This action takes one parameter - MethodNode, representing the method in which
     *                      subject to change.
     */
    public void injectIntoClass(String className, String methodName, boolean isStatic, Consumer<MethodNode> modifyMethod) throws Exception {
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
     * Applying changes to .class files
     */
    public void saveModifiedClasses() throws Exception {
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
