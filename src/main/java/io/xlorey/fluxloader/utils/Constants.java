package io.xlorey.fluxloader.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Set of main fluxloader constants
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@UtilityClass
public class Constants {
        /**
         * Folder name for plugins (mods)
         */
        public static final String PLUGINS_FOLDER_NAME = "plugins";

        /**
         * Metadata form version
         */
        public static final int PLUGINS_METADATA_REVISION = 1;

        /**
         * Name of the default plugin config
         */
        public static final String PLUGINS_DEFAULT_CONFIG_NAME = "config.yml";

        /**
         * Plugin metadata file name
         */
        public static final String PLUGINS_METADATA_NAME = "metadata.json";

        /**
         * Name of the folder with translations for plugins
         */
        public static final String PLUGINS_TRANSLATION_FOLDER = "translation";

        /**
         * Path to the FluxLoader logo
         */
        public static final String PATH_FLUXLOADER_LOGO = "io/xlorey/fluxloader/media/FluxLoader-Logo.png";

        /**
         * Path to project files
         */
        public static final ArrayList<String> WHITELIST_FLUXLOADER_FILES = new ArrayList<>() {{
                add("io/");
                add("imgui/");
                add("com/google/");
                add("org/yaml/");
        }};

        /**
         * Link to project github
         */
        public static final String GITHUB_LINK = "https://github.com/xLorey/FluxLoader";

        /**
         * Link to the project's discord channel
         */
        public static final String DISCORD_LINK = "https://discord.gg/BwSuTdEGJ4";

        /**
         * Project version
         */
        public static final String FLUX_VERSION;

        /**
         * Project name
         */
        public static final String FLUX_NAME;

        /*
          Initializing data and configuration file
         */
        static {
                Properties properties = new Properties();
                try (InputStream input = Constants.class.getClassLoader().getResourceAsStream("io/xlorey/fluxloader/config/fluxloader.properties")) {
                        if (input == null) {
                                throw new IOException("Configuration file not found");
                        }
                        properties.load(input);

                        FLUX_VERSION = properties.getProperty("version");
                        FLUX_NAME = properties.getProperty("projectName");
                } catch (IOException e) {
                        throw new RuntimeException("Failed to load configuration file", e);
                }
        }
}
