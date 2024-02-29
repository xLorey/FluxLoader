package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 29.02.2024
 * Description: Triggered when Lua script loaded.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnLuaScriptExecuted extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onLuaScriptExecuted"; }
    
    /**
    * Called Event Handling Method
    * @param filePath Path to Lua file
    * @param isRewriteEvents Flag indicating whether Lua events have been overwritten
    */
    public void handleEvent(String filePath, boolean isRewriteEvents) {}
}
