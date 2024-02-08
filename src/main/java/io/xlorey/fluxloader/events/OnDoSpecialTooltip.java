package io.xlorey.fluxloader.events;

import zombie.iso.IsoGridSquare;
import zombie.ui.ObjectTooltip;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a special tooltip is being rendered, after a user right-clicked an object.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnDoSpecialTooltip extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "DoSpecialTooltip"; }
    
    /**
    * Called Event Handling Method
    * @param objectTooltip The tooltip object to be filled.
    * @param square The grid square on which the tooltip has been triggered.
    */
    public void handleEvent(ObjectTooltip objectTooltip, IsoGridSquare square) {}
}
