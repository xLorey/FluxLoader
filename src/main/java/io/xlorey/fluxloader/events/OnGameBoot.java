package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when either a game or a server is being started, or when mods are getting reloaded during a game.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnGameBoot extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnGameBoot"; }
    
    /**
    * Called Event Handling Method
    */
    public void handleEvent() {}
}
