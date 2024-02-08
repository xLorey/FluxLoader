package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;
import zombie.characters.skills.PerkFactory;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player gains XP.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnAddXP extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "AddXP"; }
    
    /**
    * Called Event Handling Method
    * @param character The character who's gaining XP.
    * @param perk The perk that is being leveled up.
    * @param level The perk level gained.
    */
    public void handleEvent(IsoGameCharacter character, PerkFactory.Perk perk, Float level) {}
}
