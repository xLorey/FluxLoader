package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a faction is being synced by the server on client side.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnSyncFaction extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "SyncFaction"; }
    
    /**
    * Called Event Handling Method
    * @param factionName The name of the faction which is going to get synchronized.
    */
    public void handleEvent(String factionName) {}
}
