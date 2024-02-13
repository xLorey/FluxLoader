package io.xlorey.fluxloader.events;

import se.krka.kahlua.vm.KahluaTable;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a command from the server is being received.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnServerCommand extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnServerCommand"; }
    
    /**
    * Called Event Handling Method
    * @param module The name of the module for this server command.
    * @param command The text of the actual server command.
    * @param arguments The list of arguments of the server command.
    */
    public void handleEvent(String module, String command, KahluaTable arguments) {}
}
