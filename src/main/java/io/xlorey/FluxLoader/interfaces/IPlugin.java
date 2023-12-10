package io.xlorey.FluxLoader.interfaces;

/**
 * Base class of all loader plugins
 */
public interface IPlugin {
    /**
     * Plugin initialization event
     */
    void onInitialize();

    /**
     * Plugin activation event
     * Called when the plugin is enabled through the main game menu
     */
    void onEnable();

    /**
     * Plugin deactivation event
     * Called when the plugin is disabled through the main game menu
     */
    void onDisable();
}
