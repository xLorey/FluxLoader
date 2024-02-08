package io.xlorey.fluxloader.events;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a player fails to join a cooperative game.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnCoopJoinFailed extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnCoopJoinFailed"; }
    
    /**
    * Called Event Handling Method
    * @param playerId The identifier of the player who was denied access to join the cooperative game. It can be either 0, 1, 2, or 3.
    */
    public void handleEvent(Integer playerId) {}
}
