package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;
import zombie.core.raknet.UdpConnection;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the server decided to disconnect from the player.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnPlayerDisconnect extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onPlayerDisconnect"; }

    /**
     * Called Event Handling Method
     * @param player Object of the player who left.
     * @param playerConnection Connection of a player who has left the server.
     */
    public void handleEvent(IsoPlayer player, UdpConnection playerConnection) {}
}
