package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the server is done saving.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnServerFinishSaving extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnServerFinishSaving"; }
    
    /**
    * Called Event Handling Method
    */
    public void handleEvent() {}
}
