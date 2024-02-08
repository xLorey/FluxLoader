package io.xlorey.fluxloader.events;

import zombie.network.packets.hit.Player;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the player ban command is called.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnPlayerBan extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onPlayerBan"; }
    
    /**
    * Called Event Handling Method
    * @param player An object of the player who was banned.
    * @param reason Reason for blocking the player.
    */
    public void handleEvent(Player player, String reason) {}
}
