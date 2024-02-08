package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a Steam invite has been accepted.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnAcceptInvite extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnAcceptInvite"; }
    
    /**
    * Called Event Handling Method
    * @param connectionString The connection string.
    */
    public void handleEvent(String connectionString) {}
}
