package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a chat message from the server admin is being sent.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnAdminMessage extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnAdminMessage"; }
    
    /**
    * Called Event Handling Method
    * @param text The text of the message being received from the admin.
    * @param x The x coordinate where to display the message.
    * @param y The y coordinate where to display the message.
    * @param z The z coordinate where to display the message.
    */
    public void handleEvent(String text, Integer x, Integer y, Integer z) {}
}
