package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the weather is changing.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnChangeWeather extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnChangeWeather"; }
    
    /**
    * Called Event Handling Method
    * @param weather A string representing the weather. Can be either: "normal", "cloud", "rain", or "sunny"
    */
    public void handleEvent(String weather) {}
}
