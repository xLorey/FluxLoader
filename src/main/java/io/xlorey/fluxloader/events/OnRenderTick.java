package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered every time the display is being rendered.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnRenderTick extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnRenderTick"; }
    
    /**
    * Called Event Handling Method
    */
    public void handleEvent() {}
}
