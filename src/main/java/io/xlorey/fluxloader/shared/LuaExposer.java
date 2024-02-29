package io.xlorey.fluxloader.shared;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Deknil
 * GitHub: <a href="https://github.com/Deknil">https://github.com/Deknil</a>
 * Date: 27.02.2024
 * Description: A utility class for exposing classes and global objects for use in Lua scripts.
 * <p> FluxLoader © 2024. All rights reserved. </p>
 */
@UtilityClass
public class LuaExposer {
    /**
     * A collection of classes that should be exhibited.
     */
    private static final Set<Class<?>> exposedClasses = new HashSet<>();

    /**
     * A collection of global objects that should be exposed.
     */
    private static final Set<Object> exposedGlobalObjects = new HashSet<>();

    /**
     * Method for exposing the class.
     * @param clazz The class to expose.
     */
    public static synchronized void addExposedClass(Class<?> clazz) {
        exposedClasses.add(clazz);
    }

    /**
     * Method for exposing a global object, which contain methods with annotation {@link se.krka.kahlua.integration.annotations.LuaMethod}.
     * @param globalObject The global object to expose.
     */
    public static synchronized void addExposedGlobalObject(Object globalObject) {
        exposedGlobalObjects.add(globalObject);
    }

    /**
     * Method for getting a collection of exposed classes.
     * @return A collection of exposed classes.
     */
    public static synchronized Set<Class<?>> getExposedClasses() {
        return Collections.unmodifiableSet(exposedClasses);
    }

    /**
     * Method for getting a collection of exposed global objects.
     * @return A collection of exposed global objects.
     */
    public static synchronized Set<Object> getExposedGlobalObjects() {
        return Collections.unmodifiableSet(exposedGlobalObjects);
    }

    /**
     * Method for removing a class from the collection of exposed classes.
     * @param clazz The class to delete.
     */
    public static synchronized void removeExposedClass(Class<?> clazz) {
        exposedClasses.remove(clazz);
    }

    /**
     * Method for removing a global object from the collection of exposed global objects.
     * @param globalObject The global object to delete.
     */
    public static synchronized void removeExposedGlobalObject(Object globalObject) {
        exposedGlobalObjects.remove(globalObject);
    }
}