package io.xlorey.fluxloader.events;

import zombie.radio.scripting.RadioScriptManager;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Triggered when radio scripts are being loaded.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnLoadRadioScripts extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "OnLoadRadioScripts"; }
    
    /**
    * Called Event Handling Method
    * @param radioScriptManager The radio script manager.
    * @param worldInit True if the world has not yet been initialized.
    */
    public void handleEvent(RadioScriptManager radioScriptManager, Boolean worldInit) {}
}
