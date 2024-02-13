package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the player receives a server message during a cooperative game.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnCoopServerMessage extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnCoopServerMessage"; }
    
    /**
    * Called Event Handling Method
    * @param messageType The type of message received from the server. Can be either `ping`, `pong`, `steam-id`, or `server-address`.
    * @param playerNick The nick of the player who's sending the message.
    * @param steamId The Steam identifier of the player who's sending the message.
    */
    public void handleEvent(String messageType, String playerNick, String steamId) {}
}
