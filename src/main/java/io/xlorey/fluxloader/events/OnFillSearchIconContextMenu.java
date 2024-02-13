package io.xlorey.fluxloader.events;

import se.krka.kahlua.vm.KahluaTable;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: TODO
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnFillSearchIconContextMenu extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onFillSearchIconContextMenu"; }
    
    /**
    * Called Event Handling Method
    * @param context The context menu to be filled.
    * @param baseIcon TODO
    */
    public void handleEvent(KahluaTable context, KahluaTable baseIcon) {}
}
