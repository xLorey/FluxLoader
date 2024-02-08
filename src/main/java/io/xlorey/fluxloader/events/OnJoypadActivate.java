package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a joypad is activated in-game.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnJoypadActivate extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnJoypadActivate"; }
    
    /**
    * Called Event Handling Method
    * @param controllerId The identifier of the joypad which has been activated.
    */
    public void handleEvent(Integer controllerId) {}
}
