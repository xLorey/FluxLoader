package io.xlorey.fluxloader.client.core;

import io.xlorey.fluxloader.client.handlers.OnInitWorldHandler;
import io.xlorey.fluxloader.client.handlers.OnMainMenuEnterHandler;
import io.xlorey.fluxloader.client.handlers.OnPostTickRenderThreadHandler;
import io.xlorey.fluxloader.client.handlers.OnPostUIDrawHandler;
import io.xlorey.fluxloader.enums.EventPriority;
import io.xlorey.fluxloader.plugin.PluginManager;
import io.xlorey.fluxloader.shared.EventManager;

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
     * Initializing the loader. Called after loading mods and basic initializations,
     * namely at the moment after the Translator is initialized
     * @exception Exception in cases of unsuccessful core initialization
     */
    public static void init() throws Exception {
        EventManager.subscribe(new OnInitWorldHandler(), EventPriority.HIGHEST);
        EventManager.subscribe(new OnMainMenuEnterHandler(), EventPriority.HIGHEST);
        EventManager.subscribe(new OnPostTickRenderThreadHandler(), EventPriority.HIGHEST);
        EventManager.subscribe(new OnPostUIDrawHandler(), EventPriority.HIGHEST);

        PluginManager.loadPlugins(true);

        WidgetManager.init();
    }
}