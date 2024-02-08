package io.xlorey.fluxloader.events;

import zombie.iso.IsoObject;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the amount of water in an object has changed.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnWaterAmountChange extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnWaterAmountChange"; }
    
    /**
    * Called Event Handling Method
    * @param object The object in which the water amount is changing.
    * @param waterAmount The amount of water that is being added or removed from the water container.
    */
    public void handleEvent(IsoObject object, Integer waterAmount) {}
}
