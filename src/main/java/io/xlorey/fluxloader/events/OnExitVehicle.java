package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a character is exiting a vehicle.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnExitVehicle extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnExitVehicle"; }
    
    /**
    * Called Event Handling Method
    * @param character The character who's exiting the vehicle.
    */
    public void handleEvent(IsoGameCharacter character) {}
}
