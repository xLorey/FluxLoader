package io.xlorey.fluxloader.events;

import se.krka.kahlua.vm.KahluaTable;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered after spawn regions are loaded.
This event is triggered on the server.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnSpawnRegionsLoaded extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnSpawnRegionsLoaded"; }
    
    /**
    * Called Event Handling Method
    * @param spawnRegions A table of spawn regions that have been loaded.
    */
    public void handleEvent(KahluaTable spawnRegions) {}
}
