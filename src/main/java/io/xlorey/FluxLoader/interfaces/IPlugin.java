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
     * Plugin start event
     */
    void onExecute();

    /**
     * Plugin termination event
     */
    void onTerminate();
}
