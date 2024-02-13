package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a character is requesting a trade with another character.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnRequestTrade extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "RequestTrade"; }
    
    /**
    * Called Event Handling Method
    * @param player The player who's requesting the trade.
    */
    public void handleEvent(IsoPlayer player) {}
}
