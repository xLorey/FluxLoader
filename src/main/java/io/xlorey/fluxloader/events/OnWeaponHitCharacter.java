package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;
import zombie.inventory.types.HandWeapon;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a character has been hit by a weapon.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnWeaponHitCharacter extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnWeaponHitCharacter"; }
    
    /**
    * Called Event Handling Method
    * @param wielder The character whose weapon hit another character.
    * @param character The character who's been hit by another character.
    * @param handWeapon The hand weapon used to hit the character.
    * @param damage The damage inflicted to the character who's been hit.
    */
    public void handleEvent(IsoGameCharacter wielder, IsoGameCharacter character, HandWeapon handWeapon, Float damage) {}
}
