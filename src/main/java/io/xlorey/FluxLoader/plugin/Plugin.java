package io.xlorey.fluxloader.plugin;

import io.xlorey.fluxloader.interfaces.IPlugin;
import io.xlorey.fluxloader.utils.Logger;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
    @Getter
    private Configuration config;

    /**
     * Field containing information about the plugin.
     * Metadata can include various information about the plugin, such as name, version, author, etc.
     */
    @Setter
    @Getter
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
     * @return A File object pointing to the normalized path to the plugin configuration directory.
     */
    public File getConfigFolder() {
        return getMetadata().getConfigFolder();
    }

    /**
     * Returns the absolute path to the configuration file with the specified name.
     * The path is based on the plugin configuration folder and the specified configuration file name.
     * @param configName The name of the configuration file. This can simply be a file name (for example, "config.yml")
     * without specifying the full path.
     * @return A string containing the absolute path to the configuration file.
     */
    public String getConfigPath(String configName) {
        configName = validateConfigName(configName);
        return new File(getMetadata().getConfigFolder().getAbsolutePath() + File.separator + configName).getAbsolutePath();
    }

    /**
     * Returns a {@link File} object representing the configuration file.
     * The method constructs the path to the configuration file using the plugin configuration directory
     * and the provided configuration file name.
     * @param configName The name of the configuration file. Must be specified without a path, for example "config.yml".
     * @return A {@link File} object pointing to the configuration file with the specified name.
     */
    public File getConfigFile(String configName) {
        configName = validateConfigName(configName);
        return new File(getMetadata().getConfigFolder().getAbsolutePath() + File.separator + configName);
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
    public void saveDefaultConfig() {
        File defaultConfig = getConfigFile("config.yml");

        if (!defaultConfig.exists()) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.yml");
                 FileOutputStream out = new FileOutputStream(defaultConfig)) {
                if (in == null) {
                    Logger.print(String.format("Could not find 'config.yml' in JAR resources for plugin '%s'",
                            getMetadata().getId()));
                    config = null;
                    return;
                }

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = new Configuration(defaultConfig.getAbsolutePath(), this);
        getConfig().load();
    }
}