package io.xlorey.fluxloader.events;

import se.krka.kahlua.vm.KahluaTable;
import zombie.characters.IsoPlayer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered before context menu for world objects is filled.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnPreFillWorldObjectContextMenu extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnPreFillWorldObjectContextMenu"; }
    
    /**
    * Called Event Handling Method
    * @param player The player for which the context menu is being filled.
    * @param context The context menu to be filled.
    * @param worldObjects The world objects available nearby the player.
    * @param test True if called for the purpose of testing for nearby objects.
    */
    public void handleEvent(IsoPlayer player, KahluaTable context, KahluaTable worldObjects, Boolean test) {}
}
