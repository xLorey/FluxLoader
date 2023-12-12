package io.xlorey.FluxLoader.plugin;

import io.xlorey.FluxLoader.interfaces.IPlugin;

/**
 * Base class of all plugins
 */
public class Plugin implements IPlugin {
    private PluginInfo pluginInfo;

    /**
     * Plugin initialization event
     */
    @Override
    public void onInitialize() {

    }

    /**
     * Plugin start event
     */
    @Override
    public void onExecute() {

    }

    /**
     * Plugin termination event
     */
    @Override
    public void onTerminate() {

    }

    /**
     * Getting information about a plugin
     * @return information about the plugin in PluginInfo format
     */
    public PluginInfo getPluginInfo() {
        return pluginInfo;
    }

    /**
     * Setting information about a plugin
     * @param pluginInfo metadata about the plugin in the form of PluginInfo
     */
    public void setPluginInfo(PluginInfo pluginInfo) {
        this.pluginInfo = pluginInfo;
    }
}
