package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;
import zombie.iso.IsoGridSquare;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered after a new world has been initialized.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnNewGame extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnNewGame"; }
    
    /**
    * Called Event Handling Method
    * @param player The player who's starting the game.
    * @param square The grid square where the player is located.
    */
    public void handleEvent(IsoPlayer player, IsoGridSquare square) {}
}
