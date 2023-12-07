package io.xlorey.FluxLoader.server.core;

import io.xlorey.FluxLoader.utils.Logger;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * Toolkit for managing packet blocking status.
 */
public class IncomingPacket {
    private static final Map<ByteBuffer, Boolean> blockedPackets = new ConcurrentHashMap<>();

    /**
     * Blocks a packet.
     * @param byteBuffer package data
     */
    public static void blockPacket(ByteBuffer byteBuffer) {
        blockedPackets.put(byteBuffer, true);
    }

    /**
     * Checks if a packet is blocked and resets the block for that specific packet.
     * @param byteBuffer package data
     * @return true if the packet was blocked, false otherwise.
     */
    public static boolean checkAndResetPacketBlocking(ByteBuffer byteBuffer) {
        Boolean isBlocked = blockedPackets.get(byteBuffer);
        if (isBlocked != null && isBlocked) {
            Logger.print("Packet was blocked!");
            blockedPackets.remove(byteBuffer);
            return true;
        }
        return false;
    }

}
