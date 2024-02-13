package io.xlorey.fluxloader.events;

import zombie.iso.IsoChunk;
import zombie.iso.IsoGridSquare;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when a building tile is being set.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnDoTileBuilding2 extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnDoTileBuilding2"; }
    
    /**
    * Called Event Handling Method
    * @param chunk The chunk in which the tile is being set.
    * @param render Whether the tile should be rendered or not.
    * @param x The x coordinate of the tile being set.
    * @param y The y coordinate of the tile being set.
    * @param z The z coordinate of the tile being set.
    * @param square The grid square where the tile is being set.
    */
    public void handleEvent(IsoChunk chunk, Boolean render, Integer x, Integer y, Integer z, IsoGridSquare square) {}
}
