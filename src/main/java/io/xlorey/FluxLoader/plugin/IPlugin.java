package io.xlorey.FluxLoader.plugin;

/**
 * Base class of all loader plugins
 */
public interface IPlugin {
    /**
     * Plugin initialization event
     */
    void onInitialize();
}
