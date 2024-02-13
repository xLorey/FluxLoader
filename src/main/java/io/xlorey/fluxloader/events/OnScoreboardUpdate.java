package io.xlorey.fluxloader.events;

import java.util.ArrayList;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when multiplayer scoreboard is updated.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnScoreboardUpdate extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnScoreboardUpdate"; }
    
    /**
    * Called Event Handling Method
    * @param playerNames The list of player names which are being updated on the score board.
    * @param displayNames The list of display names for the players which are being updated on the score board.
    * @param steamIds The list of Steam identifiers of the players which are being updated on the score board.
    */
    public void handleEvent(ArrayList playerNames, ArrayList displayNames, ArrayList steamIds) {}
}
