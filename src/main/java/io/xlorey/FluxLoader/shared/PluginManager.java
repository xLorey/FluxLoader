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
                System.out.println("The folder was created successfully.");
            } else {
                System.out.println("Failed to create folder.");
            }
        } else {
            System.out.println("Plugins folder found!");
        }
    }
}
