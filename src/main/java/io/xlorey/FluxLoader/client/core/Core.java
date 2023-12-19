package io.xlorey.FluxLoader.client.core;

import io.xlorey.FluxLoader.shared.EventManager;
import io.xlorey.FluxLoader.shared.PluginManager;
import io.xlorey.FluxLoader.utils.Logger;

/**
 * FluxLoader Core
 */
public class Core {
    /**
     * Flag indicating whether client plugins have been loaded
     */
    private static boolean isClientLoaded = false;

    /**
     * Initializing the loader
     * @exception Exception in cases of unsuccessful core initialization
     */
    public static void init() throws Exception {
        Logger.printCredits();

        Logger.print("FluxLoader Core initialization for the client...");

        EventManager.subscribe(new EventsHandler());

        PluginManager.loadPlugins();

        WidgetManager.init();

        isClientLoaded = true;
    }

    /**
     * Checks if client plugins have been loaded.
     * This method provides information about the initialization state of the FluxLoader client.
     * It returns the value of a flag that indicates whether initialization was successful
     * and loading client plugins.
     * @return true if client plugins were loaded successfully; otherwise false.
     */
    public static boolean isClientLoaded() {
        return isClientLoaded;
    }
}
