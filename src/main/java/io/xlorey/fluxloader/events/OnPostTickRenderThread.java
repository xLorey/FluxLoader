package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when every tick after all elements have been rendered. Called from the render thread.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnPostTickRenderThread extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onPostTickRenderThread"; }
    
    /**
    * Called Event Handling Method
    */
    public void handleEvent() {}
}
