package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: TODO
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnSteamServerFailedToRespond2 extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnSteamServerFailedToRespond2"; }
    
    /**
    * Called Event Handling Method
    * @param host TODO
    * @param port TODO
    */
    public void handleEvent(String host, Integer port) {}
}
