package io.xlorey.FluxLoader.plugin;

import io.xlorey.FluxLoader.utils.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;

/**
 * A set of tools for creating plugin configuration files
 */
public class Configuration {
    /**
     * Deserialized configuration file as a dictionary
     */
    private Map<String, Object> config = new LinkedHashMap<>();

    /**
     * Path to the configuration file
     */
    private final String configPath;

    /**
     * Configuration file name
     */
    private final String configName;

    /**
     * Plugin instance
     */
    private final Plugin plugin;

    /**
     * Constructor of the Configuration class.
     * This constructor initializes a new configuration instance for the plugin.
     * It sets the path to the configuration file and stores a link to the plugin instance.
     * The configuration file name is also determined based on the path provided.
     * @param configPath Path to the configuration file that will be used to load and save data.
     * @param plugin The plugin instance for which the configuration is being created. This instance is used
     * to interact with other plugin components and its environment.
     */
    public Configuration(String configPath, Plugin plugin) {
        this.configPath = configPath;
        this.plugin = plugin;

        File configFile = new File(configPath);
        this.configName = configFile.getName();
    }

    /**
     * Copies or loads a configuration file.
     * This method checks for the presence of a configuration file at the path specified in {@code configPath}.
     * If the file does not exist, the method tries to find and copy it from the JAR resources.
     * The file name to search for in the JAR is based on the name of the configuration file with a ".yml" extension.
     * If there is no file in the JAR resources, the method creates a new empty configuration file.
     * If the file already exists, the method simply loads the configuration from that file.
     */
    public void copyOrLoadConfig() {
        File configFile = new File(configPath);

        if (!isExists()) {
            String fileName = configFile.getName();

            try (InputStream in = plugin.getClass().getClassLoader().getResourceAsStream(fileName);
                 FileOutputStream out = new FileOutputStream(configFile)) {
                if (in == null) {
                    create();
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

        load();
    }

    /**
     * Reloads the configuration file.
     * This method saves the current configuration to the file, then reloads the configuration
     * from the file, effectively refreshing the configuration in memory.
     * After reloading, the updated configuration is available for use.
     */
    public void reload() {
        Logger.print("Reloading the configuration file: " + configName);

        save();
        load();

        Logger.print(String.format("Configuration file '%s' has been reloaded.", configName));
    }

    /**
     * Saves the current configuration to a file.
     * The method writes the contents of the configuration stored in memory to a YAML file.
     * The path to the configuration file is determined by the value of the {@code configPath} field.
     * If an I/O error occurs while writing to a file.
     * The exception is caught and output to the stacktrace, but is not thrown further.
     */
    public void save() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        try (FileWriter writer = new FileWriter(configPath)) {
            yaml.dump(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads configuration from a file.
     * Before reading a file, the method checks for its existence by calling {@code isExists()}.
     * If the file exists, the method reads the YAML file, the path to which is determined by the value of the {@code configPath} field,
     * and loads its contents into the {@code config} map.
     * If the file content is null, the configuration is not loaded.
     * If an I/O error occurs while reading a file,
     * the exception is caught and pushed onto the trace stack, but is not thrown further.
     */
    public void load() {
        if (!isExists()) {
            return;
        }

        Yaml yaml = new Yaml();
        try (FileInputStream inputStream = new FileInputStream(configPath)) {
            Map<String, Object> loadedConfig = yaml.load(inputStream);

            if (loadedConfig == null) return;

            config = loadedConfig;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks for the existence of a configuration file and creates it if it does not exist.
     * In case of an error, displays a stack trace.
     */
    public void create() {
        File configFile = new File(configPath);

        if (!configFile.exists()) {
            try {
                if (!configFile.createNewFile()) {
                    throw new IOException("Failed to create configuration file: " + configPath);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Logger.print("An error occurred while creating the configuration file!");
            }
        }
    }

    /**
     * Checks if the configuration file exists.
     * @return {@code true} if the configuration file exists, otherwise {@code false}.
     */
    public boolean isExists() {
        File configFile = new File(configPath);
        return configFile.exists();
    }

    /**
     * Gets the configuration value for the specified key.
     * This method supports nested keys by separating them with a dot.
     * For example, "server.port" will be interpreted as accessing the value
     * by the "port" key inside the map stored by the "server" key.
     *
     * @param key A configuration key that can include nested levels separated by dots.
     * @return The value for the specified key or {@code null} if the key is not found or does not lead to a map.
     */
    @SuppressWarnings("unchecked")
    private Object getConfigValue(String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> configMap = config;

        for (int i = 0; i < keys.length - 1; i++) {
            configMap = (Map<String, Object>) configMap.get(keys[i]);
            if (configMap == null) {
                return null;
            }
        }
        return configMap.get(keys[keys.length - 1]);
    }

    /**
     * Sets the configuration value for the specified key.
     * This method supports nested keys by separating them with a dot.
     * For example, "server.port" will be interpreted as setting the value
     * by the "port" key inside the map stored by the "server" key.
     * If necessary in the process, the method creates new maps for nested keys.
     *
     * @param key A configuration key that can include nested levels separated by dots.
     * @param value The value to set by the specified key.
     */
    @SuppressWarnings("unchecked")
    private void setConfigValue(String key, Object value) {
        String[] keys = key.split("\\.");
        Map<String, Object> configMap = config;

        for (int i = 0; i < keys.length - 1; i++) {
            Object current = configMap.get(keys[i]);

            if (!(current instanceof Map)) {
                current = new LinkedHashMap<String, Object>();
                configMap.put(keys[i], current);
            }

            configMap = (Map<String, Object>) current;
        }

        configMap.put(keys[keys.length - 1], value);
    }

    /**
     * Gets the integer value for the specified configuration key.
     * Returns 0 if the value is not found or is not a number.
     * @param key Configuration key.
     * @return The integer value for the key, or 0 if the key is not found or the value is not a number.
     */
    public int getInt(String key) {
        Object value = getConfigValue(key);
        return value instanceof Number ? ((Number) value).intValue() : 0;
    }

    /**
     * Sets the integer value for the specified configuration key.
     * @param key Configuration key.
     * @param value Integer value to set.
     */
    public void setInt(String key, int value) {
        setConfigValue(key, value);
    }

    /**
     * Gets a boolean value for the specified configuration key.
     * Returns false if the value is not found or is not a boolean value.
     * @param key Configuration key.
     * @return The boolean value for the key, or false if the key is not found or the value is not a boolean.
     */
    public boolean getBoolean(String key) {
        Object value = getConfigValue(key);
        return value instanceof Boolean ? (Boolean) value : false;
    }

    /**
     * Sets a boolean value for the specified configuration key.
     * @param key Configuration key.
     * @param value Boolean value to set.
     */
    public void setBoolean(String key, boolean value) {
        setConfigValue(key, value);
    }

    /**
     * Gets the floating point value (double) for the specified configuration key.
     * Returns 0.0 if the value is not found or is not a number.
     * @param key Configuration key.
     * @return The floating point value for the key, or 0.0 if the key is not found or the value is not a number.
     */
    public double getDouble(String key) {
        Object value = getConfigValue(key);
        return value instanceof Number ? ((Number) value).doubleValue() : 0.0;
    }

    /**
     * Sets the floating point value (double) for the specified configuration key.
     * @param key Configuration key.
     * @param value The floating point value to set.
     */
    public void setDouble(String key, double value) {
        setConfigValue(key, value);
    }

    /**
     * Retrieves a List of objects for the specified configuration key.
     * Returns an empty list if the value is not found or is not a list.
     * @param key Configuration key.
     * @return A list of objects for the key, or an empty list if the key is not found or the value is not a list.
     */
    @SuppressWarnings("unchecked")
    public List<Object> getList(String key) {
        Object value = getConfigValue(key);
        return value instanceof List ? (List<Object>) value : new ArrayList<>();
    }

    /**
     * Sets a List of objects for the specified configuration key.
     * @param key Configuration key.
     * @param value List of objects to install.
     */
    public void setList(String key, List<Object> value) {
        setConfigValue(key, value);
    }

    /**
     * Gets the configuration string value for the specified key.
     * Returns {@code null} if the value is not found or is not a string.
     * @param key A configuration key that can include nested levels separated by dots.
     * @return The string value for the specified key, or {@code null} if the key is not found or the value is not a string.
     */
    public String getString(String key) {
        Object value = getConfigValue(key);
        return value instanceof String ? (String) value : null;
    }

    /**
     * Sets the configuration string value to the specified key.
     * @param key A configuration key that can include nested levels separated by dots.
     * @param value The string value to set to the specified key.
     */
    public void setString(String key, String value) {
        setConfigValue(key, value);
    }

    /**
     * Retrieves a set of keys for the specified configuration section.
     * @param sectionKey The configuration section key.
     * @return The set of section keys, or {@code null} if the section is not found or is not a map.
     */
    @SuppressWarnings("unchecked")
    public Set<String> getSectionKeys(String sectionKey) {
        Object section = getConfigValue(sectionKey);

        if (section instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) section;
            return map.keySet();
        }

        return null;
    }

    /**
     * Gets a collection of values for the specified configuration section.
     * @param sectionKey The configuration section key.
     * @return Collection of section values as {@code Collection<Object>},
     * or {@code null} if the section is not found or is not a map.
     */
    @SuppressWarnings("unchecked")
    public Collection<Object> getSectionValues(String sectionKey) {
        Object section = getConfigValue(sectionKey);
        if (section instanceof Map) {
            return ((Map<String, Object>) section).values();
        }
        return null;
    }
}
