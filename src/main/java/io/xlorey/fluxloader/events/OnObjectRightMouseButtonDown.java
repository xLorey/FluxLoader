package io.xlorey.fluxloader.events;

import zombie.iso.IsoObject;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when right mouse button clicked on object
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnObjectRightMouseButtonDown extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnObjectRightMouseButtonDown"; }
    
    /**
    * Called Event Handling Method
    * @param object The object on which the right mouse button was pressed down.
    * @param x The x coordinate where the right mouse button was pressed down.
    * @param y The y coordinate where the right mouse button was pressed down.
    */
    public void handleEvent(IsoObject object, Integer x, Integer y) {}
}
