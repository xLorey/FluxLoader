package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered during the process of initializing the world.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnInitWorld extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnInitWorld"; }
    
    /**
    * Called Event Handling Method
    */
    public void handleEvent() {}
}
