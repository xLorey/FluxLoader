package io.xlorey.fluxloader.events;

import se.krka.kahlua.vm.KahluaTable;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: TODO
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnSteamRulesRefreshComplete extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnSteamRulesRefreshComplete"; }
    
    /**
    * Called Event Handling Method
    * @param host TODO
    * @param port TODO
    * @param rulesTable TODO
    */
    public void handleEvent(String host, Integer port, KahluaTable rulesTable) {}
}
