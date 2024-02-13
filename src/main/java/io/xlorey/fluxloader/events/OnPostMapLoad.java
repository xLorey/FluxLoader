package io.xlorey.fluxloader.events;

import zombie.iso.IsoCell;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered after a cell is loaded.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnPostMapLoad extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnPostMapLoad"; }
    
    /**
    * Called Event Handling Method
    * @param cell The cell which was loaded.
    * @param worldX The world x coordinate of the cell which was loaded.
    * @param worldY The world y coordinate of the cell which was loaded.
    */
    public void handleEvent(IsoCell cell, Integer worldX, Integer worldY) {}
}
