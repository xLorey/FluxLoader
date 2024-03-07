package io.xlorey.fluxloader.server.core;

import io.xlorey.fluxloader.enums.EventPriority;
import io.xlorey.fluxloader.plugin.PluginManager;
import io.xlorey.fluxloader.server.handlers.OnServerInitHandler;
import io.xlorey.fluxloader.server.handlers.OnServerShutdownHandler;
import io.xlorey.fluxloader.shared.EventManager;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Flux Loader server core
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class Core {
    /**
     * Initializing the loader. Called after basic initializations, namely at the moment after the Translator
     * is initialized
     * @exception Exception in cases of unsuccessful core initialization
     */
    public static void init() throws Exception {
        EventManager.subscribe(new OnServerShutdownHandler(), EventPriority.HIGHEST);
        EventManager.subscribe(new OnServerInitHandler(), EventPriority.HIGHEST);

        PluginManager.loadPlugins(false);
    }
}