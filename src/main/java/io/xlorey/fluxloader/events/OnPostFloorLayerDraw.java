package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered after a floor layer is rendered.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnPostFloorLayerDraw extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnPostFloorLayerDraw"; }
    
    /**
    * Called Event Handling Method
    * @param z The z coordinate of the layer which was rendered.
    */
    public void handleEvent(Integer z) {}
}
