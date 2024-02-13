package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;
import zombie.inventory.types.HandWeapon;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a character is done performing an attack.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnPlayerAttackFinished extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnPlayerAttackFinished"; }
    
    /**
    * Called Event Handling Method
    * @param character The character who's finished attacking.
    * @param handWeapon The hand weapon used to perform the attack.
    */
    public void handleEvent(IsoGameCharacter character, HandWeapon handWeapon) {}
}
