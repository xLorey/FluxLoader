package io.xlorey.fluxloader.events;

import zombie.iso.sprite.IsoSpriteManager;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered after tiles definitions have been loaded.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnLoadedTileDefinitions extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnLoadedTileDefinitions"; }
    
    /**
    * Called Event Handling Method
    * @param spriteManager The sprite manager.
    */
    public void handleEvent(IsoSpriteManager spriteManager) {}
}
