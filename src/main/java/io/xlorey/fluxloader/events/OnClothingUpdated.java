package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a character's clothing items are updated.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnClothingUpdated extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnClothingUpdated"; }
    
    /**
    * Called Event Handling Method
    * @param playerOrCharacter The character whose clothing has been updated.
    */
    public void handleEvent(IsoPlayer playerOrCharacter) {}
}
