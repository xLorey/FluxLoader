package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered after a new Steam workshop item was successfully created.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnSteamWorkshopItemCreated extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnSteamWorkshopItemCreated"; }
    
    /**
    * Called Event Handling Method
    * @param steamId The Steam identifier of the user who created the workshop item.
    * @param userNeedsToAcceptWorkshopLegalAgreement Whether the user has to accept the workshop legal agreement.
    */
    public void handleEvent(String steamId, Boolean userNeedsToAcceptWorkshopLegalAgreement) {}
}
