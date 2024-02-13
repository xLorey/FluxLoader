package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a gamepad has been connected.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnGamepadConnect extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnGamepadConnect"; }
    
    /**
    * Called Event Handling Method
    * @param controllerID The identifier of the gamepad which has been connected.
    */
    public void handleEvent(Integer controllerID) {}
}
