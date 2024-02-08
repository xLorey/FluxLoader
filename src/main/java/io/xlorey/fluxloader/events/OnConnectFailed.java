package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the connection to the server has failed.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnConnectFailed extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnConnectFailed"; }
    
    /**
    * Called Event Handling Method
    * @param error The error message describing the reason for the connection failure.
    */
    public void handleEvent(String error) {}
}
