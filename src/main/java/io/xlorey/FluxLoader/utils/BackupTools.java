package io.xlorey.FluxLoader.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * A set of tools for creating a backup
 */
@UtilityClass
public class BackupTools {
    /**
     * Backup file extension
     */
    private static final String BACKUP_EXTENSION = ".bkup";

    /**
     * Creating a backup file
     * @param pathToClassFile path to the file with '.class' extension
     * @throws IOException in cases of unsuccessful backup creation
     */
    public static void createBackup(String pathToClassFile) throws IOException {
        Logger.print(String.format("Trying to create a restore point for a file'%s'...", pathToClassFile));

        validateClassFilePath(pathToClassFile);

        Path originalFilePath = getFullPath(pathToClassFile);
        Path backupFilePath = getBackupPath(originalFilePath);

        if (Files.exists(backupFilePath)) {
            Logger.print(String.format("Backup of the file '%s' already exists. Skipping backup.", pathToClassFile));
            return;
        }

        try {
            Files.copy(originalFilePath, backupFilePath);
        } catch (IOException e) {
            Logger.print("Error while creating backup file.");
            throw e;
        }

        Logger.print(String.format("Backup for file '%s' saved!", pathToClassFile));
    }

    /**
     * Restoring a file if there is a backup
     * @param pathToClassFile path to the modified file (extension '.class')
     * @throws IOException in cases of unsuccessful recovery
     */
    public static void restoreFile(String pathToClassFile) throws IOException {
        Logger.print(String.format("Attempting to recover a file '%s'...", pathToClassFile));

        validateClassFilePath(pathToClassFile);

        Path originalFilePath = getFullPath(pathToClassFile);
        Path backupFilePath = getBackupPath(originalFilePath);

        if (!Files.exists(backupFilePath)) {
            Logger.print(String.format("Backup file for '%s' not found. Skipping restore", pathToClassFile));
            return;
        }

        try {
            Files.move(backupFilePath, originalFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            Logger.print("Error when restoring the game file.");
            throw e;
        }
        Logger.print(String.format("File '%s' was restored!", pathToClassFile));
    }

    /**
     * Checking the path for backup capability
     * @param path PATH TO FILE
     */
    private static void validateClassFilePath(String path) {
        if (!path.endsWith(".class")) {
            throw new IllegalArgumentException("Invalid file format. Path must end with '.class'");
        }
    }

    /**
     * Returns the full path to the class file.
     * @param pathToClassFile Relative path to the class file.
     * @return The full path to the class file as a Path object.
     */
    private static Path getFullPath(String pathToClassFile) {
        return Paths.get(Paths.get("").toAbsolutePath().toString(), Constants.PATH_TO_GAME_CLASS_FOLDER, pathToClassFile);
    }

    /**
     * Returns the path to the backup file.
     * @param originalPath Original path to the file being backed up.
     * @return Path to the backup file as a Path object.
     */
    private static Path getBackupPath(Path originalPath) {
        return Paths.get(originalPath.toString() + BACKUP_EXTENSION);
    }
}
