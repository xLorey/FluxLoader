package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the mouse button is down.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnMouseDown extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnMouseDown"; }
    
    /**
    * Called Event Handling Method
    * @param x The x coordinate where the mouse button was pressed down.
    * @param y The y coordinate where the mouse button was pressed down.
    */
    public void handleEvent(Integer x, Integer y) {}
}
