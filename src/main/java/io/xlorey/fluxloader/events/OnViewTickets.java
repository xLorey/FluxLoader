package io.xlorey.fluxloader.events;

import zombie.core.network.ByteBufferWriter;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the game client is receiving tickets from the server.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnViewTickets extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "ViewTickets"; }
    
    /**
    * Called Event Handling Method
    * @param tickets The buffer where to write the tickets.
    */
    public void handleEvent(ByteBufferWriter tickets) {}
}
