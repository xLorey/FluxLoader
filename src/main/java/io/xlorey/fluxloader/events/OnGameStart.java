package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered after the start of a new game, and after a saved game has been loaded.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnGameStart extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnGameStart"; }
    
    /**
    * Called Event Handling Method
    */
    public void handleEvent() {}
}
