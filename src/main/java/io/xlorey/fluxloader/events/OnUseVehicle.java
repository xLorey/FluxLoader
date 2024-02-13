package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;
import zombie.vehicles.BaseVehicle;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a character is using a vehicle.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnUseVehicle extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnUseVehicle"; }
    
    /**
    * Called Event Handling Method
    * @param player The player who's driving the vehicle.
    * @param baseVehicle The vehicle which the player is driving.
    * @param pressedNotTapped Whether the player had been keeping the accelerator pressed or if it was just a tap.
    */
    public void handleEvent(IsoPlayer player, BaseVehicle baseVehicle, Boolean pressedNotTapped) {}
}
