package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the server global object system is being initialized.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnSGlobalObjectSystemInit extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnSGlobalObjectSystemInit"; }
    
    /**
    * Called Event Handling Method
    */
    public void handleEvent() {}
}
