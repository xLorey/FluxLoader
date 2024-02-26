package io.xlorey.fluxloader;

import io.xlorey.fluxloader.interfaces.IPatch;
import io.xlorey.fluxloader.patches.*;
import io.xlorey.fluxloader.utils.BackupTools;
import io.xlorey.fluxloader.utils.Constants;
import io.xlorey.fluxloader.utils.JarTools;
import io.xlorey.fluxloader.utils.Logger;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Plugin loader installation tools
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@UtilityClass
public class Installer {
    /**
     * A list of patches to be applied to the game files. Each patch is represented by an implementation
     * of the IPatch interface, which encapsulates the logic for modifying specific game files.
     */
    private static final ArrayList<IPatch> patches = new ArrayList<>(){{
        add(new PatchGameWindow("zombie.GameWindow"));
        add(new PatchLuaEventManager("zombie.Lua.LuaEventManager"));
        add(new PatchBanSteamIDCommand("zombie.commands.serverCommands.BanSteamIDCommand"));
        add(new PatchBanUserCommand("zombie.commands.serverCommands.BanUserCommand"));
        add(new PatchUnbanUserCommand("zombie.commands.serverCommands.UnbanUserCommand"));
        add(new PatchUnbanSteamIDCommand("zombie.commands.serverCommands.UnbanSteamIDCommand"));
        add(new PatchKickUserCommand("zombie.commands.serverCommands.KickUserCommand"));
        add(new PatchQuitCommand("zombie.commands.serverCommands.QuitCommand"));
        add(new PatchChatServer("zombie.network.chat.ChatServer"));
        add(new PatchGameServer("zombie.network.GameServer"));
        add(new PatchSpriteRenderer("zombie.core.SpriteRenderer"));
        add(new PatchUIManager("zombie.ui.UIManager"));
    }};

    /**
     * The path to the game folder where the game files are located. This path is determined at runtime
     * based on the location of the installer JAR file.
     */
    public static String gameFolderPath;

    /**
     * Retrieves the File object representing the installer JAR file. This method is used to determine
     * the location of the installer, which in turn is used to establish the base path for game file modifications.
     * @return A File object pointing to the installer JAR file.
     * @throws URISyntaxException If the URL to the JAR file is not formatted correctly.
     */
    private static File getInstallerJarFile() throws URISyntaxException {
        return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
    }

    /**
     * Getting the path to the folder with the game files necessary to form the JVM context
     * @return path to the folder with game classes
     */
    public static String getGameFolderPath() {
        return gameFolderPath.replace("zombie", "");
    }

    /**
     * Checks the game folder to ensure that the necessary game files are present. This method sets the
     * gameFolderPath based on the existence of specific directories relative to the installer's location.
     * It supports differentiating between client and server installations by checking for the presence of
     * specific subdirectories.
     * @throws Exception If the required game files or directories are not found.
     */
    private static void checkGameFolder() throws Exception {
        gameFolderPath = getInstallerJarFile().getParent();

        Path clientPath = Paths.get(gameFolderPath, "zombie");
        Path serverPath = Paths.get(gameFolderPath, "java", "zombie");

        if (Files.isDirectory(clientPath)) {
            gameFolderPath = clientPath.toString();
        } else if (Files.isDirectory(serverPath)) {
            gameFolderPath = serverPath.toString();
        } else {
            throw new Exception("No game files found!\nPlace this JAR file in the root folder of the game!");
        }
    }

    /**
     * Initiates the installation process for patches and dependencies. This method performs several key
     * steps: verifying the location of the game folder, creating backups of the original game files,
     * applying patches to modify the game files, and unpacking any necessary dependency files into the
     * game directory. This ensures that all modifications and dependencies are correctly installed.
     * @throws Exception If there are issues during the installation process, including errors in creating
     * backups, applying patches, or unpacking dependencies.
     */
    public static void install() throws Exception {
        Logger.print(String.format("Preparing for installation %s...", Constants.FLUX_NAME));
        Logger.print("Checking the installer location...");

        checkGameFolder();

        Logger.print("Preparing to create backups...");
        for (IPatch patch : patches) {
            BackupTools.createBackup(patch.getClassName());
        }

        Logger.print("Preparing to make changes to game files...");
        for (IPatch patch : patches) {
            patch.patch();
        }

        Logger.print("Unpacking dependency files...");
        try {
            String jarPath = getInstallerJarFile().getPath();
            String unpackPath = getGameFolderPath();

            Logger.print("Attempting to extract the core files...");
            JarTools.unpackJar(Constants.WHITELIST_FLUXLOADER_FILES, jarPath, unpackPath);
        } catch (Exception e) {
            Logger.print("Error during installation: " + e.getMessage());
        }
    }

    /**
     * Initiates the uninstallation process, which involves restoring the original game files from backups
     * and removing any dependency files that were unpacked during installation. This method ensures that
     * the game folder is returned to its state prior to the installation of patches and dependencies, thereby
     * removing any modifications made by the installer.
     * @throws Exception If there are issues during the uninstallation process, including errors in restoring
     * backups or deleting dependency files.
     */
    public static void uninstall() throws Exception {
        Logger.print(String.format("Preparing for uninstallation %s...", Constants.FLUX_NAME));
        Logger.print("Checking the uninstaller location...");

        checkGameFolder();

        Logger.print("Preparing to restore game files...");
        for (IPatch patch : patches) {
            BackupTools.restoreFile(patch.getClassName());
        }

        Logger.print("Removing dependency files...");
        try {
            String jarPath = getInstallerJarFile().getPath();
            String unpackPath = getGameFolderPath();

            Logger.print("Attempting to delete core files...");
            JarTools.deleteJarFilesFromDirectory(Constants.WHITELIST_FLUXLOADER_FILES, jarPath, unpackPath);
        } catch (Exception e) {
            Logger.print("Error during uninstallation: " + e.getMessage());
        }
    }
}
