package io.xlorey.FluxLoader.client.core;

import io.xlorey.FluxLoader.annotations.SubscribeEvent;

/**
 * Client event handler
 */
public class EventsHandler {
    /**
     * Handling the render event after the UI has been rendered
     */
    @SubscribeEvent(eventName = "OnPostUIDraw")
    public void onPostDrawUIHandler() {
        UIManager.update();
    }

    /**
     * Handling the event of complete initialization of the game window
     */
    @SubscribeEvent(eventName="onClientWindowInit")
    public void onClientWindowInitHandler(){
    }
}