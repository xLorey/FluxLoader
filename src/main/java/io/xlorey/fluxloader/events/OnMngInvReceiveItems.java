package io.xlorey.fluxloader.events;

import se.krka.kahlua.vm.KahluaTable;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the game client is receiving inventory items from the server.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnMngInvReceiveItems extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "MngInvReceiveItems"; }
    
    /**
    * Called Event Handling Method
    * @param items The items that are being received.
    */
    public void handleEvent(KahluaTable items) {}
}
