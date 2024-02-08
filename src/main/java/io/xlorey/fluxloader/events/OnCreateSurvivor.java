package io.xlorey.fluxloader.events;

import zombie.characters.IsoSurvivor;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a survivor is being created.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnCreateSurvivor extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnCreateSurvivor"; }
    
    /**
    * Called Event Handling Method
    * @param survivor The survivor who's being created.
    */
    public void handleEvent(IsoSurvivor survivor) {}
}
