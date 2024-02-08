package io.xlorey.fluxloader.client.core;

import io.xlorey.fluxloader.client.handlers.OnInitWorldHandler;
import io.xlorey.fluxloader.client.handlers.OnMainMenuEnterHandler;
import io.xlorey.fluxloader.plugin.PluginManager;
import io.xlorey.fluxloader.shared.EventManager;
import io.xlorey.fluxloader.utils.Logger;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Flux Loader client core
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class Core {
    /**
     * Flag indicating whether client plugins are running
     */
    public static boolean isPluginsExecuted = false;

    /**
     * Initializing the loader
     * @exception Exception in cases of unsuccessful core initialization
     */
    public static void init() throws Exception {
        Logger.printCredits();

        Logger.print("FluxLoader Core initialization for the client...");

        // Logic for enabling and disabling plugins
        EventManager.subscribe(new OnInitWorldHandler());
        EventManager.subscribe(new OnMainMenuEnterHandler());

        PluginManager.loadPlugins(true);
    }
}