package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Same as OnTick, except is only called while on the main menu.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnFETick extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnFETick"; }
    
    /**
    * Called Event Handling Method
    * @param numberTicks Always zero.
    */
    public void handleEvent(Double numberTicks) {}
}
