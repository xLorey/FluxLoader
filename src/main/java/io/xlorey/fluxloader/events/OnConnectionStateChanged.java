package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: When a player is connecting to the server, the connection is going through different stages. This event is triggered for each of these stages of the initial connection.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnConnectionStateChanged extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnConnectionStateChanged"; }
    
    /**
    * Called Event Handling Method
    * @param state The current state of the connection that has changed.
    * @param reason The reason leading to the state change. It can be null.
    */
    public void handleEvent(String state, String reason) {}
}
