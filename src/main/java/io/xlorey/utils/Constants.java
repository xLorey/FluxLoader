package io.xlorey.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Set of main fluxloader constants
 */
public class Constants {
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
                try (InputStream input = Constants.class.getClassLoader().getResourceAsStream("io/xlorey/config/fluxloader.properties")) {
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
