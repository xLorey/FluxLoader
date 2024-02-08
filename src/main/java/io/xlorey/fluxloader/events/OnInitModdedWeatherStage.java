package io.xlorey.fluxloader.events;

import zombie.iso.weather.WeatherPeriod;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the modded weather state is being initialized.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnInitModdedWeatherStage extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnInitModdedWeatherStage"; }
    
    /**
    * Called Event Handling Method
    * @param weatherPeriod The weather period of this weather stage.
    * @param weatherStage The weather stage to be initialized.
    * @param airFrontStrength TODO
    */
    public void handleEvent(WeatherPeriod weatherPeriod, WeatherPeriod.WeatherStage weatherStage, Float airFrontStrength) {}
}
