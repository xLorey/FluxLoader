package io.xlorey.fluxloader.events;

import zombie.chat.ChatMessage;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a chat message is being sent.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnAddMessage extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnAddMessage"; }
    
    /**
    * Called Event Handling Method
    * @param chatMessage The chat message being added.
    * @param tabId The identifier of the tab in which the message is being added.
    */
    public void handleEvent(ChatMessage chatMessage, Short tabId) {}
}
