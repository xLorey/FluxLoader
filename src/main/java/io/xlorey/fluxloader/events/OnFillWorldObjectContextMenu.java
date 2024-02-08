package io.xlorey.fluxloader.events;

import se.krka.kahlua.vm.KahluaTable;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when world object context menus are being filled.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnFillWorldObjectContextMenu extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnFillWorldObjectContextMenu"; }
    
    /**
    * Called Event Handling Method
    * @param playerIndex The index of the player for which the context menu is being filled.
    * @param context The context menu to be filled.
    * @param worldObjects The world objects available nearby the player.
    * @param test Set to true if called for the purpose of testing for nearby objects.
    */
    public void handleEvent(Integer playerIndex, KahluaTable context, KahluaTable worldObjects, Boolean test) {}
}
