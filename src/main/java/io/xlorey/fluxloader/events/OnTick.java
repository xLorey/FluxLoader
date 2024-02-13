package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered every tick, try to not use this one, use EveryTenMinutes instead because it can create a lot of frame loss/garbage collection.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnTick extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnTick"; }
    
    /**
    * Called Event Handling Method
    * @param numberTicks The number of ticks.
    */
    public void handleEvent(Double numberTicks) {}
}
