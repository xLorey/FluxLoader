package io.xlorey.FluxLoader.shared;

import io.xlorey.FluxLoader.plugin.Plugin;
import io.xlorey.FluxLoader.plugin.PluginInfo;
import io.xlorey.FluxLoader.utils.Constants;
import io.xlorey.FluxLoader.utils.Logger;
import io.xlorey.FluxLoader.utils.VersionChecker;
import zombie.core.Core;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Plugin (mod) management system
 */
public class PluginManager {
    /**
     * All loaded information about plugins
     */
    private static final HashMap<File, PluginInfo> pluginsList = new HashMap<>();

    /**
     * Loading plugins for the client
     * @param isClient flag for loading plugins in the client environment
     * @throws IOException in cases of input/output problems
     */
    public static void loadPlugins(boolean isClient) throws Exception {
        Logger.print("Loading plugins into the environment...");

        checkPluginFolder();

        ArrayList<File> pluginFiles = getPluginFiles();

        for (File plugin : pluginFiles) {
            PluginInfo pluginInfo = PluginInfo.getInfoFromFile(plugin);

            if (pluginInfo == null) {
                Logger.print(String.format("No metadata found for potential plugin '%s'. Skipping...", plugin.getName()));
                continue;
            }
            pluginsList.put(plugin, pluginInfo);
        }

        // Checking for dependency availability and versions
        for (Map.Entry<File, PluginInfo> entry : pluginsList.entrySet()) {
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
                }
            }
        }

        for (Map.Entry<File, PluginInfo> entry : pluginsList.entrySet()) {
            File plugin = entry.getKey();
            PluginInfo pluginInfo = entry.getValue();

            List<String> entryPoints = isClient ? pluginInfo.getEntrypoints().get("client") : pluginInfo.getEntrypoints().get("server");

            // Checking for empty entry points
            if (entryPoints == null || entryPoints.isEmpty()) {
                Logger.print(String.format("No entry points defined for plugin '%s'(ID: '%s', Version: %s). Skipping...",
                        pluginInfo.getName(), pluginInfo.getId(), pluginInfo.getVersion()));
                continue;
            }

            ClassLoader appClassLoader;

            if (isClient) {
                appClassLoader = io.xlorey.FluxLoader.client.core.Core.class.getClassLoader();

            } else {
                appClassLoader = io.xlorey.FluxLoader.server.core.Core.class.getClassLoader();
            }

            // Creating a URLClassLoader for the plugin JAR file
            URLClassLoader classLoader = new URLClassLoader(new URL[]{plugin.toURI().toURL()}, appClassLoader);
            for (String entryPoint : entryPoints) {
                Class<?> pluginClass = Class.forName(entryPoint, true, classLoader);
                Plugin pluginInstance = (Plugin) pluginClass.getDeclaredConstructor().newInstance();

                pluginInstance.setInfo(pluginInfo);

                Logger.print(String.format("Loading plugin '%s' (ID: '%s', Version: %s)",
                        pluginInfo.getName(), pluginInfo.getId(), pluginInfo.getVersion()));

                pluginInstance.onInitialize();

                EventManager.subscribe(pluginInstance);
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
