package io.xlorey.fluxloader.events;

import zombie.iso.objects.IsoFire;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a fire starts.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnNewFire extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnNewFire"; }
    
    /**
    * Called Event Handling Method
    * @param fire The fire object.
    */
    public void handleEvent(IsoFire fire) {}
}
