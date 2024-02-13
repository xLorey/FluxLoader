package io.xlorey.fluxloader.events;

import zombie.chat.ChatTab;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the default chat tab has been set.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnSetDefaultTab extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnSetDefaultTab"; }
    
    /**
    * Called Event Handling Method
    * @param chatTab The chat tab that is being set as default.
    */
    public void handleEvent(ChatTab chatTab) {}
}
