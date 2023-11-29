package io.xlorey.FluxLoader.interfaces;

/**
 * Base class of all loader plugins
 */
public interface IPlugin {
    /**
     * Plugin initialization event
     */
    void onInitialize();
}
