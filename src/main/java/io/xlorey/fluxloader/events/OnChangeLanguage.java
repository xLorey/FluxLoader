package io.xlorey.fluxloader.events;

import zombie.core.Language;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 29.02.2024
 * Description: Triggered when the language is changed through the settings
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnChangeLanguage extends Event {
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onChangeLanguage"; }
    
    /**
    * Called Event Handling Method
    * @param language New language information
    */
    public void handleEvent(Language language) {}
}
