package io.xlorey.fluxloader.events;

import zombie.characters.BodyDamage.BodyPartType;
import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoZombie;
import zombie.inventory.types.HandWeapon;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a character hits a zombie.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnHitZombie extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnHitZombie"; }
    
    /**
    * Called Event Handling Method
    * @param zombie The zombie that is being hit.
    * @param character The character who's hitting the zombie.
    * @param bodyPartType The body part where the zombie was hit.
    * @param handWeapon The hand weapon used to hit the zombie.
    */
    public void handleEvent(IsoZombie zombie, IsoGameCharacter character, BodyPartType bodyPartType, HandWeapon handWeapon) {}
}
