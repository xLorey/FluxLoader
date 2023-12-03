package io.xlorey.FluxLoader.server.core;

/**
 * Toolkit for managing packet blocking status.
 */
public class IncomingPacket {
    private static volatile boolean isPacketBlocked = false;

    /**
     * Blocks the packets.
     */
    public static synchronized void blockPacket() {
        isPacketBlocked = true;
    }

    /**
     * Checks if the packets are blocked and resets the block.
     * @return true if the packets were blocked, false otherwise.
     */
    public static synchronized boolean checkAndResetPacketBlocking() {
        if (isPacketBlocked) {
            isPacketBlocked = false;
            return true;
        }
        return false;
    }
}
