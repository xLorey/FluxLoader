package io.xlorey.fluxloader.events;

import se.krka.kahlua.vm.KahluaTable;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the forage definitions are being added.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnAddForageDefs extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onAddForageDefs"; }
    
    /**
    * Called Event Handling Method
    * @param forageSystem The forage system object
    */
    public void handleEvent(KahluaTable forageSystem) {}
}
