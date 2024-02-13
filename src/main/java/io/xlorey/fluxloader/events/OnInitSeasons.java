package io.xlorey.fluxloader.events;

import zombie.erosion.season.ErosionSeason;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when the seasons have been initialized.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnInitSeasons extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnInitSeasons"; }
    
    /**
    * Called Event Handling Method
    * @param erosionSeason The season to be initialized.
    */
    public void handleEvent(ErosionSeason erosionSeason) {}
}
