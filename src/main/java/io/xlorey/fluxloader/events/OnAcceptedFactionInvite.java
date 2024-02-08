package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a faction invite has been accepted.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnAcceptedFactionInvite extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "AcceptedFactionInvite"; }
    
    /**
    * Called Event Handling Method
    * @param factionName The name of the faction the player accepted to join.
    * @param playerName The name of the player who accepted the invitation.
    */
    public void handleEvent(String factionName, String playerName) {}
}
