package io.xlorey.fluxloader.plugin;

import io.xlorey.fluxloader.interfaces.IControlsWidget;
import zombie.core.textures.Texture;

import java.util.HashMap;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 10.02.2024
 * Description: Centralized storage of information about plugins, their instances, etc.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class PluginRegistry {
    /**
     * Registry of icons of loaded plugins
     * Key: plugin id
     *Value: texture object
     */
    private static final HashMap<String, Texture> pluginIconRegistry = new HashMap<>();

    /**
     * Register of loaded plugin controls
     * Key: plugin id
     * Value: Controls widget
     */
    private static final HashMap<String, IControlsWidget> pluginControlsRegistry = new HashMap<>();

    /**
     * Register of all loaded client plugins
     * Key: "entryPoint:ID:Version"
     * Value: plugin instance
     */
    private static final HashMap<String, Plugin> clientPluginsRegistry = new HashMap<>();

    /**
     * Register of all loaded server plugins
     * Key: "entryPoint:ID:Version"
     * Value: plugin instance
     */
    private static final HashMap<String, Plugin> serverPluginsRegistry = new HashMap<>();

    /**
     * Retrieves the list of loaded client plugins controls
     * @return HashMap containing information about loaded client plugin icons, where the key is the plugin ID.
     *         and the value is the corresponding ControlsWidget instance.
     */
    public static HashMap<String, IControlsWidget> getPluginControlsRegistry() {
        return pluginControlsRegistry;
    }

    /**
     * Retrieves the list of loaded client plugins icons
     * @return HashMap containing information about loaded client plugin icons, where the key is the plugin ID.
     *         and the value is the corresponding texture instance.
     */
    public static HashMap<String, Texture> getPluginIconRegistry() {
        return pluginIconRegistry;
    }

    /**
     * Retrieves the list of loaded client plugins
     * @return A HashMap containing information about loaded client plugins, where the key is "entryPoint:ID:Version"
     *         and the value is the corresponding Plugin instance.
     */
    public static HashMap<String, Plugin> getLoadedClientPlugins() {
        return clientPluginsRegistry;
    }
    /**
     * Retrieves the list of loaded server plugins
     * @return A HashMap containing information about loaded server plugins, where the key is "entryPoint:ID:Version"
     *         and the value is the corresponding Plugin instance.
     */
    public static HashMap<String, Plugin> getLoadedServerPlugins() {
        return serverPluginsRegistry;
    }

    /**
     * Adds a plugin icon to the registry.
     * @param pluginId Plugin ID
     * @param texture Texture of the plugin icon
     */
    public static void addPluginIcon(String pluginId, Texture texture) {
        pluginIconRegistry.put(pluginId, texture);
    }

    /**
     * Adds plugin controls to the registry.
     * @param pluginId Plugin ID
     * @param controlsWidget An instance of the plugin controls
     */
    public static void addPluginControls(String pluginId, IControlsWidget controlsWidget) {
        pluginControlsRegistry.put(pluginId, controlsWidget);
    }

    /**
     * Adds a client plugin instance to the registry.
     * @param pluginId Plugin ID, including entry point, ID and version (ENTRYPOINT:ID:VERSION)
     * @param plugin Plugin instance
     */
    public static void addClientPlugin(String pluginId, Plugin plugin) {
        clientPluginsRegistry.put(pluginId, plugin);
    }

    /**
     * Adds an instance of the server plugin to the registry.
     * @param pluginId Plugin ID, including entry point, ID and version (ENTRYPOINT:ID:VERSION)
     * @param plugin Plugin instance
     */
    public static void addServerPlugin(String pluginId, Plugin plugin) {
        serverPluginsRegistry.put(pluginId, plugin);
    }

    /**
     * Returns the plugin icon by its identifier.
     * @param pluginId Plugin ID
     * @return The texture of the plugin icon, if found; otherwise null.
     */
    public static Texture getPluginIcon(String pluginId) {
        return pluginIconRegistry.get(pluginId);
    }

    /**
     * Returns plugin controls by its identifier.
     * @param pluginId Plugin ID
     * @return An instance of the plugin controls, if found; otherwise null.
     */
    public static IControlsWidget getPluginControls(String pluginId) {
        return pluginControlsRegistry.get(pluginId);
    }
}
