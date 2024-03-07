package io.xlorey.fluxloader.plugin;

import io.xlorey.fluxloader.interfaces.IPlugin;
import io.xlorey.fluxloader.shared.LuaManager;
import io.xlorey.fluxloader.shared.TranslationManager;
import io.xlorey.fluxloader.utils.Constants;

import java.io.File;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Base class of all plugins
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class Plugin implements IPlugin {
    /**
     * Standard plugin configuration file
     * Returns null if the configuration file was not saved/loaded
     */
    private Configuration config;

    /**
     * Field containing information about the plugin.
     * Metadata can include various information about the plugin, such as name, version, author, etc.
     */
    private Metadata metadata;

    /**
     * Plugin initialization event
     */
    @Override
    public void onInitialize() {}

    /**
     * Plugin start event
     */
    @Override
    public void onExecute() {}

    /**
     * Plugin termination event
     */
    @Override
    public void onTerminate() {}

    /**
     * Obtaining translation from loaded plugin translation resource files (./translation/{country_code}.yml).
     * The language of the resulting translation is set from the current one in the client/server.
     * If there is no translation for a given language, the translation for the default language (EN) is returned,
     * if there is no translation for the key at all, just the key is returned.
     * @param key translation key (if the translation is nested, indicated with a dot, for example, "test.text")
     * @return translation for the given key
     */
    public final String getTranslate(String key) {
        return TranslationManager.getTranslate(metadata.getId(), key);
    }

    /**
     * Returns plugin metadata.
     * @return Plugin {@link Metadata} .
     */
    public final Metadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the plugin metadata.
     * @param metadata Plugin {@link Metadata} to install.
     */
    public final void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Returns the configuration object for this plugin.
     * @return A {@link Configuration} object representing the plugin configuration.
     */
    public final Configuration getConfig() {
        return config;
    }

    /**
     * Checks if the supplied configuration file name ends in ".yml".
     * If the file name does not end in ".yml", this extension is added.
     * @param configName Name of the configuration file to check and modify.
     * @return The name of the configuration file with the ".yml" extension appended if it was missing.
     */
    private String validateConfigName(String configName) {
        if (!configName.endsWith(".yml")) {
            configName += ".yml";
        }
        return configName;
    }

    /**
     * Returns a File object representing the configuration directory for this plugin.
     * The directory path is normalized to prevent problems with various file systems.
     * @return A {@link File} object pointing to the normalized path to the plugin configuration directory.
     */
    public final File getConfigFolder() {
        return metadata.getConfigFolder();
    }

    /**
     * Returns the absolute path to the configuration file with the specified name.
     * The path is based on the plugin configuration folder and the specified configuration file name.
     * @param configName The name of the configuration file. This can simply be a file name (for example, "config.yml")
     * without specifying the full path.
     * @return A string containing the absolute path to the configuration file.
     */
    public final String getConfigPath(String configName) {
        configName = validateConfigName(configName);
        return new File(metadata.getConfigFolder().getAbsolutePath() + File.separator + configName).getAbsolutePath();
    }

    /**
     * Returns a {@link File} object representing the configuration file.
     * The method constructs the path to the configuration file using the plugin configuration directory
     * and the provided configuration file name.
     * @param configName The name of the configuration file. Must be specified without a path, for example "config.yml".
     * @return A {@link File} object pointing to the configuration file with the specified name.
     */
    public final File getConfigFile(String configName) {
        configName = validateConfigName(configName);
        return new File(metadata.getConfigFolder().getAbsolutePath() + File.separator + configName);
    }

    /**
     * Loads Lua scripts from the resources folder with the default option to overwrite events.
     */
    public final void loadLua() {
        loadLua(true);
    }

    /**
     * Loads Lua scripts from the resources folder
     * @param isRewriteEvents true if events should be rewritten, false otherwise
     */
    public final void loadLua(boolean isRewriteEvents) {
        if (!metadata.getLuaFolder().toFile().exists()) return;

        LuaManager.loadLuaFromFolder(metadata.getLuaFolder().toAbsolutePath().toString(), isRewriteEvents);
    }

    /**
     * Saves the default configuration for the plugin.
     * If the configuration file (config.yml) does not exist in the plugin configuration directory,
     * it will be copied from the internal resources of the plugin JAR file.
     * After copying the file, if necessary, the configuration is loaded.
     * If the file already exists, the method simply loads the configuration from that file.
     * If the 'config.yml' file is not found in the JAR resources, or if errors occur when reading or writing this file
     * into the configuration file, then null is set to the config location.
     */
    public final void saveDefaultConfig() {
        config = new Configuration(getConfigPath(Constants.PLUGINS_DEFAULT_CONFIG_NAME), this);
        config.load();
    }
}