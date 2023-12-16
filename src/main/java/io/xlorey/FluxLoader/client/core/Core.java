package io.xlorey.FluxLoader.client.core;

import io.xlorey.FluxLoader.shared.EventManager;
import io.xlorey.FluxLoader.shared.PluginManager;
import io.xlorey.FluxLoader.utils.Logger;

/**
 * FluxLoader Core
 */
public class Core {
    /**
     * Initializing the loader
     * @exception Exception in cases of unsuccessful core initialization
     */
    public static void init() throws Exception {
        Logger.printCredits();

        Logger.print("FluxLoader Core initialization for the client...");

        EventManager.subscribe(new EventsHandler());

        PluginManager.loadPlugins(true);

        UIManager.init();
    }
}
