package io.xlorey.fluxloader.plugin;

import io.xlorey.fluxloader.utils.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: A set of tools for creating plugin configuration files
 * <p>FluxLoader © 2024. All rights reserved.</p>
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
     * This method checks for the existence of a configuration file at the specified path in {@code configPath}.
     * If the file does not exist, the method tries to find and copy it from the JAR file resources.
     * If the file is not in the JAR resources or already exists in the destination folder, then nothing is done.
     */
    public final synchronized void copy() {
        File configFile = new File(configPath);

        if (isExists()) return;

        String fileName = configFile.getName();

        try (InputStream in = plugin.getClass().getClassLoader().getResourceAsStream(fileName);
             FileOutputStream out = new FileOutputStream(configFile)) {

            if (in == null) return;

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            Logger.print(String.format("Error when copying config '%s' from plugin Jar archive: '%s'",
                    configName,
                    plugin.getMetadata().getId()
                    ));
            e.printStackTrace();
        }
    }

    /**
     * Reloads the configuration file.
     * This method saves the current configuration to the file, then reloads the configuration
     * from the file, effectively refreshing the configuration in memory.
     * After reloading, the updated configuration is available for use.
     */
    public final synchronized void reload() {
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
    public final synchronized void save() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setProcessComments(true);

        Yaml yaml = new Yaml(options);

        try (FileWriter writer = new FileWriter(configPath)) {
            yaml.dump(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the configuration by copying the file from the JAR (if it exists in it).
     * If the file does not exist in the JAR and folder, an empty config is created and loaded empty, otherwise the existing data is loaded.
     * Then loads the configuration from the YAML file at the specified path in {@code configPath}.
     * If the loaded configuration is {@code null}, nothing is done.
     */
    public final synchronized void load() {
        copy();

        if (!isExists()) {
            create();
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
    public final synchronized void create() {
        File configFile = new File(configPath);

        if (!configFile.exists()) return;

        try {
            if (!configFile.createNewFile()) {
                throw new IOException("Failed to create configuration file: " + configPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.print("An error occurred while creating the configuration file!");
        }
    }

    /**
     * Checks if the configuration file exists.
     * @return {@code true} if the configuration file exists, otherwise {@code false}.
     */
    public final boolean isExists() {
        File configFile = new File(configPath);
        return configFile.exists();
    }

    /**
     * Checks if the configuration is empty.
     * @return {@code true} if the configuration is empty, otherwise {@code false}.
     */
    public final boolean isEmpty() {
        return config.isEmpty();
    }

    /**
     * Clears the entire configuration, removing all keys and values.
     */
    public final synchronized void clear() {
        config.clear();
    }

    /**
     * Checks whether the configuration contains the specified key.
     * @param key The key to check for presence in the configuration.
     * @return {@code true} if the configuration contains the specified key, otherwise {@code false}.
     */
    public final boolean contains(String key) {
        return getConfigValue(key) != null;
    }

    /**
     * Removes the specified key and its value from the configuration.
     * @param key The key to remove from the configuration.
     */
    @SuppressWarnings("unchecked")
    public final synchronized void remove(String key) {
        String[] keys = key.split("\\.");
        Map<String, Object> configMap = config;

        for (int i = 0; i < keys.length - 1; i++) {
            configMap = (Map<String, Object>) configMap.get(keys[i]);
            if (configMap == null) {
                return;
            }
        }

        configMap.remove(keys[keys.length - 1]);
    }

   /**
     * Returns a copy of the entire configuration as {@code Map<String, Object>}.
     * @return A copy of the entire configuration as {@code Map<String, Object>}.
     */
    public final Map<String, Object> getAll() {
        return new LinkedHashMap<>(config);
    }

    /**
     * Merges the current configuration with another configuration by adding or replacing keys and values.
     * @param otherConfig Another configuration to merge the current configuration with.
     */
    public final void merge(Map<String, Object> otherConfig) {
        mergeRecursive(config, otherConfig);
    }

    /**
     * Merges the current configuration with another configuration by adding or replacing keys and values.
     * @param otherConfig Another configuration to merge the current configuration with.
     */
    public final void merge(Configuration otherConfig) {
        Map<String, Object> otherConfigMap = otherConfig.getAll();
        merge(otherConfigMap);
    }

    /**
     * Recursively combines two configurations.
     * @param targetConfig The target configuration to which the keys and values are added.
     * @param sourceConfig Another configuration from which the keys and values are copied.
     */
    @SuppressWarnings("unchecked")
    private void mergeRecursive(Map<String, Object> targetConfig, Map<String, Object> sourceConfig) {
        for (Map.Entry<String, Object> entry : sourceConfig.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map && targetConfig.containsKey(key) && targetConfig.get(key) instanceof Map) {
                // If the value is also a map and the key is already present in the target map,
                // then call this method recursively to combine nested maps
                mergeRecursive((Map<String, Object>) targetConfig.get(key), (Map<String, Object>) value);
            } else {
                // Otherwise, just replace the value in the target map
                targetConfig.put(key, value);
            }
        }
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
    public final int getInt(String key) {
        Object value = getConfigValue(key);
        return value instanceof Number ? ((Number) value).intValue() : 0;
    }

    /**
     * Sets the integer value for the specified configuration key.
     * @param key Configuration key.
     * @param value Integer value to set.
     */
    public final void setInt(String key, int value) {
        setConfigValue(key, value);
    }

    /**
     * Gets the Long value for the specified configuration key.
     * Returns 0L if the value is not found or is not a number.
     * @param key Configuration key.
     * @return A Long value for the key, or 0L if the key is not found or the value is not a number.
     */
    public final long getLong(String key) {
        Object value = getConfigValue(key);
        return value instanceof Number ? ((Number) value).longValue() : 0L;
    }

    /**
     * Sets a Long value for the specified configuration key.
     * @param key Configuration key.
     * @param value The Long value to set.
     */
    public final void setLong(String key, long value) {
        setConfigValue(key, value);
    }

    /**
     * Gets the Short value for the specified configuration key.
     * Returns 0 if the value is not found or is not a number.
     * @param key Configuration key.
     * @return A Short value for the key, or 0 if the key is not found or the value is not a number.
     */
    public final short getShort(String key) {
        Object value = getConfigValue(key);
        return value instanceof Number ? ((Number) value).shortValue() : 0;
    }

    /**
     * Sets the value of type Short to the specified configuration key.
     * @param key Configuration key.
     * @param value The Short value to set.
     */
    public final void setShort(String key, short value) {
        setConfigValue(key, value);
    }

    /**
     * Gets a Byte value for the specified configuration key.
     * Returns 0 if the value is not found or is not a number.
     * @param key Configuration key.
     * @return A Byte value for the key, or 0 if the key is not found or the value is not a number.
     */
    public final byte getByte(String key) {
        Object value = getConfigValue(key);
        return value instanceof Number ? ((Number) value).byteValue() : 0;
    }

    /**
     * Sets a Byte value for the specified configuration key.
     * @param key Configuration key.
     * @param value The Byte value to set.
     */
    public final void setByte(String key, byte value) {
        setConfigValue(key, value);
    }

    /**
     * Gets a boolean value for the specified configuration key.
     * Returns false if the value is not found or is not a boolean value.
     * @param key Configuration key.
     * @return The boolean value for the key, or false if the key is not found or the value is not a boolean.
     */
    public final boolean getBoolean(String key) {
        Object value = getConfigValue(key);
        return value instanceof Boolean ? (Boolean) value : false;
    }

    /**
     * Sets a boolean value for the specified configuration key.
     * @param key Configuration key.
     * @param value Boolean value to set.
     */
    public final void setBoolean(String key, boolean value) {
        setConfigValue(key, value);
    }

    /**
     * Gets the floating point value (double) for the specified configuration key.
     * Returns 0.0 if the value is not found or is not a number.
     * @param key Configuration key.
     * @return The floating point value for the key, or 0.0 if the key is not found or the value is not a number.
     */
    public final double getDouble(String key) {
        Object value = getConfigValue(key);
        return value instanceof Number ? ((Number) value).doubleValue() : 0.0;
    }

    /**
     * Sets the floating point value (double) for the specified configuration key.
     * @param key Configuration key.
     * @param value The floating point value to set.
     */
    public final void setDouble(String key, double value) {
        setConfigValue(key, value);
    }

    /**
     * Gets the float value for the specified configuration key.
     * Returns 0.0f if the value is not found or is not a number.
     * @param key Configuration key.
     * @return The float value for the key, or 0.0f if the key is not found or the value is not a number.
     */
    public final float getFloat(String key) {
        Object value = getConfigValue(key);
        return value instanceof Number ? ((Number) value).floatValue() : 0.0f;
    }

    /**
     * Sets the float value for the specified configuration key.
     * @param key Configuration key.
     * @param value The floating point value to set.
     */
    public final void setFloat(String key, float value) {
        setConfigValue(key, value);
    }

    /**
     * Gets the configuration string value for the specified key.
     * Returns {@code null} if the value is not found or is not a string.
     * @param key A configuration key that can include nested levels separated by dots.
     * @return The string value for the specified key, or {@code null} if the key is not found or the value is not a string.
     */
    public final String getString(String key) {
        Object value = getConfigValue(key);
        return value instanceof String ? (String) value : null;
    }

    /**
     * Sets the configuration string value to the specified key.
     * @param key A configuration key that can include nested levels separated by dots.
     * @param value The string value to set to the specified key.
     */
    public final void setString(String key, String value) {
        setConfigValue(key, value);
    }

    /**
     * Gets the character value (char) for the specified configuration key.
     * Returns '\u0000' (null character) if the value is not found or is not a character.
     * @param key Configuration key.
     * @return The character value for the key, or '\u0000' if the key is not found or the value is not a character.
     */
    public final char getChar(String key) {
        Object value = getConfigValue(key);
        return (value instanceof Character) ? (Character) value : '\u0000';
    }

    /**
     * Sets the character value (char) for the specified configuration key.
     * @param key Configuration key.
     * @param value The character value to set.
     */
    public final void setChar(String key, char value) {
        setConfigValue(key, value);
    }

    /**
     * Retrieves a set of keys for the specified configuration section.
     * @param sectionKey The configuration section key.
     * @return The set of section keys, or {@code null} if the section is not found or is not a map.
     */
    @SuppressWarnings("unchecked")
    public final Set<String> getSectionKeys(String sectionKey) {
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
    public final Collection<Object> getSectionValues(String sectionKey) {
        Object section = getConfigValue(sectionKey);
        if (section instanceof Map) {
            return ((Map<String, Object>) section).values();
        }
        return null;
    }

    /**
     * Retrieves a List of objects for the specified configuration key.
     * @param key Configuration key.
     * @return A list of objects for the key, or an empty list if the key is not found or the value is not a list.
     */
    @SuppressWarnings("unchecked")
    public final List<Object> getList(String key) {
        Object value = getConfigValue(key);
        return value instanceof List ? (List<Object>) value : new ArrayList<>();
    }

    /**
     * Sets a List of objects for the specified configuration key.
     * @param key Configuration key.
     * @param value List of objects to install.
     */
    public final void setList(String key, List<Object> value) {
        setConfigValue(key, value);
    }

    /**
     * Retrieves a List of strings for the specified configuration key.
     * @param key Configuration key.
     * @return A list of strings for the key, or an empty list if the key is not found or the value is not a list of strings.
     */
    public final List<String> getStringList(String key) {
        return getList(key).stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Sets a List of strings for the specified configuration key.
     * @param key Configuration key.
     * @param value List of strings to set.
     */
    public final void setStringList(String key, List<String> value) {
        setConfigValue(key, value);
    }

    /**
     * Retrieves a List of floats for the specified configuration key.
     * @param key Configuration key.
     * @return A list of floats for the key, or an empty list if the key is not found or the value is not a list of floats.
     */
    public final List<Float> getFloatList(String key) {
        return getList(key).stream()
                .filter(Float.class::isInstance)
                .map(Float.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Sets a List of floats for the specified configuration key.
     * @param key Configuration key.
     * @param value List of floats to set.
     */
    public final void setFloatList(String key, List<Float> value) {
        setConfigValue(key, value);
    }

    /**
     * Retrieves a List of doubles for the specified configuration key.
     * @param key Configuration key.
     * @return A list of doubles for the key, or an empty list if the key is not found or the value is not a list of doubles.
     */
    public final List<Double> getDoubleList(String key) {
        return getList(key).stream()
                .filter(Double.class::isInstance)
                .map(Double.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Sets a List of doubles for the specified configuration key.
     * @param key Configuration key.
     * @param value List of doubles to set.
     */
    public final void setDoubleList(String key, List<Double> value) {
        setConfigValue(key, value);
    }

    /**
     * Retrieves a List of longs for the specified configuration key.
     * @param key Configuration key.
     * @return A list of longs for the key, or an empty list if the key is not found or the value is not a list of longs.
     */
    public final List<Long> getLongList(String key) {
        return getList(key).stream()
                .filter(Long.class::isInstance)
                .map(Long.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Sets a List of longs for the specified configuration key.
     * @param key Configuration key.
     * @param value List of longs to set.
     */
    public final void setLongList(String key, List<Long> value) {
        setConfigValue(key, value);
    }

    /**
     * Retrieves a List of integers for the specified configuration key.
     * @param key Configuration key.
     * @return A list of integers for the key, or an empty list if the key is not found or the value is not a list of integers.
     */
    public final List<Integer> getIntegerList(String key) {
        return getList(key).stream()
                .filter(Integer.class::isInstance)
                .map(Integer.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Sets a List of integers for the specified configuration key.
     * @param key Configuration key.
     * @param value List of integers to set.
     */
    public final void setIntegerList(String key, List<Integer> value) {
        setConfigValue(key, value);
    }

    /**
     * Retrieves a List of characters for the specified configuration key.
     * @param key Configuration key.
     * @return A list of characters for the key, or an empty list if the key is not found or the value is not a list of characters.
     */
    public final List<Character> getCharacterList(String key) {
        return getList(key).stream()
                .filter(Character.class::isInstance)
                .map(Character.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Sets a List of characters for the specified configuration key.
     * @param key Configuration key.
     * @param value List of characters to set.
     */
    public final void setCharacterList(String key, List<Character> value) {
        setConfigValue(key, value);
    }

    /**
     * Retrieves a List of booleans for the specified configuration key.
     * @param key Configuration key.
     * @return A list of booleans for the key, or an empty list if the key is not found or the value is not a list of booleans.
     */
    public final List<Boolean> getBooleanList(String key) {
        return getList(key).stream()
                .filter(Boolean.class::isInstance)
                .map(Boolean.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Sets a List of booleans for the specified configuration key.
     * @param key Configuration key.
     * @param value List of booleans to set.
     */
    public final void setBooleanList(String key, List<Boolean> value) {
        setConfigValue(key, value);
    }

    /**
     * Retrieves a List of shorts for the specified configuration key.
     * @param key Configuration key.
     * @return A list of shorts for the key, or an empty list if the key is not found or the value is not a list of shorts.
     */
    public final List<Short> getShortList(String key) {
        return getList(key).stream()
                .filter(Short.class::isInstance)
                .map(Short.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Sets a List of shorts for the specified configuration key.
     * @param key Configuration key.
     * @param value List of shorts to set.
     */
    public final void setShortList(String key, List<Short> value) {
        setConfigValue(key, value);
    }

    /**
     * Retrieves a List of bytes for the specified configuration key.
     * @param key Configuration key.
     * @return A list of bytes for the key, or an empty list if the key is not found or the value is not a list of bytes.
     */
    public final List<Byte> getByteList(String key) {
        return getList(key).stream()
                .filter(Byte.class::isInstance)
                .map(Byte.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Sets a List of bytes for the specified configuration key.
     * @param key Configuration key.
     * @param value List of bytes to set.
     */
    public final void setByteList(String key, List<Byte> value) {
        setConfigValue(key, value);
    }
}