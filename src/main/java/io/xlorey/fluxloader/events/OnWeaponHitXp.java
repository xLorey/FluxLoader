package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;
import zombie.characters.IsoPlayer;
import zombie.inventory.types.HandWeapon;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player is gaining XP for a successful hit.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnWeaponHitXp extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnWeaponHitXp"; }
    
    /**
    * Called Event Handling Method
    * @param player The player who's wielding the weapon.
    * @param handWeapon The hand weapon used to perform the attack.
    * @param character The character who's being hit.
    * @param damageSplit The damage split of the hit.
    */
    public void handleEvent(IsoPlayer player, HandWeapon handWeapon, IsoGameCharacter character, Float damageSplit) {}
}
