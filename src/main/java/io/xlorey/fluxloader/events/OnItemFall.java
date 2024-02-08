package io.xlorey.fluxloader.events;

import zombie.inventory.InventoryItem;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when an item is being dropped on the ground.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnItemFall extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onItemFall"; }
    
    /**
    * Called Event Handling Method
    * @param item The inventory item being dropped on the ground.
    */
    public void handleEvent(InventoryItem item) {}
}
