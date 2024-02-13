package io.xlorey.fluxloader.events;

import zombie.core.raknet.UdpConnection;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the player sends a command to the chat.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnSendChatCommand extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onSendChatCommand"; }
    
    /**
    * Called Event Handling Method
    * @param playerConnection Active player connection
     * @param command command sent to the console including arguments, i.e. the entire string sent to the console
    */
    public void handleEvent(UdpConnection playerConnection, String command) {}
}
