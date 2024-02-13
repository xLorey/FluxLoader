package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the command is sent to the server console.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnSendConsoleCommand extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onSendConsoleCommand"; }
    
    /**
    * Called Event Handling Method
    * @param command command sent to the console including arguments, i.e. the entire string sent to the console
    */
    public void handleEvent(String command) {}
}
