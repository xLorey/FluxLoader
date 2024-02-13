package io.xlorey.fluxloader.events;

import se.krka.kahlua.vm.KahluaTable;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the game client is receiving GlobalModData from the server.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnReceiveGlobalModData extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnReceiveGlobalModData"; }
    
    /**
    * Called Event Handling Method
    * @param key The key for the ModData that has been received.
    * @param modData The ModData that has been received.
    */
    public void handleEvent(String key, KahluaTable modData) {}
}
