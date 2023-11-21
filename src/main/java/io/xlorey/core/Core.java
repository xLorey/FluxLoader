package io.xlorey.core;

import io.xlorey.utils.Logger;

/**
 * FluxLoader Core
 */
public class Core {
    /**
     * Core instance
     */
    private static Core instance;

    /**
     * Blocking creation from outside
     */
    private Core(){}

    /**
     * Getting a core instance
     * @return fluxloader core
     */
    public static Core getInstance() {
        if (instance == null) {
            instance = new Core();
        }
        return instance;
    }

    /**
     * Initializing the loader
     */
    public void init() {
        Logger.print("FluxLoader Core initialization...");
    }
}
