package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoZombie;
import zombie.iso.IsoGridSquare;
import zombie.iso.objects.IsoDoor;
import zombie.iso.objects.IsoThumpable;
import zombie.iso.objects.IsoWindow;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a character collides with an object.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnObjectCollide extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnObjectCollide"; }
    
    /**
    * Called Event Handling Method
    * @param character The character who's colliding with another object.
    * @param door The object that is being collided with.
    */
    public void handleEvent(IsoGameCharacter character, IsoDoor door) {}

    /**
     * Called Event Handling Method
     * @param character The character who's colliding with another object.
     * @param door The object that is being collided with.
     */
    public void handleEvent(IsoGameCharacter character, IsoGridSquare door) {}

    /**
     * Called Event Handling Method
     * @param character The character who's colliding with another object.
     * @param door The object that is being collided with.
     */
    public void handleEvent(IsoGameCharacter character, IsoThumpable door) {}

    /**
     * Called Event Handling Method
     * @param character The character who's colliding with another object.
     * @param door The object that is being collided with.
     */
    public void handleEvent(IsoGameCharacter character, IsoWindow door) {}

    /**
     * Called Event Handling Method
     * @param character The character who's colliding with another object.
     * @param door The object that is being collided with.
     */
    public void handleEvent(IsoGameCharacter character, IsoZombie door) {}
}
