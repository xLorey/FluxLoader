package io.xlorey.fluxloader.server.handlers;

import io.xlorey.fluxloader.events.OnServerShutdown;
import io.xlorey.fluxloader.plugin.PluginManager;
import io.xlorey.fluxloader.plugin.PluginRegistry;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 08.02.2024
 * Description: Handling server shutdown, necessary to shut down plugins
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class OnServerShutdownHandler extends OnServerShutdown {
    @Override
    public void handleEvent() {
        PluginManager.terminatePlugins(PluginRegistry.getLoadedServerPlugins());
    }
}
