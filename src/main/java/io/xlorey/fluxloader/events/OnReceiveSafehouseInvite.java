package io.xlorey.fluxloader.events;

import zombie.iso.areas.SafeHouse;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player is invited to a safehouse.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnReceiveSafehouseInvite extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "ReceiveSafehouseInvite"; }
    
    /**
    * Called Event Handling Method
    * @param safeHouse The safehouse for which the player received an invitation.
    * @param playerName The name of the player who's been invited to join the safehouse.
    */
    public void handleEvent(SafeHouse safeHouse, String playerName) {}
}
