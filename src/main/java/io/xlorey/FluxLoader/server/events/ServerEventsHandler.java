package io.xlorey.FluxLoader.server.events;

import io.xlorey.FluxLoader.annotations.SubscribeEvent;
import io.xlorey.FluxLoader.plugin.Plugin;
import io.xlorey.FluxLoader.plugin.PluginInfo;
import io.xlorey.FluxLoader.shared.PluginManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Server events handler
 */
public class ServerEventsHandler {
    /**
     * Handling a server shutdown event
     */
    @SubscribeEvent(eventName="onServerShutdown")
    public void onServerShutdownHandler(){
        HashMap<PluginInfo, Plugin> plugins = PluginManager.getLoadedPlugins();

        for (Map.Entry<PluginInfo, Plugin> entry : plugins.entrySet()) {
            Plugin plugin = entry.getValue();

            plugin.onDisable();
        }
    }
}
