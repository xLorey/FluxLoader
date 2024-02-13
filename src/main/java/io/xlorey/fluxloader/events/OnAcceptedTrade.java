package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a trade request has been accepted.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnAcceptedTrade extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "AcceptedTrade"; }
    
    /**
    * Called Event Handling Method
    * @param accepted Whether the trade was accepted or not.
    */
    public void handleEvent(Boolean accepted) {}
}
