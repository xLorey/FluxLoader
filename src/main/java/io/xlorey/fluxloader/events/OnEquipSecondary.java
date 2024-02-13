package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;
import zombie.inventory.InventoryItem;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a character equips an item in its secondary slot.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnEquipSecondary extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnEquipSecondary"; }
    
    /**
    * Called Event Handling Method
    * @param character The character who's equipping the item.
    * @param inventoryItem The item that is being equipped in the secondary slot.
    */
    public void handleEvent(IsoGameCharacter character, InventoryItem inventoryItem) {}
}
