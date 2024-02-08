package io.xlorey.fluxloader.events;

import zombie.iso.IsoObject;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when an object is added to the map.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnObjectAdded extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnObjectAdded"; }
    
    /**
    * Called Event Handling Method
    * @param object The object that was added.
    */
    public void handleEvent(IsoObject object) {}
}
