package io.xlorey.fluxloader.events;

import zombie.inventory.types.Food;
import zombie.iso.IsoGridSquare;
import zombie.iso.objects.IsoDeadBody;
import zombie.iso.objects.IsoWorldInventoryObject;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a container is being updated.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnContainerUpdate extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnContainerUpdate"; }
    
    /**
    * Called Event Handling Method
    * @param container The container which is being updated.
    */
    public void handleEvent(Food container) {}

    /**
    * Called Event Handling Method
    * @param container The container which is being updated.
    */
    public void handleEvent(IsoDeadBody container) {}

    /**
     * Called Event Handling Method
     * @param container The container which is being updated.
     */
    public void handleEvent(IsoGridSquare container) {}

    /**
     * Called Event Handling Method
     * @param container The container which is being updated.
     */
    public void handleEvent(IsoWorldInventoryObject container) {}
}
