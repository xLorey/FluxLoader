package io.xlorey.FluxLoader.shared;

import io.xlorey.FluxLoader.utils.Constants;
import io.xlorey.FluxLoader.utils.Logger;

import java.io.File;

/**
 * Plugin (mod) management system
 */
public class PluginManager {
    /**
     * Manager initialization
     */
    public static void init() {
        Logger.print("Initializing the plugin management system...");

        checkPluginFolder();
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
