package io.xlorey.fluxloader.events;

import zombie.iso.objects.IsoThumpable;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a thumpable object is being destroyed.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnDestroyIsoThumpable extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnDestroyIsoThumpable"; }
    
    /**
    * Called Event Handling Method
    * @param thumpable The thumpable object which is being destroyed.
    */
    public void handleEvent(IsoThumpable thumpable) {}
}
