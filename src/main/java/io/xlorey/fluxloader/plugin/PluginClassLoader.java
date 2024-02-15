package io.xlorey.fluxloader.plugin;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: A custom class loader for loading plugin classes.
 *              Extends {@link URLClassLoader} to provide functionality for loading classes from a URL, such as a JAR file.
 *              <p>This class loader is specifically designed to work with plugins, allowing classes to be dynamically loaded
 *              and accessed during runtime. It also includes a method to check if a class has already been loaded, which helps
 *              prevent duplicate loading of the same class.</p>
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class PluginClassLoader extends URLClassLoader {
    private final Map<String, Class<?>> classes = new HashMap<>();

    /**
     * Constructs a new PluginClassLoader for the specified URLs using the specified parent class loader.
     * @param urls   The URLs from which to load classes and resources.
     */
    public PluginClassLoader(URL[] urls) {
        super(urls);
    }

    /**
     * Finds a loaded class by name.
     * This method is used to check if a class has already been loaded by this class loader.
     *
     * @param name The fully qualified name of the desired class.
     * @return The Class object, or {@code null} if the class has not been loaded.
     */
    public Class<?> findLoaded(String name) {
        return super.findLoadedClass(name);
    }

    /**
     * Finds and loads the class with the specified name from the URL search
     * path. Any URLs referring to JAR files are loaded and opened as needed
     * until the class is found.
     * @param     name the name of the class
     * @return    the resulting class
     * @throws    ClassNotFoundException if the class could not be found,
     *            or if the loader is closed.
     * @throws    NullPointerException if {@code name} is {@code null}.
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return findClass(name, true);
    }

    /**
     * Finds a class by name, optionally performing a global search.
     * @param name           The fully qualified name of the desired class.
     * @param isGlobalSearch Indicates whether to perform a global search for the class.
     *                       If true, attempts to retrieve the class from PluginManager if not found locally.
     * @return The Class object corresponding to the specified name.
     * @throws ClassNotFoundException If the class could not be found.
     */
    protected Class<?> findClass(String name, boolean isGlobalSearch) throws ClassNotFoundException {
        Class<?> result = classes.get(name);

        if (result == null) {
            if (isGlobalSearch) {
                result = PluginRegistry.getClassByName(name);
            }

            if (result == null) {
                result = super.findClass(name);

                if (result != null) {
                    PluginRegistry.addClassInCache(name, result);
                }
            }

            classes.put(name, result);
        }

        return result;
    }

    /**
     * Retrieves the set of loaded class names.
     * @return A set containing the names of the loaded classes.
     */
    public Set<String> getClasses() {
        return classes.keySet();
    }
}