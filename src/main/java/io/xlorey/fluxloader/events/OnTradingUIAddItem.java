package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;
import zombie.inventory.InventoryItem;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player adds an item to a trade.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnTradingUIAddItem extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "TradingUIAddItem"; }
    
    /**
    * Called Event Handling Method
    * @param player The player who's adding an item to the trade.
    * @param inventoryItem The item which the player is adding to the trade.
    */
    public void handleEvent(IsoPlayer player, InventoryItem inventoryItem) {}
}
