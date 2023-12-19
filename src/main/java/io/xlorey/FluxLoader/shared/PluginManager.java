package io.xlorey.FluxLoader.shared;

import io.xlorey.FluxLoader.plugin.Plugin;
import io.xlorey.FluxLoader.plugin.PluginInfo;
import io.xlorey.FluxLoader.utils.Constants;
import io.xlorey.FluxLoader.utils.Logger;
import io.xlorey.FluxLoader.utils.VersionChecker;
import lombok.experimental.UtilityClass;
import zombie.core.Core;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Plugin (mod) management system
 */
@UtilityClass
public class PluginManager {
    /**
     * All loaded information about plugins
     * Key: plugin file
     * Value: information about the plugin
     */
    private static final HashMap<File, PluginInfo> pluginsInfoRegistry = new HashMap<>();

    /**
     * Register of all loaded client plugins
     * Key: plugin id
     * Value: plugin instance
     */
    private static final HashMap<String, Plugin> clientPluginsRegistry = new HashMap<>();

    /**
     * Register of all loaded server plugins
     * Key: plugin id
     * Value: plugin instance
     */
    private static final HashMap<String, Plugin> serverPluginsRegistry = new HashMap<>();

    /**
     * Retrieves the list of loaded client plugins
     * @return A HashMap containing information about loaded client plugins, where the key is plugin id
     *         and the value is the corresponding Plugin instance.
     */
    public static HashMap<String, Plugin> getLoadedClientPlugins() {
        return clientPluginsRegistry;
    }

    /**
     * Retrieves the list of loaded server plugins
     * @return A HashMap containing information about loaded server plugins, where the key is plugin id
     *         and the value is the corresponding Plugin instance.
     */
    public static HashMap<String, Plugin> getLoadedServerPlugins() {
        return serverPluginsRegistry;
    }

    /**
     * Starts the execution of all plugins specified in the past registry.
     * This method iterates through all the plugins in the provided registry and calls their onExecute method.
     * During the execution of each plugin, messages about the start and successful completion are logged.
     * If exceptions occur during plugin execution, the method also logs the corresponding errors.
     * @param pluginsRegistry A registry of plugins, each of which will be executed.
     */
    public static void executePlugins(HashMap<String, Plugin> pluginsRegistry) {
        for (Map.Entry<String, Plugin> entry : pluginsRegistry.entrySet()) {
            String pluginId = entry.getKey();
            Plugin pluginInstance = entry.getValue();

            Logger.print(String.format("Plugin '%s' started trying to execute...", pluginId));

            try {
                pluginInstance.onExecute();
            } catch (Exception e) {
                Logger.print(String.format("Plugin '%s' failed to execute correctly due to: %s", pluginId, e));
            }

            Logger.print(String.format("Plugin '%s' has been successfully executed. All internal processes have been successfully launched.", pluginId));
        }
    }

    /**
     * Stops all plugins specified in the past registry.
     * This method iterates through all the plugins in the provided registry and calls their onTerminate method.
     * During the process of stopping each plugin, messages about the start and successful completion are logged.
     * If exceptions occur when stopping the plugin, the method also logs the corresponding errors.
     * @param pluginsRegistry A registry of plugins, each of which will be stopped.
     */
    public static void terminatePlugins(HashMap<String, Plugin> pluginsRegistry) {
        for (Map.Entry<String, Plugin> entry : pluginsRegistry.entrySet()) {
            String pluginId = entry.getKey();
            Plugin pluginInstance = entry.getValue();

            Logger.print(String.format("Plugin '%s' is starting to shut down...", pluginId));

            try {
                pluginInstance.onTerminate();
            } catch (Exception e) {
                Logger.print(String.format("Plugin '%s' failed to shut down correctly due to: %s", pluginId, e));
            }

            Logger.print(String.format("Plugin '%s' has been successfully terminated. All internal processes have been completed.", pluginId));
        }
    }

    /**
     * Loading plugins for the client
     * @throws IOException in cases of input/output problems
     */
    public static void loadPlugins() throws Exception {
        Logger.print("Loading plugins into the environment...");

        checkPluginFolder();

        /*
           Getting a list of plugins
         */
        ArrayList<File> pluginFiles = getPluginFiles();

        /*
            Searching for plugins in a directory
         */
        for (File plugin : pluginFiles) {
            PluginInfo pluginInfo = PluginInfo.getInfoFromFile(plugin);

            if (pluginInfo == null) {
                Logger.print(String.format("No metadata found for potential plugin '%s'. Skipping...", plugin.getName()));
                continue;
            }
            pluginsInfoRegistry.put(plugin, pluginInfo);
        }

        /*
            Checking for dependency availability and versions
         */
        dependencyVerification();

        /*
            Sorting plugins by load order and Circular dependency check
         */
        ArrayList<File> sortedOrder = sortPluginsByLoadOrder();

        /*
            Loading the plugin
         */
        for (File plugin : sortedOrder) {
            PluginInfo pluginInfo = pluginsInfoRegistry.get(plugin);

            List<String> clientEntryPoints = pluginInfo.getEntrypoints().get("client");
            List<String> serverEntryPoints = pluginInfo.getEntrypoints().get("server");

            // Checking for empty entry points
            if (!isValidEntryPoints(clientEntryPoints, "client", pluginInfo) || !isValidEntryPoints(serverEntryPoints, "server", pluginInfo)) {
                continue;
            }

            ClassLoader commonClassLoader = PluginManager.class.getClassLoader();
            try (URLClassLoader classLoader = new URLClassLoader(new URL[]{plugin.toURI().toURL()}, commonClassLoader)) {
                loadEntryPoints(clientEntryPoints, clientPluginsRegistry, pluginInfo, classLoader);
                loadEntryPoints(serverEntryPoints, serverPluginsRegistry, pluginInfo, classLoader);
            } catch (Exception e){
                throw new Exception(String.format("Failed to load '%s': %s", pluginInfo.getId(), e.getMessage()));
            }
        }
    }

    /**
     * Loads plugin input points using the provided class loader.
     * This method is designed to load and initialize plugin classes based on lists of input points.
     * For each input point in the list, the method tries to load the corresponding class, create an instance
     * plugin and add it to the specified plugin registry. In case of errors during class loading process
     * or creating instances, the method throws an exception.
     * @param entryPoints List of string identifiers of the plugin classes' entry points.
     * @param targetRegistry The registry to which loaded plugin instances should be added.
     * @param pluginInfo Plugin information used to log and associate instances with plugin data.
     * @param classLoader {@link URLClassLoader} for loading plugin classes.
     * @throws Exception If errors occur while loading classes or creating instances.
     */
    private static void loadEntryPoints(List<String> entryPoints, HashMap<String, Plugin> targetRegistry, PluginInfo pluginInfo, URLClassLoader classLoader) throws Exception {
        for (String entryPoint : entryPoints) {
            Class<?> pluginClass = Class.forName(entryPoint, true, classLoader);
            Plugin pluginInstance = (Plugin) pluginClass.getDeclaredConstructor().newInstance();

            pluginInstance.setPluginInfo(pluginInfo);

            Logger.print(String.format("Plugin '%s'(ID: '%s', Version: %s) detected. Initialization attempt...",
                    pluginInfo.getName(), pluginInfo.getId(), pluginInfo.getVersion()));

            pluginInstance.onInitialize();

            if (!targetRegistry.containsKey(pluginInfo.getId())) {
                targetRegistry.put(pluginInfo.getId(), pluginInstance);
            }

            EventManager.subscribe(pluginInstance);
        }
    }

    /**
     * Checks the presence and non-empty list of input points for the plugin.
     * This method is used to check if the input points for the plugin are defined.
     * If the list of input points is empty or null, the method logs a message about loading skipped
     * plugin and returns false.
     *
     * @param entryPoints List of plugin entry points. Can be null.
     * @param type The type of input points (e.g. "client" or "server").
     * @param pluginInfo Plugin information used for logging.
     * @return true if the list of input points is not empty or null, false otherwise.
     */
    private static boolean isValidEntryPoints(List<String> entryPoints, String type, PluginInfo pluginInfo) {
        if (entryPoints == null || entryPoints.isEmpty()) {
            Logger.print(String.format("No %s entry points defined for plugin '%s' (ID: '%s', Version: %s). Skipping...",
                    type, pluginInfo.getName(), pluginInfo.getId(), pluginInfo.getVersion()));
            return false;
        }
        return true;
    }

    /**
     * Sorts plugins by load order using topological sorting.
     * This method takes into account the dependencies between plugins and arranges them in such a way
     * so that each plugin is loaded after all the plugins it depends on have been loaded.
     *
     * @return List of plugin files, sorted in download order.
     * @throws Exception in case a cyclic dependency or other error is detected.
     */
    private static ArrayList<File> sortPluginsByLoadOrder() throws Exception {
        HashMap<String, List<String>> graph = new HashMap<>();
        HashMap<String, Integer> state = new HashMap<>();
        ArrayList<String> sortedOrder = new ArrayList<>();
        HashMap<String, File> pluginFilesMap = new HashMap<>();

        // Initializing the graph and states
        for (Map.Entry<File, PluginInfo> entry : pluginsInfoRegistry.entrySet()) {
            String pluginId = entry.getValue().getId();
            pluginFilesMap.put(pluginId, entry.getKey());
            state.put(pluginId, 0); // 0 - not visited, 1 - on the stack, 2 - visited

            if (!pluginId.equals("project-zomboid") && !pluginId.equals("flux-loader")) {
                graph.putIfAbsent(pluginId, new ArrayList<>());

                for (String dependency : entry.getValue().getDependencies().keySet()) {
                    if (!dependency.equals("project-zomboid") && !dependency.equals("flux-loader")) {
                        graph.putIfAbsent(dependency, new ArrayList<>());
                        graph.get(dependency).add(pluginId);
                    }
                }
            }
        }

        // Topological sorting and checking for cyclic dependencies
        for (String pluginId : graph.keySet()) {
            if (state.get(pluginId) == 0 && topologicalSortDFS(pluginId, graph, state, sortedOrder)) {
                throw new Exception("Cyclic dependency detected: " + pluginId);
            }
        }

        // Convert to file list
        ArrayList<File> sortedPluginFiles = new ArrayList<>();
        for (String pluginId : sortedOrder) {
            sortedPluginFiles.add(pluginFilesMap.get(pluginId));
        }

        return sortedPluginFiles;
    }

    /**
     * Helps perform topological sorting using the DFS algorithm.
     * This method also checks for circular dependencies between plugins.
     *
     * @param current The current node being processed (plugin ID).
     * @param graph Dependency graph, where the key is the plugin identifier and the value is a list of dependencies.
     * @param state A dictionary to keep track of the state of each node during the traversal process.
     * @param sortedOrder A list to store the sorted order of nodes.
     * @return Returns `true` if no circular dependencies are found, `false` otherwise.
     */
    private static boolean topologicalSortDFS(String current, HashMap<String, List<String>> graph, HashMap<String, Integer> state, ArrayList<String> sortedOrder) {
        if (state.get(current) == 1) {
            return true; // Cyclic dependency detected
        }
        if (state.get(current) == 2) {
            return false; // Already visited
        }

        state.put(current, 1); // Mark the node as in the stack
        for (String neighbour : graph.getOrDefault(current, new ArrayList<>())) {
            if (topologicalSortDFS(neighbour, graph, state, sortedOrder)) {
                return true;
            }
        }
        state.put(current, 2); // Mark the node as visited
        sortedOrder.add(0, current);

        return false;
    }

    /**
     * Checking plugins for their dependencies and ensuring that their versions meet the requirements
     * @throws Exception in case the dependent plugin is not found among those found or its version does not meet the requirements
     */
    private static void dependencyVerification() throws Exception {
        for (Map.Entry<File, PluginInfo> entry : pluginsInfoRegistry.entrySet()) {
            Map<String, String> dependencies = entry.getValue().getDependencies();

            for (Map.Entry<String, String> depEntry : dependencies.entrySet()) {
                String depId = depEntry.getKey();
                String depVersion = depEntry.getValue();

                switch (depId) {
                    case "project-zomboid" -> {
                        if (!VersionChecker.isVersionCompatible(depVersion, Core.getInstance().getVersion())) {
                            throw new Exception(String.format("Incompatible game version for plugin id '%s'", entry.getValue().getId()));
                        }
                    }
                    case "flux-loader" -> {
                        if (!VersionChecker.isVersionCompatible(depVersion, Constants.FLUX_VERSION)) {
                            throw new Exception(String.format("Incompatible flux-loader version for plugin id '%s'", entry.getValue().getId()));
                        }
                    }
                    default -> {
                        // Checking the presence of the plugin in the directory
                        boolean hasPlugin = false;

                        for (Map.Entry<File, PluginInfo> checkEntry : pluginsInfoRegistry.entrySet()) {
                            String id = checkEntry.getValue().getId();
                            String version = checkEntry.getValue().getVersion();

                            if (depId.equals(id) && VersionChecker.isVersionCompatible(depVersion, version)){
                                hasPlugin = true;
                                break;
                            }
                        }

                        if (!hasPlugin) {
                            throw new Exception(String.format("Plugin '%s' does not have a dependent plugin '%s' or its version does not meet the requirements",
                                    entry.getValue().getId(),
                                    depId
                                    ));
                        }
                    }
                }
            }
        }
    }

    /**
     * Finds all JAR plugins in the specified directory.
     * @return List of JAR files.
     * @throws IOException in cases of input/output problems
     */
    public static ArrayList<File> getPluginFiles() throws IOException {
        ArrayList<File> jarFiles = new ArrayList<>();
        File dir = new File(Constants.PLUGINS_FOLDER_NAME);

        if (!dir.isDirectory()) {
            throw new IOException("Path is not a directory: " + dir.getPath());
        }

        File[] files = dir.listFiles((File pathname) -> pathname.isFile() && pathname.getName().endsWith(".jar"));
        if (files != null) {
            Collections.addAll(jarFiles, files);
        }

        return jarFiles;
    }

    /**
     * Checking for availability and creating a folder for plugins
     */
    private static void checkPluginFolder() {
        Logger.print("Checking for plugins folder...");

        File folder = new File(Constants.PLUGINS_FOLDER_NAME);

        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Logger.print("Failed to create folder...");
            }
        }
    }
}
