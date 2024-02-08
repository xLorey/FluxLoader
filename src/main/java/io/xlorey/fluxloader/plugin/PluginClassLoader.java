package io.xlorey.fluxloader.plugin;

import java.net.URL;
import java.net.URLClassLoader;

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
    /**
     * Constructs a new PluginClassLoader for the specified URLs using the specified parent class loader.
     * @param urls   The URLs from which to load classes and resources.
     * @param parent The parent class loader for delegation.
     */
    public PluginClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    /**
     * Finds a loaded class by name.
     * This method is used to check if a class has already been loaded by this class loader.
     * @param name The fully qualified name of the desired class.
     * @return The Class object, or {@code null} if the class has not been loaded.
     */
    public Class<?> findLoaded(String name) {
        return super.findLoadedClass(name);
    }
}