package io.xlorey.fluxloader.events;

import zombie.network.Server;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: TODO
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnSteamServerResponded2 extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnSteamServerResponded2"; }
    
    /**
    * Called Event Handling Method
    * @param host TODO
    * @param port TODO
    * @param server TODO
    */
    public void handleEvent(String host, Integer port, Server server) {}
}
