package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the status of a friend changed on Steam.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnSteamFriendStatusChanged extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnSteamFriendStatusChanged"; }
    
    /**
    * Called Event Handling Method
    * @param steamId Steam identifier of the user who's friend status has changed.
    */
    public void handleEvent(String steamId) {}
}
