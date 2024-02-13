package io.xlorey.fluxloader.events;

import zombie.core.raknet.UdpConnection;

import java.nio.ByteBuffer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a packet from a client arrives at the server.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnAddIncoming extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onAddIncoming"; }

    /**
     * Called Event Handling Method
     * @param opcode an opcode that specifies the type of event
     * @param data data associated with a packet, represented as a ByteBuffer
     * @param playerConnection player connection associated with packet
     */
    public void handleEvent(Short opcode, ByteBuffer data, UdpConnection playerConnection) {}
}
