package io.xlorey.fluxloader.events;

import zombie.iso.IsoObject;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a tile object has been removed.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnTileRemoved extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnTileRemoved"; }
    
    /**
    * Called Event Handling Method
    * @param object The object to be removed.
    */
    public void handleEvent(IsoObject object) {}
}
