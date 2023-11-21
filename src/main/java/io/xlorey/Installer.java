package io.xlorey;

import io.xlorey.utils.Constants;
import io.xlorey.utils.Logger;
import io.xlorey.utils.patch.PatchFile;
import io.xlorey.utils.patch.PatchGameWindow;
import io.xlorey.utils.patch.PatchTools;
import lombok.experimental.UtilityClass;

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
    }

    /**
     * Project uninstallation
     */
    public static void uninstall() throws Exception {
        Logger.print(String.format("Preparing for removal %s...", Constants.FLUX_NAME));
        Logger.print("Checking the uninstaller location...");

        checkGameFolder();

        for (PatchFile injector: injectorsList) {
            injector.rollBackChanges();
        }
    }
}
