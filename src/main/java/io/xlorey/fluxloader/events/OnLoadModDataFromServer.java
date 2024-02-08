package io.xlorey.fluxloader.events;

import zombie.iso.IsoGridSquare;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered after ModData has been received from the server.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnLoadModDataFromServer extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onLoadModDataFromServer"; }
    
    /**
    * Called Event Handling Method
    * @param square The grid square whose ModData is getting loaded from the server.
    */
    public void handleEvent(IsoGridSquare square) {}
}
