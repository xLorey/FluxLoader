package io.xlorey.FluxLoader.client.core;

import io.xlorey.FluxLoader.annotations.SubscribeEvent;
import io.xlorey.FluxLoader.shared.PluginManager;

/**
 * Client event handler
 */
public class EventsHandler {
    /**
     * Handling the render event after the UI has been rendered
     */
    @SubscribeEvent(eventName = "OnPostUIDraw")
    public void onPostDrawUIHandler() {
        WidgetManager.update();
    }

    /**
     * Handling the event of complete initialization of the game window
     */
    @SubscribeEvent(eventName="onClientWindowInit")
    public void onClientWindowInitHandler(){
    }

    /**
     * Game session loading event handler
     */
    @SubscribeEvent(eventName = "OnInitWorld")
    public void onInitWorldHandler(){
        if (!Core.isPluginsExecuted) {
            PluginManager.executePlugins(PluginManager.getLoadedClientPlugins());
            Core.isPluginsExecuted = true;
        }
    }

    /**
     * Main menu exit event handler
     */
    @SubscribeEvent(eventName = "OnMainMenuEnter")
    public void onMainMenuEnterHandler(){
        if (Core.isPluginsExecuted) {
            PluginManager.terminatePlugins(PluginManager.getLoadedClientPlugins());
            Core.isPluginsExecuted = false;
        }
    }

    /**
     * Handling render events on the render thread
     */
    public static void onDrawWithRenderThreadHandler(){
        WidgetManager.drawImGui();
    }
}