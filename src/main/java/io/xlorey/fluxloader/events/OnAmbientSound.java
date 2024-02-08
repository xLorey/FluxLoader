package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when an ambient sound starts.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnAmbientSound extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnAmbientSound"; }
    
    /**
    * Called Event Handling Method
    * @param name The name of the ambient sound.
    * @param x The x coordinate of the ambient sound.
    * @param y The y coordinate of the ambient sound.
    */
    public void handleEvent(String name, Float x, Float y) {}
}
