package io.xlorey.fluxloader.server.core;

import io.xlorey.fluxloader.enums.EventPriority;
import io.xlorey.fluxloader.plugin.PluginManager;
import io.xlorey.fluxloader.plugin.PluginRegistry;
import io.xlorey.fluxloader.server.handlers.OnServerShutdownHandler;
import io.xlorey.fluxloader.shared.EventManager;
import io.xlorey.fluxloader.utils.Logger;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Flux Loader server core
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class Core {
    /**
     * Server starting arguments
     */
    public static String[] serverArgs;

    /**
     * Initializing the loader
     * @exception Exception in cases of unsuccessful core initialization
     */
    public static void init() throws Exception {
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

        PluginManager.loadPlugins(false);

        EventManager.subscribe(new OnServerShutdownHandler(), EventPriority.HIGHEST);

        PluginManager.executePlugins(PluginRegistry.getLoadedServerPlugins());
    }
}