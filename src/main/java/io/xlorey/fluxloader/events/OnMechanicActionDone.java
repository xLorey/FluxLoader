package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player finished a mechanic action.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnMechanicActionDone extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnMechanicActionDone"; }
    
    /**
    * Called Event Handling Method
    * @param character The character who performed the mechanic action.
    * @param success Whether the machanic action was successful or not.
    * @param vehicleId The identifier of the vehicle on which the mechanic action is being performed.
    * @param partId The vehicle part identifier that is being installed or removed.
    * @param itemId The item identifier used to perform the mechanic action.
    * @param installing Whether the vehicle part is being installed or removed.
    */
    public void handleEvent(IsoGameCharacter character, Boolean success, Integer vehicleId, String partId, Long itemId, Boolean installing) {}
}
