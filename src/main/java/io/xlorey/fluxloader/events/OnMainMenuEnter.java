package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when main menu is displayed to users. This can occur either when they launch the game, or when they quit a running game.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnMainMenuEnter extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnMainMenuEnter"; }
    
    /**
    * Called Event Handling Method
    */
    public void handleEvent() {}
}
