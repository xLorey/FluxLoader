package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a chat tab is closed.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnTabRemoved extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnTabRemoved"; }
    
    /**
    * Called Event Handling Method
    * @param tabTitle The name of the chat tab which was added.
    * @param tabId The identifier of the chat tab which was added.
    */
    public void handleEvent(String tabTitle, Integer tabId) {}
}
