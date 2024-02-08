package io.xlorey.fluxloader.events;

import se.krka.kahlua.vm.KahluaTable;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when inventory object context menus are being filled.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnFillInventoryObjectContextMenu extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnFillInventoryObjectContextMenu"; }
    
    /**
    * Called Event Handling Method
    * @param playerIndex The index of the player for which the context menu is being filled.
    * @param table The context menu to be filled.
    * @param items The items available in the player inventory.
    */
    public void handleEvent(Integer playerIndex, KahluaTable table, KahluaTable items) {}
}
