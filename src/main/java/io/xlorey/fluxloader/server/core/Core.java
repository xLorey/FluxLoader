package io.xlorey.fluxloader.server.core;

import io.xlorey.fluxloader.enums.EventPriority;
import io.xlorey.fluxloader.plugin.PluginManager;
import io.xlorey.fluxloader.server.handlers.OnServerInitHandler;
import io.xlorey.fluxloader.server.handlers.OnServerShutdownHandler;
import io.xlorey.fluxloader.shared.EventManager;
import io.xlorey.fluxloader.utils.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;

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
     * Pre-initialization of the core. Loads before all server initializations.
     */
    public static void preInit() {
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
    }

    /**
     * Initializing the loader. Called after basic initializations, namely at the moment after the Translator
     * is initialized
     * @exception Exception in cases of unsuccessful core initialization
     */
    public static void init() throws Exception {
        PluginManager.loadPlugins(false);

        EventManager.subscribe(new OnServerShutdownHandler(), EventPriority.HIGHEST);
        EventManager.subscribe(new OnServerInitHandler(), EventPriority.HIGHEST);
    }
}