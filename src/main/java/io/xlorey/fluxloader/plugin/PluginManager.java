package io.xlorey.fluxloader.plugin;

import io.xlorey.fluxloader.interfaces.IControlsWidget;
import io.xlorey.fluxloader.utils.Constants;
import io.xlorey.fluxloader.utils.Logger;
import io.xlorey.fluxloader.utils.VersionChecker;
import lombok.experimental.UtilityClass;
import zombie.core.Core;
import zombie.core.textures.Texture;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Plugin (mod) management system
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@UtilityClass
public class PluginManager {

    /**
     * Loading plugins into the game context
     * @param isClient flag indicating whether loading occurs on the client side
     * @throws IOException in cases of input/output problems
     */
    public static void loadPlugins(boolean isClient) throws Exception {
        Logger.print("Loading plugins into the environment...");

        /*
        Getting a list of plugins, checking if the folder exists and creating it otherwise
         */
        ArrayList<File> folderPluginsFiles = getPluginFiles();

        /*
        List of all plugins with valid metadata
         */
        HashMap<File, Metadata> validPlugins = new HashMap<>();

        /*
        Plugin Metadata Validation
         */
        for (File plugin : folderPluginsFiles) {
            Metadata metadata = Metadata.getInfoFromFile(plugin);

            if (metadata == null) {
                Logger.print(String.format("No metadata found for potential plugin '%s'. Skipping...", plugin.getName()));
                continue;
            }

            int metadataRevision = metadata.getRevision();
            List<String> clientEntryPoints = metadata.getEntrypoints().get("client");
            List<String> serverEntryPoints = metadata.getEntrypoints().get("server");

            // Checking whether the metadata version matches the loader version
            if (metadataRevision != Constants.PLUGINS_METADATA_REVISION) {
                Logger.print(String.format("The metadata form for plugin '%s' does not conform to the current standard (Plugin: %s, Standard: %s)! Skipping...",
                        plugin.getName(),
                        metadataRevision,
                        Constants.PLUGINS_METADATA_REVISION));
                continue;
            }

            // Checking for empty entry points
            if (!isValidEntryPoints(clientEntryPoints, "client", metadata) || !isValidEntryPoints(serverEntryPoints, "server", metadata)) {
                continue;
            }

            // creating a folder for configs
            File configFolder = metadata.getConfigFolder();
            if (!configFolder.exists()) {
                try {
                    configFolder.mkdir();
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.print(String.format("An error occurred while creating the config folder for plugin '%s'", metadata.getId()));
                }
            }

            validPlugins.put(plugin, metadata);
        }

        /*
        Checking for dependency availability and versions
         */
        dependencyVerification(validPlugins);

        /*
        Sorting plugins by load order and Circular dependency check
         */
        ArrayList<File> sortedOrder = sortPluginsByLoadOrder(validPlugins);

        /*
        Loading the plugin
         */
        for (File plugin : sortedOrder) {
            Metadata metadata = validPlugins.get(plugin);

            List<String> clientEntryPoints = metadata.getEntrypoints().get("client");
            List<String> serverEntryPoints = metadata.getEntrypoints().get("server");

            // Creating a URL for the plugin
            URL pluginUrl = plugin.toURI().toURL();

            // try-with-resources
            try (PluginClassLoader classLoader = new PluginClassLoader(new URL[]{pluginUrl})) {
                PluginRegistry.addPluginLoader(metadata.getId(), classLoader);

                loadEntryPoints(true, clientEntryPoints, metadata, classLoader);
                loadEntryPoints(false, serverEntryPoints, metadata, classLoader);

                if (isClient) {
                    String controlsClassName = metadata.getControlsEntrypoint();
                    if (controlsClassName != null && !controlsClassName.isEmpty()) {
                        Class<?> controlsClass = Class.forName(controlsClassName, true, classLoader);
                        IControlsWidget controlsInstance = (IControlsWidget) controlsClass.getDeclaredConstructor().newInstance();

                        PluginRegistry.addPluginControls(metadata.getId(), controlsInstance);
                    }

                    String iconPath = metadata.getIcon();
                    URL iconUrl = classLoader.getResource(iconPath);

                    if (iconUrl != null) {
                        try (BufferedInputStream bis = new BufferedInputStream(iconUrl.openStream())) {
                            Texture texture = new Texture(iconPath, bis, true);
                            PluginRegistry.addPluginIcon(metadata.getId(), texture);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new Exception(String.format("Failed to load plugin '%s' icon texture", metadata.getId()));
                        }
                    }
                }
            }
        }
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
            Plugin pluginInstance = entry.getValue();
            String pluginId = pluginInstance.getMetadata().getId();

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
            Plugin pluginInstance = entry.getValue();
            String pluginId = pluginInstance.getMetadata().getId();

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
     * Loads plugin input points using the provided class loader.
     * This method is designed to load and initialize plugin classes based on lists of input points.
     * For each input point in the list, the method tries to load the corresponding class, create an instance
     * plugin and add it to the specified plugin registry. In case of errors during class loading process
     * or creating instances, the method throws an exception.
     * @param isClient flag indicating whether client or server entry points are loaded
     * @param entryPoints List of string identifiers of the plugin classes' entry points.
     * @param metadata Plugin information used to log and associate instances with plugin data.
     * @param classLoader {@link PluginClassLoader} for loading plugin classes.
     * @throws Exception If errors occur while loading classes or creating instances.
     */
    private static void loadEntryPoints(boolean isClient, List<String> entryPoints, Metadata metadata, PluginClassLoader classLoader) throws Exception {
        String pluginType = isClient ? "client" : "server";

        if (entryPoints.isEmpty()) {
            Logger.print(String.format("There are no %s entry points for plugin '%s'", pluginType, metadata.getId()));
            return;
        }

        for (int index = 0; index < entryPoints.size(); index++) {
            String entryPoint = entryPoints.get(index);

            Class<?> pluginClass = Class.forName(entryPoint, true, classLoader);
            Plugin pluginInstance = (Plugin) pluginClass.getDeclaredConstructor().newInstance();

            pluginInstance.setMetadata(metadata);

            Logger.print(String.format("Loading %s plugin entry point %d/%d: '%s'(ID: '%s', Version: %s)...",
                    pluginType, index + 1, entryPoints.size(), metadata.getName(), metadata.getId(), metadata.getVersion()));

            pluginInstance.onInitialize();

            String registryKey = String.format("%s:%s:%s", entryPoint, metadata.getId(), metadata.getVersion());

            if (isClient) {
                PluginRegistry.addClientPlugin(registryKey, pluginInstance);
            } else {
                PluginRegistry.addServerPlugin(registryKey, pluginInstance);
            }
        }
    }

    /**
     * Sorts plugins by load order using topological sorting.
     * This method takes into account the dependencies between plugins and arranges them in such a way
     * so that each plugin is loaded after all the plugins it depends on have been loaded.
     * @return List of plugin files, sorted in download order.
     * @throws Exception in case a cyclic dependency or other error is detected.
     */
    private static ArrayList<File> sortPluginsByLoadOrder(HashMap<File, Metadata> pluginsMap) throws Exception {
        HashMap<String, List<String>> graph = new HashMap<>();
        HashMap<String, Integer> state = new HashMap<>();
        ArrayList<String> sortedOrder = new ArrayList<>();
        HashMap<String, File> pluginFilesMap = new HashMap<>();

        // Initializing the graph and states
        for (Map.Entry<File, Metadata> entry : pluginsMap.entrySet()) {
            String pluginId = entry.getValue().getId();
            File pluginFile = entry.getKey();

            pluginFilesMap.put(pluginId, pluginFile);
            state.put(pluginId, 0); // 0 - not visited, 1 - on the stack, 2 - visited

            graph.putIfAbsent(pluginId, new ArrayList<>());

            for (String dependency : entry.getValue().getDependencies().keySet()) {
                if (!dependency.equals("project-zomboid") && !dependency.equals("flux-loader")) {
                    graph.putIfAbsent(dependency, new ArrayList<>());
                    graph.get(dependency).add(pluginId);
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
    private static void dependencyVerification(HashMap<File, Metadata> pluginsMap) throws Exception {
        for (Map.Entry<File, Metadata> entry : pluginsMap.entrySet()) {
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

                        for (Map.Entry<File, Metadata> checkEntry : pluginsMap.entrySet()) {
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
     * Checks for the presence of a list of entry points for the plugin.
     * This method is used to check if entry points are defined for a plugin.
     * If the list of entry points is null, the method writes a message about skipping loading the plugin
     * and returns false. An empty list is considered valid.
     * @param entryPoints List of plugin entry points. Can be null.
     * @param type The type of entry points (e.g. "client" or "server").
     * @param metadata Plugin information used for logging.
     * @return true if the list of entry points is not null, false otherwise.
     */
    private static boolean isValidEntryPoints(List<String> entryPoints, String type, Metadata metadata) {
        if (entryPoints == null) {
            Logger.print(String.format("Entry points list is null for %s plugin '%s' (ID: '%s', Version: %s). Skipping...",
                    type, metadata.getName(), metadata.getId(), metadata.getVersion()));
            return false;
        }
        return true;
    }

    /**
     * Finds all JAR plugins in the specified directory. Also checks for the presence of a folder and
     * creates it if it is missing.
     * @throws IOException in cases of input/output problems
     * @return List of JAR files.
     */
    private static ArrayList<File> getPluginFiles() throws IOException {
        Logger.print("Checking for plugins folder...");

        File folder = new File(Constants.PLUGINS_FOLDER_NAME);

        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Logger.print("Failed to create folder...");
            }
        }

        if (!folder.isDirectory()) {
            throw new IOException("Path is not a directory: " + folder.getPath());
        }

        ArrayList<File> jarFiles = new ArrayList<>();

        File[] files = folder.listFiles((File pathname) -> pathname.isFile() && pathname.getName().endsWith(".jar"));

        if (files != null) {
            Collections.addAll(jarFiles, files);
        }

        return jarFiles;
    }
}