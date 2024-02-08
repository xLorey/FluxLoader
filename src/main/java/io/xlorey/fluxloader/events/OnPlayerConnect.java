package io.xlorey.fluxloader.events;

import zombie.core.raknet.UdpConnection;

import java.nio.ByteBuffer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the player connects to the server
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnPlayerConnect extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onPlayerConnect"; }

    /**
     * Called Event Handling Method
     * @param data event-related data represented as a ByteBuffer
     * @param playerConnection active player connection
     * @param username user name
     */
    public void handleEvent(ByteBuffer data, UdpConnection playerConnection, String username) {}
}
