package io.xlorey.fluxloader.server.handlers;

import io.xlorey.fluxloader.events.OnServerInitialize;
import io.xlorey.fluxloader.plugin.PluginManager;
import io.xlorey.fluxloader.plugin.PluginRegistry;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 27.02.2024
 * Description: Handling server initialize, necessary to execute plugins
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class OnServerInitHandler extends OnServerInitialize {
    /**
     * Called Event Handling Method
     */
    @Override
    public void handleEvent() {
        PluginManager.executePlugins(PluginRegistry.getLoadedServerPlugins());
    }
}
