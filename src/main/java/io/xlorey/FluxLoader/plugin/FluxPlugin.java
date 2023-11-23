package io.xlorey.FluxLoader.plugin;

/**
 * Base class of all loader plugins
 */
public abstract class FluxPlugin {
    /**
     * Plugin initialization event
     */
    public abstract void onInitialize();

    /**
     * Getting the plugin ID
     * @return Plugin ID as a string
     */
    public abstract String getPluginId();

    /**
     * Getting the plugin name
     * @return plugin name
     */
    public abstract String getPluginName();

    /**
     * Getting the plugin version
     * @return string version of the plugin
     */
    public abstract String getPluginVersion();

    /**
     * Getting an array of dependencies for a given plugin
     * @return array of strings - ID - dependent plugins
     */
    public abstract String[] getPluginDependencies();
}
