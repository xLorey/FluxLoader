package io.xlorey.FluxLoader.plugin;

import com.google.gson.Gson;
import lombok.Data;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * The PluginInfo class represents plugin data loaded from the plugin.json file.
 */
@Data
public class PluginInfo {
    /**
     * Tool for working with JSON files
     */
    private static final Gson gson = new Gson();

    /**
     * Plugin name
     */
    private String name;

    /**
     * Description of the plugin
     */
    private String description;

    /**
     * Unique identifier of the plugin
     */
    private String id;

    /**
     * Plugin version
     */
    private String version;

    /**
     * List of plugin authors
     */
    private List<String> authors;

    /**
     * Contact information for the plugin authors
     */
    private List<String> contact;

    /**
     * The license under which the plugin is distributed
     */
    private String license;

    /**
     * Path to the plugin icon
     */
    private String icon;

    /**
     * Plugin entry points for client and server parts
     */
    private Map<String, List<String>> entrypoints;

    /**
     * Plugin dependencies on other projects or libraries
     */
    private Map<String, String> dependencies;

    /**
     * Getting plugin metadata
     * @param jarFile plugin file
     * @return plugin metadata in PluginInfo format
     * @throws IOException In case of I/O errors.
     */
    public static PluginInfo getInfoFromFile(File jarFile) throws IOException {
        try (JarFile jar = new JarFile(jarFile)) {
            ZipEntry entry = jar.getEntry("plugin.json");
            if (entry != null) {
                try (InputStream input = jar.getInputStream(entry);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

                    StringBuilder jsonBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonBuilder.append(line);
                    }

                    return gson.fromJson(jsonBuilder.toString(), PluginInfo.class);
                }
            }
        }
        return null;
    }
}
