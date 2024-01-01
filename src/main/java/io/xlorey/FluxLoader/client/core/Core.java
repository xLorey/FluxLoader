package io.xlorey.FluxLoader.client.core;

import io.xlorey.FluxLoader.shared.EventManager;
import io.xlorey.FluxLoader.shared.PluginManager;
import io.xlorey.FluxLoader.utils.Logger;

/**
 * FluxLoader Core
 */
public class Core {
    /**
     * Flag indicating whether client plugins are running
     */
    public static boolean isPluginsExecuted = false;

    /**
     * Initializing the loader
     * @exception Exception in cases of unsuccessful core initialization
     */
    public static void init() throws Exception {
        Logger.printCredits();

        Logger.printLog("FluxLoader Core initialization for the client...");

        EventManager.subscribe(new EventsHandler());

        PluginManager.loadPlugins(true);

        WidgetManager.init();
    }
}