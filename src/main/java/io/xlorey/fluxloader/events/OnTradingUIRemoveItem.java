package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player removes an item from a trade.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnTradingUIRemoveItem extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "TradingUIRemoveItem"; }
    
    /**
    * Called Event Handling Method
    * @param player The player who's removing an item from the trade.
    * @param itemIndex The index of the item that the player is removing from the trade.
    */
    public void handleEvent(IsoPlayer player, Integer itemIndex) {}
}
