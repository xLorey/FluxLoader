package io.xlorey.fluxloader.events;

import java.nio.ByteBuffer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the game client is receiving user log from the server.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnReceiveUserlog extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnReceiveUserlog"; }
    
    /**
    * Called Event Handling Method
    * @param username The username for which we're receiving the log.
    * @param result The resulting log.
    */
    public void handleEvent(String username, ByteBuffer result) {}
}
