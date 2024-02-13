package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;
import zombie.inventory.types.HandWeapon;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a character hits a tree with a hand weapon.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnWeaponHitTree extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnWeaponHitTree"; }
    
    /**
    * Called Event Handling Method
    * @param character The character whose weapon hit a tree.
    * @param handWeapon The hand weapon used to hit the tree.
    */
    public void handleEvent(IsoGameCharacter character, HandWeapon handWeapon) {}
}
