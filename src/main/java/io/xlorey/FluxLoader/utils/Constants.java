package io.xlorey.FluxLoader.utils;


import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Set of main fluxloader constants
 */
@UtilityClass
public class Constants {
        /**
         * Folder name for plugins (mods)
         */
        public static final String PLUGINS_FOLDER_NAME = "plugins";

        /**
         * Path to compiled game files
         */
        public static final String PATH_TO_GAME_CLASS_FOLDER = "zombie";

        /**
         * Path to the FluxLoader logo
         */
        public static final String PATH_FLUXLOADER_LOGO = "io/xlorey/FluxLoader/media/FluxLoader-Logo.png";

        /**
         * Path to project files
         */
        public static final ArrayList<String> WHITELIST_FLUXLOADER_FILES = new ArrayList<>() {{ add("io/xlorey/"); }};

        /**
         * Project version
         */
        public static final String FLUX_VERSION;

        /**
         * Project name
         */
        public static final String FLUX_NAME;

        /**
         * Link to project github
         */
        public static final String GITHUB_LINK = "https://github.com/xLorey/FluxLoader-PZ";

        /**
         * Link to the project's discord channel
         */
        public static final String DISCORD_LINK = "https://discord.gg/BwSuTdEGJ4";

        /*
          Initializing data and configuration file
         */
        static {
                Properties properties = new Properties();
                try (InputStream input = Constants.class.getClassLoader().getResourceAsStream("io/xlorey/FluxLoader/config/fluxloader.properties")) {
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
