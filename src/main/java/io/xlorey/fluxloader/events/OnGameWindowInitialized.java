package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Client window full initialization events
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnGameWindowInitialized extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName(){ return "onGameWindowInitialized"; };

    /**
     * Called Event Handling Method
     */
    public void handleEvent() {}
}
