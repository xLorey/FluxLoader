package io.xlorey.fluxloader.events;

import zombie.iso.weather.WeatherPeriod;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the modded weather sage is being updated.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnUpdateModdedWeatherStage extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnUpdateModdedWeatherStage"; }
    
    /**
    * Called Event Handling Method
    * @param weatherPeriod The current weather period.
    * @param weatherStage The current stage of the weather.
    * @param strength The strength of the air front.
    */
    public void handleEvent(WeatherPeriod weatherPeriod, WeatherPeriod.WeatherStage weatherStage, Float strength) {}
}
