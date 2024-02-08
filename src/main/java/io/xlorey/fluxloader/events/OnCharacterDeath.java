package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a character dies.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnCharacterDeath extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnCharacterDeath"; }
    
    /**
    * Called Event Handling Method
    * @param character The character who's about to die.
    */
    public void handleEvent(IsoGameCharacter character) {}
}
