package io.xlorey.fluxloader.events;

import zombie.iso.IsoGridSquare;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a grid square is being reused.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnReuseGridsquare extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "ReuseGridsquare"; }
    
    /**
    * Called Event Handling Method
    * @param square The grid square that is going to be reused.
    */
    public void handleEvent(IsoGridSquare square) {}
}
