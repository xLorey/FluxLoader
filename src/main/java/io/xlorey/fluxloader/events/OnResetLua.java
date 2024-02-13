package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when Lua is being reset.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnResetLua extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnResetLua"; }
    
    /**
    * Called Event Handling Method
    * @param reason The reason why Lua was reset.
    */
    public void handleEvent(String reason) {}
}
