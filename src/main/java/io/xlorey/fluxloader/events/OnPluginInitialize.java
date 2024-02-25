package io.xlorey.fluxloader.events;

import io.xlorey.fluxloader.plugin.Plugin;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 24.02.2024
 * Description: Event that triggered when the plugin is initialized
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class OnPluginInitialize extends Event{
    /**
     * Getting the event name
     * @return name of the event being implemented
     */
    @Override
    public String getEventName() { return "onPluginInitialize"; }

    /**
     * Called Event Handling Method
     * @param plugin plugin that has been initialized
     */
    public void handleEvent(Plugin plugin) {}
}
