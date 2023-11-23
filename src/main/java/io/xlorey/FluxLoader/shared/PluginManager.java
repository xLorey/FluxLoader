package io.xlorey.FluxLoader.shared;

import io.xlorey.FluxLoader.plugin.FluxPlugin;
import io.xlorey.FluxLoader.utils.Constants;
import io.xlorey.FluxLoader.utils.Logger;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Plugin (mod) management system
 */
public class PluginManager {
    /**
     * All loaded plugins
     */
    private static HashMap<String, FluxPlugin> plugins = new HashMap<>();

    /**
     * Plugin dependencies
     */
    private static HashMap<String, List<String>> dependencies = new HashMap<>();

    /**
     * Manager initialization
     * @exception Exception error when initializing the plugin management system
     */
    public static void init() throws Exception {
        Logger.print("Initializing the plugin management system...");

        checkPluginFolder();

        loadPlugins();
    }

    /**
     * Search and load plugins
     * @exception Exception error loading plugins
     */
    public static void loadPlugins() throws Exception {
        File pluginsDir = new File(Constants.PLUGINS_FOLDER_NAME);
        File[] fileList = pluginsDir.listFiles((dir, name) -> name.endsWith(".jar"));

        if (fileList != null) {
            for (File file : fileList) {
                try {
                    URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
                    JarFile jarFile = new JarFile(file);
                    String mainClassName = jarFile.getManifest().getMainAttributes().getValue("Main-Class");
                    Class<?> clazz = Class.forName(mainClassName, true, classLoader);

                    if (FluxPlugin.class.isAssignableFrom(clazz)) {
                        FluxPlugin plugin = (FluxPlugin) clazz.getDeclaredConstructor().newInstance();
                        String pluginId = plugin.getPluginId();

                        if (plugins.containsKey(pluginId)) {
                            Logger.print("Plugin with ID " + pluginId + " already loaded. Skipping...");
                            continue;
                        }

                        plugins.put(pluginId, plugin);
                        dependencies.put(pluginId, Arrays.asList(plugin.getPluginDependencies()));
                    }
                } catch (Exception e) {
                    Logger.print("Error scanning plugin: " + file.getName());
                    e.printStackTrace();
                }
            }
        }

        List<String> sortedPluginIds = topologicalSort(dependencies);
        for (String pluginId : sortedPluginIds) {
            FluxPlugin plugin = plugins.get(pluginId);
            Logger.print(String.format("Loading plugin '%s' (ID: '%s', Version: %s)", plugin.getPluginName(), plugin.getPluginId(), plugin.getPluginVersion()));
            plugin.onInitialize();
        }
    }

    /**
     * Sorting dependencies so that each one is downloaded in order
     * @param dependencies list of dependency plugin IDs
     * @return sorted list of dependencies
     * @throws Exception error while sorting the list
     */
    private static List<String> topologicalSort(Map<String, List<String>> dependencies) throws Exception {
        Set<String> visited = new HashSet<>();
        Set<String> stack = new HashSet<>();
        List<String> sortedList = new ArrayList<>();

        for (String pluginId : dependencies.keySet()) {
            if (!visited.contains(pluginId)) {
                topologicalSortUtil(pluginId, dependencies, visited, stack, sortedList);
            }
        }
        return sortedList;
    }

    /**
     * helper method for topological sorting of plugins
     *
     * @param pluginId     ID of the current plugin that is being considered for sorting.
     * @param dependencies Plugin dependency map, where the key is the plugin ID and the value is a list of IDs
     *                     dependent plugins.
     * @param visited      A set of plugin IDs that have already been visited during the sorting process.
     * @param stack        Set of plugin IDs that are currently in the processed path
     *                     (used to detect circular dependencies).
     * @param sortedList   A list to which sorted plugins (plugin IDs) are added. After finishing
     *                     sorted, this list contains plugins in the order in which they should be loaded.
     * @throws Exception   An exception is thrown if a circular dependency is detected between plugins.
     */
    private static void topologicalSortUtil(String pluginId, Map<String, List<String>> dependencies,
                                            Set<String> visited, Set<String> stack,
                                            List<String> sortedList) throws Exception {
        if (stack.contains(pluginId)) {
            throw new Exception(String.format("Detected cyclic dependency involving '%s'", pluginId));
        }

        if (!visited.contains(pluginId)) {
            stack.add(pluginId);
            visited.add(pluginId);

            List<String> deps = dependencies.get(pluginId);
            if (deps != null) {
                for (String depId : deps) {
                    topologicalSortUtil(depId, dependencies, visited, stack, sortedList);
                }
            }

            stack.remove(pluginId);
            sortedList.add(pluginId);
        }
    }

    /**
     * Checking the presence of a folder for plugins. In its absence - creation
     */
    private static void checkPluginFolder() {
        Logger.print("Checking for plugins folder...");

        File folder = new File(Constants.PLUGINS_FOLDER_NAME);

        if (!folder.exists()) {
            Logger.print("There is no plugin folder. Creation...");
            if (folder.mkdir()) {
                Logger.print("The folder was created successfully.");
            } else {
                Logger.print("Failed to create folder.");
            }
        } else {
            Logger.print("Plugins folder found!");
        }
    }
}
