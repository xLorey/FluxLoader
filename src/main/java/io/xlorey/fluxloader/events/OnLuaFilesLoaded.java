package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 29.02.2024
 * Description: Triggered when a standard group of Lua files is loaded.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnLuaFilesLoaded extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onLuaFilesLoaded"; }
    
    /**
    * Called Event Handling Method
    * @param groupName Lua file group names: shared, client or server
    */
    public void handleEvent(String groupName) {}
}
