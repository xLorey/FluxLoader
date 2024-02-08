package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player is being updated.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnPlayerUpdate extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnPlayerUpdate"; }
    
    /**
    * Called Event Handling Method
    * @param player The player who's being updated.
    */
    public void handleEvent(IsoPlayer player) {}
}
