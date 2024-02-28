package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the player unban command is called.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnPlayerUnban extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onPlayerUnban"; }
    
    /**
    * Called Event Handling Method
    * @param player Player nickname, in case of unban by nickname,
    *               or string representation of SteamID, in case of unban by ID
    * @param adminName Nickname of the administrator who unbanned
    */
    public void handleEvent(String player, String adminName) {}
}
