package io.xlorey.fluxloader.events;

import zombie.iso.IsoObject;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a sound is being played.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnWorldSound extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnWorldSound"; }
    
    /**
    * Called Event Handling Method
    * @param x The x coordinate of the sound.
    * @param y The y coordinate of the sound.
    * @param z The z coordinate of the sound.
    * @param radius The radius of the sound.
    * @param volume The volume of the sound.
    * @param source The object that triggered the sound.
    */
    public void handleEvent(Integer x, Integer y, Integer z, Integer radius, Integer volume, IsoObject source) {}
}
