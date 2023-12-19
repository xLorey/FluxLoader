package io.xlorey.FluxLoader.server.core;

import io.xlorey.FluxLoader.annotations.SubscribeEvent;
import io.xlorey.FluxLoader.shared.PluginManager;

/**
 * Server events handler
 */
public class EventsHandler {
    /**
     * Handling the server shutdown event
     */
    @SubscribeEvent(eventName="onServerShutdown")
    public void onServerShutdownHandler(){
        PluginManager.terminatePlugins(PluginManager.getLoadedServerPlugins());
    }
}
