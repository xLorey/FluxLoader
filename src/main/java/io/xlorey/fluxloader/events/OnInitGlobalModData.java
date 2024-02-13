package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered after GlobalModData has been initialized.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnInitGlobalModData extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnInitGlobalModData"; }
    
    /**
    * Called Event Handling Method
    * @param isNewGame Whether this is a new game or not.
    */
    public void handleEvent(Boolean isNewGame) {}
}
