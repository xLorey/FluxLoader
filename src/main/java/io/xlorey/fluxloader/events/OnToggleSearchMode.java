package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when search mode is being toggled.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnToggleSearchMode extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onToggleSearchMode"; }
    
    /**
    * Called Event Handling Method
    * @param player The player who's toggling search mode.
    * @param isSearchMode Whether search mode is being enabled or disabled.
    */
    public void handleEvent(IsoPlayer player, Boolean isSearchMode) {}
}
