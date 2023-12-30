package io.xlorey.FluxLoader;

import io.xlorey.FluxLoader.utils.BackupTools;
import io.xlorey.FluxLoader.utils.Constants;
import io.xlorey.FluxLoader.utils.JarTools;
import io.xlorey.FluxLoader.utils.Logger;
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
     * Checks for the presence of a game folder.
     * @exception Exception in cases where the installer is not located next to the game folder
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
     * @exception Exception in cases of unsuccessful installation
     */
    public static void install() throws Exception {
        Logger.print(String.format("Preparing for installation %s...", Constants.FLUX_NAME));
        Logger.print("Checking the installer location...");

        checkGameFolder();

        ArrayList<String> modifiedFileList = JarTools.getFilesInFolder(getJarPath(), "zombie");

        Logger.print("Preparing to create backups...");

        for (String modifiedFile : modifiedFileList) {
            if (modifiedFile.contains("$")) continue;

            modifiedFile = modifiedFile.replace("zombie/", "");

            BackupTools.createBackup(modifiedFile);
        }

        Logger.print("Unpacking dependency files...");

        try {
            String jarPath = getJarPath();
            String unpackPath = new File(jarPath).getParent();

            Logger.print("Attempting to extract the core files...");
            JarTools.unpackJar(Constants.WHITELIST_FLUXLOADER_FILES, jarPath, unpackPath);

            Logger.print("Attempting to extract modified game files...");
            JarTools.unpackJar(modifiedFileList, jarPath, unpackPath);
        } catch (Exception e) {
            Logger.print("Error during installation: " + e.getMessage());
        }
    }

    /**
     * Project uninstallation
     * @exception Exception in cases of unsuccessful removal
     */
    public static void uninstall() throws Exception {
        Logger.print(String.format("Preparing for uninstallation %s...", Constants.FLUX_NAME));
        Logger.print("Checking the uninstaller location...");

        checkGameFolder();

        ArrayList<String> modifiedFileList = JarTools.getFilesInFolder(getJarPath(), "zombie");

        Logger.print("Preparing to restore backups...");

        for (String modifiedFile : modifiedFileList) {
            if (modifiedFile.contains("$")) continue;

            modifiedFile = modifiedFile.replace("zombie/", "");

            BackupTools.restoreFile(modifiedFile);
        }

        Logger.print("Removing dependency files...");

        try {
            String jarPath = getJarPath();
            String unpackPath = new File(jarPath).getParent();
            Logger.print("Attempting to delete core files...");
            JarTools.deleteJarFilesFromDirectory(Constants.WHITELIST_FLUXLOADER_FILES, jarPath, unpackPath);
        } catch (Exception e) {
            Logger.print("Error during uninstallation: " + e.getMessage());
        }
    }
}
