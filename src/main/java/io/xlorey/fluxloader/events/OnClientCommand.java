package io.xlorey.fluxloader.events;

import se.krka.kahlua.vm.KahluaTable;
import zombie.characters.IsoPlayer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the player sends a command to the server.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnClientCommand extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnClientCommand"; }
    
    /**
    * Called Event Handling Method
    * @param module The name of the module for this client command.
    * @param command The text of the actual client command.
    * @param player The player who initiated the client command.
    * @param args The arguments of the client command.
    */
    public void handleEvent(String module, String command, IsoPlayer player, KahluaTable args) {}
}
