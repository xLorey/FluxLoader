package io.xlorey.fluxloader.shared;

import io.xlorey.fluxloader.utils.Logger;
import lombok.experimental.UtilityClass;
import org.yaml.snakeyaml.Yaml;
import zombie.core.Translator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Deknil
 * GitHub: <a href="https://github.com/Deknil">https://github.com/Deknil</a>
 * Date: 29.02.2024
 * Description: This class provides translation functionality by retrieving translations from a translation registry.
 * <p> FluxLoader © 2024. All rights reserved. </p>
 */
@UtilityClass
public class TranslationManager {
    /**
     * Registry of all downloaded translations.
     * Key: plugin (or core) id
     * Value: translation data map:
     *          Key: code of the country
     *          Value: key translation value
     */
    private static final Map<String, Map<String, Map<String, Object>>> translationRegistry = new HashMap<>();

    /**
     * Loads translations for the specified plugin (or core) ID from the specified folder.
     * @param id Plugin identifier.
     * @param translationsFolder Path to the folder with translations.
     */
    public static synchronized void loadTranslations(String id, Path translationsFolder){
        File folder = translationsFolder.toFile();

        if (!folder.isDirectory() || !folder.exists()) return;

        File[] files = folder.listFiles((File pathname) -> pathname.isFile() && pathname.getName().endsWith(".yml"));

        if (files == null) return;

        for (File file : files) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                Map<String, Object> translationMap = new Yaml().load(inputStream);

                if (translationMap == null) {
                    Logger.print(String.format("Failed to load translation file '%s' from '%s'!",
                            file.getName(),
                            id));
                    return;
                }
                translationRegistry
                        .computeIfAbsent(id, k -> new HashMap<>())
                        .put(file.getName().toLowerCase().replaceFirst("\\.yml$", ""), translationMap);

            } catch (IOException e) {
                Logger.print(String.format("There was an error loading translation file '%s' from '%s'!",
                        file.getName(),
                        id));
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves the translation for the specified key from the translation registry for the language used by the server/client.
     * If the translation is not found, returns the provided key.
     * @param id the identifier of the translation registry
     * @param key the key for the translation
     * @return the translation corresponding to the key and language, or the key itself if not found
     */
    public static String getTranslate(String id, String key) {
        return getTranslate(id, Translator.getLanguage().name().toLowerCase(), key);
    }

    /**
     * Retrieves the translation for the specified key and language from the translation registry.
     * If the translation is not found, returns the provided key.
     * @param id the identifier of the translation registry
     * @param language the language for which the translation is requested
     * @param key the key for the translation
     * @return the translation corresponding to the key and language, or the key itself if not found
     */
    public static String getTranslate(String id, String language, String key) {
        key = key.trim();

        StringBuilder returnKey = new StringBuilder("%");
        String[] keyParts = key.split("\\.");
        for (String part : keyParts) {
            returnKey.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        returnKey.append("%");

        // Checking the availability of translations for the specified identifier
        if (!translationRegistry.containsKey(id)) {
            Logger.print(String.format("Translation for id '%s' not found! (Key: '%s', Language: '%s')",
                    id,
                    key,
                    language));
            return returnKey.toString();
        }

        Map<String, Map<String, Object>> translations = translationRegistry.get(id);

        // Checking the availability of translations for the specified language
        if (!translations.containsKey(language)) {
            Logger.print(String.format("Translation for language '%s' not found! (Key: '%s', ID: '%s')",
                    language,
                    key,
                    id));
            if (!translations.containsKey(Translator.getDefaultLanguage().name().toLowerCase())) {
                Logger.print(String.format("Default language '%s' translation not found! (Key: '%s', ID: '%s')",
                        Translator.getDefaultLanguage().name().toLowerCase(),
                        key,
                        id));
                return returnKey.toString();
            }
            language = Translator.getDefaultLanguage().name().toLowerCase();
        }

        Map<String, Object> languageTranslations = translations.get(language);

        // Return the translation for the specified key
        String translation = getTranslationText(key, languageTranslations);
        if (translation == null) {
            Logger.print(String.format("Translation for key '%s' not found! (ID: '%s', Language: '%s')",
                    key,
                    id,
                    language));
        }

        return translation != null ? translation : returnKey.toString();
    }

    /**
     * Retrieves the translation text for the specified key from the translation map.
     * @param key the key for the translation
     * @param translationMap the map containing translations
     * @return the translation text corresponding to the key, or null if not found
     */
    @SuppressWarnings("unchecked")
    private static String getTranslationText(String key, Map<String, Object> translationMap) {
        String[] keys = key.split("\\.");
        for (int i = 0; i < keys.length - 1; i++) {
            Object obj = translationMap.get(keys[i]);

            if (!(obj instanceof Map)) return null;

            translationMap = (Map<String, Object>) obj;
        }
        Object lastValue = translationMap.get(keys[keys.length - 1]);
        return lastValue instanceof String ? (String) lastValue : null;
    }
}