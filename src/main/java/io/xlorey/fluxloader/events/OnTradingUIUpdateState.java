package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player updates the item state of a trade.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnTradingUIUpdateState extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "TradingUIUpdateState"; }
    
    /**
    * Called Event Handling Method
    * @param player The player who's updating an item.
    * @param itemIndex The index of the item that the player is updating.
    */
    public void handleEvent(IsoPlayer player, Integer itemIndex) {}
}
