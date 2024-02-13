package io.xlorey.fluxloader.events;

import zombie.iso.weather.ClimateManager;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered after the ClimateManager has been initialized.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnClimateManagerInit extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnClimateManagerInit"; }
    
    /**
    * Called Event Handling Method
    * @param climateManager The climate manager which is being initialized.
    */
    public void handleEvent(ClimateManager climateManager) {}
}
