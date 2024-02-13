package io.xlorey.fluxloader.events;

import zombie.characters.IsoZombie;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a zombie dies.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnZombieDead extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnZombieDead"; }
    
    /**
    * Called Event Handling Method
    * @param zombie The zombie who's about to get killed.
    */
    public void handleEvent(IsoZombie zombie) {}
}
