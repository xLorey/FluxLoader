package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the texture of a vehicle part is changed after being damaged.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnVehicleDamageTexture extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnVehicleDamageTexture"; }
    
    /**
    * Called Event Handling Method
    * @param character The player who's driving the vehicle.
    */
    public void handleEvent(IsoGameCharacter character) {}
}
