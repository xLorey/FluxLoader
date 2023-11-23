package io.xlorey.FluxLoader.client.core;

import io.xlorey.FluxLoader.shared.PluginManager;
import io.xlorey.FluxLoader.utils.Logger;

/**
 * FluxLoader Core
 */
public class Core {
    /**
     * Initializing the loader
     */
    public static void init() {
        Logger.printCredits();

        Logger.print("FluxLoader Core initialization...");

        PluginManager.init();
    }
}
