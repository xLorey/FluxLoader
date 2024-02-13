package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a custom UI key has been pressed.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnCustomUIKeyPressed extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnCustomUIKeyPressed"; }
    
    /**
    * Called Event Handling Method
    * @param key The Keyboard key that has been pressed.
    */
    public void handleEvent(Integer key) {}
}
