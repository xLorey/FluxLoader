package io.xlorey.FluxLoader;

import io.xlorey.FluxLoader.utils.Constants;
import io.xlorey.FluxLoader.utils.JarTools;
import io.xlorey.FluxLoader.utils.Logger;
import io.xlorey.FluxLoader.utils.patch.*;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Mod loader installation tools
 */
@UtilityClass
public class Installer {
    /**
     * Arraylist of injectors that inject code into game files
     */
    private static final ArrayList<PatchFile> injectorsList = new ArrayList<>() {
        {
            add(new PatchGameWindow());
            add(new PatchGameServer());
            add(new PatchLuaEventManager());
        }
    };

    /**
     * Checks for the presence of a game folder.
     */
    public static void checkGameFolder() throws Exception {
        Path gameFolderPath = Paths.get(Constants.PATH_TO_GAME_CLASS_FOLDER);

        if(!Files.exists(gameFolderPath) || !Files.isDirectory(gameFolderPath))
            throw new Exception(String.format("There is no game folder '%s' next to %s.\n Please place this 'jar' file in the correct location!\n", Constants.PATH_TO_GAME_CLASS_FOLDER, Constants.FLUX_NAME));
    }

    /**
     * Getting the path to the current executable Jar file
     * @return path to the Jar file
     */
    private static String getJarPath() throws URISyntaxException {
        return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
    }

    /**
     * Project installation
     */
    public static void install() throws Exception {
        Logger.print(String.format("Preparing for installation %s...", Constants.FLUX_NAME));
        Logger.print("Checking the installer location...");

        checkGameFolder();

        for (PatchFile injector: injectorsList) {
            injector.inject();
        }

        PatchTools.saveModifiedClasses();

        Logger.print("Unpacking dependency files...");

        try {
            String jarPath = getJarPath();
            String unpackPath = new File(jarPath).getParent();
            JarTools.unpackJar(Constants.WHITELIST_FLUXLOADER_FILES, jarPath, unpackPath);
        } catch (Exception e) {
            Logger.print("Error during installation: " + e.getMessage());
        }
    }

    /**
     * Project uninstallation
     */
    public static void uninstall() throws Exception {
        Logger.print(String.format("Preparing for uninstallation %s...", Constants.FLUX_NAME));
        Logger.print("Checking the uninstaller location...");

        checkGameFolder();

        for (PatchFile injector: injectorsList) {
            injector.rollBackChanges();
        }

        Logger.print("Removing dependency files...");

        try {
            String jarPath = getJarPath();
            String unpackPath = new File(jarPath).getParent();
            JarTools.deleteJarFilesFromDirectory(Constants.WHITELIST_FLUXLOADER_FILES, jarPath, unpackPath);
        } catch (Exception e) {
            Logger.print("Error during uninstallation: " + e.getMessage());
        }
    }
}
