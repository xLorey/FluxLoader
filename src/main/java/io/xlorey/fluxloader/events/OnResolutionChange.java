package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when game resolution has changed.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnResolutionChange extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnResolutionChange"; }
    
    /**
    * Called Event Handling Method
    * @param oldWidth The old width of the screen.
    * @param oldHeight The old height of the screen.
    * @param newWidth The new width of the screen.
    * @param newHeight The new height of the screen.
    */
    public void handleEvent(Integer oldWidth, Integer oldHeight, Integer newWidth, Integer newHeight) {}
}
