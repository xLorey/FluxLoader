package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when joypad is activated from main screen.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnJoypadActivateUI extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnJoypadActivateUI"; }
    
    /**
    * Called Event Handling Method
    * @param joypadId The identifier of the joypad.
    */
    public void handleEvent(Integer joypadId) {}
}
