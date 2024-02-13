package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a Steam workshop item couldn't be created.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnSteamWorkshopItemNotCreated extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnSteamWorkshopItemNotCreated"; }
    
    /**
    * Called Event Handling Method
    * @param result The result code indicating why the workshop item was not created.
    */
    public void handleEvent(Integer result) {}
}
