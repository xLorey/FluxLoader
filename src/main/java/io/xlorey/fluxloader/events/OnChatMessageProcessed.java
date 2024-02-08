package io.xlorey.fluxloader.events;

import zombie.chat.ChatBase;
import zombie.chat.ChatMessage;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player sends a chat message.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnChatMessageProcessed extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onChatMessageProcessed"; }
    
    /**
    * Called Event Handling Method
    * @param chatBase Target chat data.
    * @param chatMessage Sent message details.
    */
    public void handleEvent(ChatBase chatBase, ChatMessage chatMessage) {}
}
