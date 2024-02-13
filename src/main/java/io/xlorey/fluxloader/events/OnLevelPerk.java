package io.xlorey.fluxloader.events;

import zombie.characters.IsoGameCharacter;
import zombie.characters.skills.PerkFactory;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a perk is being leveled up.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnLevelPerk extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "LevelPerk"; }
    
    /**
    * Called Event Handling Method
    * @param character The character whose perk is being leveled up or down.
    * @param perk The perk being leveled up or down.
    * @param level Perk level.
    * @param levelUp Whether the perk is being leveled up.
    */
    public void handleEvent(IsoGameCharacter character, PerkFactory.Perk perk, Integer level, Boolean levelUp) {}
}
