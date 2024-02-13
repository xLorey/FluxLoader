package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when two characters collide together.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnCharacterCollide extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnCharacterCollide"; }
    
    /**
    * Called Event Handling Method
    * @param player The character who's colliding with another character.
    * @param character The character who's being collided with.
    */
    public void handleEvent(IsoGameCharacter player, IsoGameCharacter character) {}
}
