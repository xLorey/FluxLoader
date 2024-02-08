package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player is invited to join a faction.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnReceiveFactionInvite extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "ReceiveFactionInvite"; }
    
    /**
    * Called Event Handling Method
    * @param factionName The name of the faction for which the player received an invitation.
    * @param playerName The name of the player who's been invited to join the faction.
    */
    public void handleEvent(String factionName, String playerName) {}
}
