package io.xlorey.fluxloader.events;

import zombie.iso.weather.WeatherPeriod;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a weather period is complete.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnWeatherPeriodComplete extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnWeatherPeriodComplete"; }
    
    /**
    * Called Event Handling Method
    * @param weatherPeriod The weather period.
    */
    public void handleEvent(WeatherPeriod weatherPeriod) {}
}
