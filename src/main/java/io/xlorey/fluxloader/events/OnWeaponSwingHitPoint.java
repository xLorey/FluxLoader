package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;
import zombie.inventory.types.HandWeapon;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a hand weapon has reached the apex of its swing.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnWeaponSwingHitPoint extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnWeaponSwingHitPoint"; }
    
    /**
    * Called Event Handling Method
    * @param character The character who's wielding the weapon.
    * @param handWeapon The hand weapon that is being wielded.
    */
    public void handleEvent(IsoGameCharacter character, HandWeapon handWeapon) {}
}
