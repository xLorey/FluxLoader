package io.xlorey.FluxLoader.server.core;

import io.xlorey.FluxLoader.shared.EventManager;
import io.xlorey.FluxLoader.shared.PluginManager;
import io.xlorey.FluxLoader.utils.Logger;

/**
 * FluxLoader Core
 */
public class Core {
    /**
     * Determines whether the current server is a Coop Server.
     * This method checks if client plugins have been loaded, which is an indicator
     * shared (cooperative) server in the context of FluxLoader. If client plugins are loaded,
     * this indicates that the server is running in shared mode, assuming
     * the ability to control certain aspects of the server from the client side.
     * @return true if the server is a shared server; otherwise false.
     */
    public static boolean isCoopServer() {
        return io.xlorey.FluxLoader.client.core.Core.isClientLoaded();
    }

    /**
     * Initializing the loader
     * @exception Exception in cases of unsuccessful core initialization
     */
    public static void init() throws Exception {
        if (isCoopServer()) {
            PluginManager.executePlugins(PluginManager.getLoadedServerPlugins());
            return;
        }

        Logger.printCredits();

        Logger.print("FluxLoader Core initialization for the server..");

        EventManager.subscribe(new EventsHandler());

        PluginManager.loadPlugins();

        PluginManager.executePlugins(PluginManager.getLoadedServerPlugins());
    }
}
