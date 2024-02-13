package io.xlorey.fluxloader.events;

import se.krka.kahlua.vm.KahluaTable;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered before context menus get filled with options.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnPreFillInventoryObjectContextMenu extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnPreFillInventoryObjectContextMenu"; }
    
    /**
    * Called Event Handling Method
    * @param playerID The player ID for which the context menu is being filled.
    * @param context The context menu to be filled.
    * @param items The items available in the player inventory.
    */
    public void handleEvent(Integer playerID, KahluaTable context, KahluaTable items) {}
}
