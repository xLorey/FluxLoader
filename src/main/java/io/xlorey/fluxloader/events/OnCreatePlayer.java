package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player is being created.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnCreatePlayer extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnCreatePlayer"; }
    
    /**
    * Called Event Handling Method
    * @param playerIndex The index of the player who's being created.
    * @param player The player who's being created.
    */
    public void handleEvent(Integer playerIndex, IsoPlayer player) {}
}
