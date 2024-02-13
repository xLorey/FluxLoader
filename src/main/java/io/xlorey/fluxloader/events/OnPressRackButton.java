package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;
import zombie.inventory.types.HandWeapon;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player hits the button to rack a firearm.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnPressRackButton extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnPressRackButton"; }
    
    /**
    * Called Event Handling Method
    * @param player The player who's racking the firearm.
    * @param firearm The firearm which is being racked.
    */
    public void handleEvent(IsoPlayer player, HandWeapon firearm) {}
}
