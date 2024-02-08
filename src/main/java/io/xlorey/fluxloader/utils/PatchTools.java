package io.xlorey.fluxloader.utils;

import io.xlorey.fluxloader.Installer;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.annotation.Annotation;
import lombok.experimental.UtilityClass;

import java.util.function.BiConsumer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: A set of tools for embedding into game files
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@UtilityClass
public class PatchTools {
    /**
     * Patches a specified method within a given class by first adding an annotation to the method
     * and then applying custom modifications through a provided BiConsumer 'CtClass, CtMethod'. This method
     * leverages Javassist to introspect and modify class bytecode, enabling dynamic alterations such
     * as injecting additional behavior or marking methods programmatically at runtime. This approach
     * is useful for conditional logic based on method annotations or for dynamically enhancing classes
     * without altering the source code.
     * @param className     The fully qualified name of the class containing the method to be patched.
     * @param methodName    The name of the method within the class to apply the patch to.
     * @param methodModifier A BiConsumer'CtClass, CtMethod' that contains the logic for modifying the method.
     *                       This consumer is applied after the annotation is added, allowing for
     *                       further customization of the method's behavior.
     */
    public static void patchMethod(String className, String methodName, BiConsumer<CtClass, CtMethod> methodModifier) {
        Logger.print(String.format("Attempt to patch a class '%s' in method: '%s'", className, methodName));

        try {
            CtClass ctClass = getCtClass(className);
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName);

            if (ctClass.isFrozen()) {
                ctClass.defrost();
            }

            // Adding an annotation to a method
            addAnnotationForMethod(ctClass, ctMethod, "io.xlorey.fluxloader.annotations.Modified");

            // Applying a custom modification to a method
            methodModifier.accept(ctClass, ctMethod);

            // Saving a modified class
            ctClass.writeFile(Installer.getClassPathFolder());

            // Detach a class to free up memory
            ctClass.detach();
        } catch (Exception e) {
            Logger.print(String.format("An error occurred while patching class '%s'!", className));
            e.printStackTrace();
        }
    }

    /**
     * Patches a specified method within a given class by first adding an annotation to the method
     * and then applying custom modifications through a provided BiConsumer 'CtClass, CtMethod'. This method
     * leverages Javassist to introspect and modify class bytecode, enabling dynamic alterations such
     * as injecting additional behavior or marking methods programmatically at runtime. This approach
     * is useful for conditional logic based on method annotations or for dynamically enhancing classes
     * without altering the source code.
     * @param className name of the class in which the patching method is located
     * @param methodName name of the method to be patched
     * @param methodSignature the method signature in a format suitable for searching for method overloads (e.g. "int, String")
     * @param methodModifier function used to modify the found method
     */
    public static void patchMethod(String className, String methodName, String methodSignature, BiConsumer<CtClass, CtMethod> methodModifier) {
        String[] argsTypes = methodSignature.split(",");
        Logger.print(String.format("Attempt to patch a class '%s' in method: '%s(%s args)'", className, methodName, argsTypes.length));

        try {
            CtClass ctClass = getCtClass(className);
            CtMethod ctMethod = ctClass.getDeclaredMethod(methodName, getMethodParameterTypes(ctClass.getClassPool(), methodSignature));

            if (ctClass.isFrozen()) {
                ctClass.defrost();
            }

            // Adding an annotation to a method
            addAnnotationForMethod(ctClass, ctMethod, "io.xlorey.fluxloader.annotations.Modified");

            // Applying a custom modification to a method
            methodModifier.accept(ctClass, ctMethod);

            // Saving a modified class
            ctClass.writeFile(Installer.getClassPathFolder());

            // Detach a class to free up memory
            ctClass.detach();
        } catch (Exception e) {
            Logger.print(String.format("An error occurred while patching class '%s'!", className));
            e.printStackTrace();
        }
    }

    /**
     * Retrieving the types of method parameters based on its signature.
     * @param classPool the class pool used to load classes
     * @param methodSignature the method signature in a format containing the parameter types (e.g. "int, String")
     * @return an array of CtClass objects representing the method parameter types
     * @throws NotFoundException if any of the parameter types cannot be found in the class pool
     */
    private static CtClass[] getMethodParameterTypes(ClassPool classPool, String methodSignature) throws NotFoundException {
        String[] argsType = methodSignature.split(",");
        CtClass[] parameterTypes = new CtClass[argsType.length];
        for (int i = 0; i < argsType.length; i++) {
            parameterTypes[i] = classPool.get(argsType[i].trim());
        }
        return parameterTypes;
    }

    /**
     * Retrieves a CtClass object for a given class name using Javassist. This method is used to load
     * a class from the specified class name, making it available for further manipulations such as
     * adding annotations, modifying methods, etc. The method configures the ClassPool to include
     * the classpath of the installer, ensuring that the class is loaded from the correct location.
     * @param className The fully qualified name of the class to be loaded.
     * @return A CtClass object representing the loaded class.
     * @throws NotFoundException If the class with the specified name cannot be found.
     */
    private static CtClass getCtClass(String className) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        pool.appendClassPath(Installer.getClassPathFolder());
        return pool.get(className);
    }

    /**
     * Adds a specified annotation to a given method within a class using Javassist. This method allows
     * dynamic modification of a class by programmatically adding annotations to its methods, facilitating
     * runtime modifications and marking for various purposes such as tracking changes or indicating
     * special processing requirements.
     * @param cc            The CtClass object representing the class to which the method belongs.
     * @param method        The CtMethod object representing the method to be annotated.
     * @param annotationName The fully qualified name of the annotation to add to the method.
     */
    private static void addAnnotationForMethod(CtClass cc, CtMethod method, String annotationName) {
        try {
            AnnotationsAttribute attr = new AnnotationsAttribute(cc.getClassFile().getConstPool(), AnnotationsAttribute.visibleTag);
            Annotation annotation = new Annotation(annotationName, cc.getClassFile().getConstPool());
            attr.addAnnotation(annotation);
            method.getMethodInfo().addAttribute(attr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a specific method in a given class has been annotated with a specified annotation.
     * This method uses Javassist to introspect the bytecode of the class and identify if the given
     * annotation is present on the specified method. It is useful for runtime analysis and conditional
     * logic based on annotation presence.
     * @param className      the fully qualified name of the class containing the method to check.
     * @param methodName     the name of the method to check for the annotation.
     * @param annotationName the fully qualified name of the annotation to look for.
     * @return true if the specified method has the specified annotation; false otherwise.
     */
    public static boolean isMethodHasAnnotation(String className, String methodName, String annotationName) {
        try {
            CtClass cc = getCtClass(className);

            CtMethod method = cc.getDeclaredMethod(methodName); // Getting a method by name
            MethodInfo methodInfo = method.getMethodInfo();

            // Getting a method annotation attribute
            AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
            if (annotationsAttribute == null) {
                return false; // No annotations available
            }

            // Checking the presence of an annotation
            for (Annotation annotation : annotationsAttribute.getAnnotations()) {
                if (annotation.getTypeName().equals(annotationName)) {
                    return true;
                }
            }

            cc.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Annotation not found
    }

    /**
     * Checks if any method within a specified class is annotated with a specified annotation. This method
     * iterates through all declared methods of the class, inspecting each for the presence of the specified
     * annotation. It utilizes Javassist for bytecode introspection, providing a means to dynamically assess
     * annotation usage in a class. This can be particularly useful for identifying if any method within a class
     * participates in specific behaviors or configurations denoted by the annotation.
     * @param className      the fully qualified name of the class to inspect.
     * @param annotationName the fully qualified name of the annotation to look for across all methods.
     * @return true if at least one method in the class has the specified annotation; false otherwise.
     */
    public boolean isClassHasAnnotationInMethods(String className, String annotationName) {
        try {
            CtClass cc = getCtClass(className);

            for (CtMethod method : cc.getDeclaredMethods()) { // Iterate over all methods of a class
                MethodInfo methodInfo = method.getMethodInfo();
                AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute) methodInfo.getAttribute(AnnotationsAttribute.visibleTag);
                if (annotationsAttribute != null) {
                    for (Annotation annotation : annotationsAttribute.getAnnotations()) {
                        if (annotation.getTypeName().equals(annotationName)) {
                            cc.detach();
                            return true; // Annotation found
                        }
                    }
                }
            }

            cc.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Annotation not found in class
    }
}
