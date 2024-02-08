package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;
import zombie.characters.IsoSurvivor;
import zombie.characters.SurvivorDesc;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when either a player or survivor is being created.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnCreateLivingCharacter extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnCreateLivingCharacter"; }
    
    /**
    * Called Event Handling Method
    * @param playerOrSurvivor The player or survivor who's being created.
    * @param survivorDesc The survivor description of the player or survivor who's being created.
    */
    public void handleEvent(IsoPlayer playerOrSurvivor, SurvivorDesc survivorDesc) {}

    /**
     * Called Event Handling Method
     * @param playerOrSurvivor The player or survivor who's being created.
     * @param survivorDesc The survivor description of the player or survivor who's being created.
     */
    public void handleEvent(IsoSurvivor playerOrSurvivor, SurvivorDesc survivorDesc) {}
}
