package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the game client receives the response after intiating a ping to a server.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnServerPinged extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "ServerPinged"; }
    
    /**
    * Called Event Handling Method
    * @param ipAddress The IP address of the user who pinged the server.
    * @param user The name of the user who pinged the server.
    */
    public void handleEvent(String ipAddress, String user) {}
}
