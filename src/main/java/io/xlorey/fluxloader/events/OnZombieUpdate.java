package io.xlorey.fluxloader.events;

import zombie.characters.IsoZombie;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a zombie is being updated.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnZombieUpdate extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnZombieUpdate"; }
    
    /**
    * Called Event Handling Method
    * @param zombie The zombie who's being updated.
    */
    public void handleEvent(IsoZombie zombie) {}
}
