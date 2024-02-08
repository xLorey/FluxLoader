package io.xlorey.fluxloader.client.handlers;

import io.xlorey.fluxloader.client.core.Core;
import io.xlorey.fluxloader.events.OnInitWorld;
import io.xlorey.fluxloader.plugin.PluginManager;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 08.02.2024
 * Description: World initialization event handler for launching plugins
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class OnInitWorldHandler extends OnInitWorld {
    /**
     * Called Event Handling Method
     */
    @Override
    public void handleEvent() {
        if (!Core.isPluginsExecuted) {
            PluginManager.executePlugins(PluginManager.getLoadedClientPlugins());
            Core.isPluginsExecuted = true;
        }
    }
}
