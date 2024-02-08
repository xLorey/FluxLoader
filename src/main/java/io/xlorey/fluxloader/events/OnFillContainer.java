package io.xlorey.fluxloader.events;

import zombie.inventory.ItemContainer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered after a container has been filled.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnFillContainer extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnFillContainer"; }
    
    /**
    * Called Event Handling Method
    * @param roomName The room name in which the container is installed.
    * @param containerType The type of the container that is being filled.
    * @param itemContainer The container that is being filled.
    */
    public void handleEvent(String roomName, String containerType, ItemContainer itemContainer) {}
}
