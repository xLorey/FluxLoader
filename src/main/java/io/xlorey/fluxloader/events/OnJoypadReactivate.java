package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a joypad was connected, just after it's been activated.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnJoypadReactivate extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnJoypadReactivate"; }
    
    /**
    * Called Event Handling Method
    * @param joypadId The identifier of the joypad.
    */
    public void handleEvent(Double joypadId) {}
}
