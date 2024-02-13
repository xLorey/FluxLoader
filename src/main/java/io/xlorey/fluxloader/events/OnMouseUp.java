package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the mouse button is released.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnMouseUp extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnMouseUp"; }
    
    /**
    * Called Event Handling Method
    * @param x The x coordinate where the mouse button was released.
    * @param y The y coordinate where the mouse button was released.
    */
    public void handleEvent(Integer x, Integer y) {}
}
