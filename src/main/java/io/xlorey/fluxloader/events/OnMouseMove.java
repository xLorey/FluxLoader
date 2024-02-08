package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the mouse is moved.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnMouseMove extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnMouseMove"; }
    
    /**
    * Called Event Handling Method
    * @param x The x coordinate of the mouse position.
    * @param y The y coordinate of the mouse position.
    * @param dx TODO: Mouse position deltaX?
    * @param dy TODO: Mouse position deltaY?
    */
    public void handleEvent(Integer x, Integer y, Integer dx, Integer dy) {}
}
