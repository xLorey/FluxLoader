package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;
import zombie.inventory.InventoryItem;
import zombie.inventory.types.Moveable;
import zombie.scripting.objects.MovableRecipe;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a dynamic recipe for a movable inventory item is being used.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnDynamicMovableRecipe extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnDynamicMovableRecipe"; }
    
    /**
    * Called Event Handling Method
    * @param moveable The movable object resulting from using the recipe.
    * @param movableRecipe The movable recipe that is being used.
    * @param inventoryItem The item from the player inventory.
    * @param character The character who's using the recipe.
    */
    public void handleEvent(Moveable moveable, MovableRecipe movableRecipe, InventoryItem inventoryItem, IsoGameCharacter character) {}
}
