package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Same as OnTick, but triggered when the game is paused as well.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnTickEvenPaused extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnTickEvenPaused"; }
    
    /**
    * Called Event Handling Method
    * @param numberTicks The number of ticks.
    */
    public void handleEvent(Double numberTicks) {}
}
