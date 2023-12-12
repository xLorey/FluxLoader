package io.xlorey.FluxLoader.server.core;

import io.xlorey.FluxLoader.annotations.SubscribeEvent;
import io.xlorey.FluxLoader.plugin.Plugin;
import io.xlorey.FluxLoader.shared.PluginManager;
import io.xlorey.FluxLoader.utils.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Server events handler
 */
public class EventsHandler {
    /**
     * Handling the server shutdown event
     */
    @SubscribeEvent(eventName="onServerShutdown")
    public void onServerShutdownHandler(){
        HashMap<String, Plugin> pluginsRegistry = PluginManager.getLoadedPlugins();

        for (Map.Entry<String, Plugin> entry : pluginsRegistry.entrySet()) {
            String pluginId = entry.getKey();
            Plugin pluginInstance = entry.getValue();

            Logger.print(String.format("Plugin '%s' is starting to shut down...", pluginId));

            try {
                pluginInstance.onTerminate();
            } catch (Exception e) {
                Logger.print(String.format("Plugin '%s' failed to shut down correctly due to: %s", pluginId, e));
            }

            Logger.print(String.format("Plugin '%s' has been successfully terminated.", pluginId));
        }
    }
}
