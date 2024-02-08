package io.xlorey.fluxloader.events;

import zombie.characters.IsoPlayer;

import java.util.ArrayList;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player is receiving a list of items from another player.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnReceiveItemListNet extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnReceiveItemListNet"; }
    
    /**
    * Called Event Handling Method
    * @param sender The player who's sending the item list.
    * @param itemList The list of items that is being received.
    * @param receiver The player who's receiving the item list.
    * @param sessionId The session identifier for the transaction.
    * @param custom TODO
    */
    public void handleEvent(IsoPlayer sender, ArrayList itemList, IsoPlayer receiver, String sessionId, String custom) {}
}
