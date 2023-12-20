package io.xlorey.FluxLoader.plugin;

import io.xlorey.FluxLoader.interfaces.IPlugin;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class of all plugins
 */
@Setter
@Getter
public class Plugin implements IPlugin {

    /**
     * Field containing information about the plugin.
     * Metadata can include various information about the plugin, such as name, version, author, etc.
     */
    private Metadata metadata;

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
}
