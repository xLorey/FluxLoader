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
     * Plugin activation event
     * Called when the plugin is enabled through the main game menu
     */
    @Override
    public void onEnable() {

    }

    /**
     * Plugin deactivation event
     * Called when the plugin is disabled through the main game menu
     */
    @Override
    public void onDisable() {

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
