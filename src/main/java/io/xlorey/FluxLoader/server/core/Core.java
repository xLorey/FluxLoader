package io.xlorey.FluxLoader.server.core;

import io.xlorey.FluxLoader.shared.EventManager;
import io.xlorey.FluxLoader.shared.PluginManager;
import io.xlorey.FluxLoader.utils.Logger;

/**
 * FluxLoader Core
 */
public class Core {
    /**
     * Initializing the loader
     * @param serverArgs server boot arguments
     * @exception Exception in cases of unsuccessful core initialization
     */
    public static void init(String[] serverArgs) throws Exception {
        boolean isCoop = false;

        for (String serverArg : serverArgs) {
            if (serverArg != null) {
                if (serverArg.equals("-coop")) {
                    Logger.print("Launching a co-op server...");
                    isCoop = true;
                    break;
                }
            }
        }

        if (!isCoop) Logger.printCredits();

        Logger.print("FluxLoader Core initialization for the server..");

        EventManager.subscribe(new EventsHandler());

        PluginManager.loadPlugins();

        PluginManager.executePlugins(PluginManager.getLoadedServerPlugins());
    }
}