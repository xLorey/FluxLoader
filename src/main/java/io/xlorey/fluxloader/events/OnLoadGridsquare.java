package io.xlorey.fluxloader.events;

import zombie.iso.IsoGridSquare;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a square is being loaded.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnLoadGridsquare extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "LoadGridsquare"; }
    
    /**
    * Called Event Handling Method
    * @param square The grid square that is being loaded.
    */
    public void handleEvent(IsoGridSquare square) {}
}
