package io.xlorey.fluxloader.plugin;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.xlorey.fluxloader.utils.Constants;
import io.xlorey.fluxloader.utils.Logger;
import lombok.Value;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: The Metadata class represents plugin data loaded from the metadata.json file.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@Value
public class Metadata {
    /**
     * Tool for working with JSON files
     */
    private static final Gson gson = new Gson();

    /**
     * Metadata form version
     */
    private int revision;

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
     * Entry point for rendering controls in the plugin configuration menu
     */
    private String controlsEntrypoint;

    /**
     * Plugin dependencies on other projects or libraries
     */
    private Map<String, String> dependencies;

    /**
     * Returns a File object representing the configuration directory for this plugin.
     * The directory path is normalized to prevent problems with various file systems.
     * @return A File object pointing to the normalized path to the plugin configuration directory.
     */
    public File getConfigFolder() {
        return new File(Constants.PLUGINS_FOLDER_NAME, getId());
    }

    /**
     * Returns a Path object representing the translations folder for this plugin.
     * The folder path is normalized to prevent problems with different file systems.
     * @return An object of type File pointing to the normalized path to the folder with plugin translations.
     */
    public Path getTranslationFolder() {
        return Paths.get(getConfigFolder().getAbsolutePath(), Constants.PLUGINS_TRANSLATION_FOLDER);
    }

    /**
     * Returns a Path object representing the Lua folder for this plugin.
     * Folder path has been normalized to prevent problems with different file systems.
     * @return An object of type File pointing to the normalized path to the Lua plugin folder.
     */
    public Path getLuaFolder() {
        return Paths.get(getConfigFolder().getAbsolutePath(), Constants.PLUGINS_LUA_FOLDER);
    }

    /**
     * Getting plugin metadata
     * @param jarFile plugin file
     * @return plugin metadata in Metadata format
     * @throws IOException in cases of I/O problems
     */
    public static Metadata getInfoFromFile(File jarFile) throws IOException {
        try (JarFile jar = new JarFile(jarFile)) {
            ZipEntry entry = jar.getEntry(Constants.PLUGINS_METADATA_NAME);
            if (entry != null) {
                try (InputStream input = jar.getInputStream(entry);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

                    StringBuilder jsonBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonBuilder.append(line);
                    }

                    try {
                        return gson.fromJson(jsonBuilder.toString(), Metadata.class);
                    } catch (JsonSyntaxException e) {
                        Logger.print(String.format("Failed to convert metadata to required format in file '%s'", jarFile.getName()));
                        return null;
                    }
                }
            }
        }
        return null;
    }
}