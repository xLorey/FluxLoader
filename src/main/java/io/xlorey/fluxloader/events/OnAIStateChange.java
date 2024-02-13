package io.xlorey.fluxloader.events;

import zombie.ai.State;
import zombie.characters.IsoGameCharacter;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered before an AI state is being changed.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnAIStateChange extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnAIStateChange"; }
    
    /**
    * Called Event Handling Method
    * @param character The character whose AI state is being changed.
    * @param newState The new AI state.
    * @param oldState The old AI state.
    */
    public void handleEvent(IsoGameCharacter character, State newState, State oldState) {}
}
