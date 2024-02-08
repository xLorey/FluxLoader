package io.xlorey.fluxloader.client.handlers;

import io.xlorey.fluxloader.client.core.Core;
import io.xlorey.fluxloader.events.OnMainMenuEnter;
import io.xlorey.fluxloader.plugin.PluginManager;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 08.02.2024
 * Description: Event handler for exiting to the main menu to disable plugins
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class OnMainMenuEnterHandler extends OnMainMenuEnter {
    /**
     * Called Event Handling Method
     */
    @Override
    public void handleEvent() {
        if (Core.isPluginsExecuted) {
            PluginManager.terminatePlugins(PluginManager.getLoadedClientPlugins());
            Core.isPluginsExecuted = false;
        }
    }
}
