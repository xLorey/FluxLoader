package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a character is switching seat in a vehicle.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnSwitchVehicleSeat extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnSwitchVehicleSeat"; }
    
    /**
    * Called Event Handling Method
    * @param player The player who's switching seat in the vehicle.
    */
    public void handleEvent(IsoPlayer player) {}
}
