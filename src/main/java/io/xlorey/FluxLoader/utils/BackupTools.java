package io.xlorey.FluxLoader.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.stream.Stream;

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
     * Retrieves a list of additional files from the specified folderPath that match the criteria.
     * Additional files are those whose names contain the originalFileName (excluding backups).
     * @param folderPath       The path to the folder where files are searched.
     * @param originalFilePath The path to the original file used as a reference.
     * @return An ArrayList of additional files that meet the criteria.
     * @throws IOException If an error occurs while listing files in the directory.
     */
    private static ArrayList<Path> getAdditionalFiles(Path folderPath, Path originalFilePath) throws IOException {
        ArrayList<Path> additionalFiles = new ArrayList<>();
        try (Stream<Path> files = Files.list(folderPath)) {
            files.forEach(file -> {
                String fileName = file.getFileName().toString().replace(".class", "");
                String originalFileName = originalFilePath.getFileName().toString().replace(".class", "");
                if (fileName.contains(originalFileName) && fileName.contains("$") && !fileName.contains(BACKUP_EXTENSION)){
                    additionalFiles.add(file.toAbsolutePath());
                }
            });
        } catch (IOException e) {
            Logger.print("Error while listing files in the directory.");
            throw e;
        }
        return additionalFiles;
    }

    /**
     * Validates a list of additional files for backup, ensuring that backup files do not exist.
     * @param additionalFiles The list of additional files to validate.
     * @return An ArrayList of validated additional files without existing backups.
     */
    private static ArrayList<Path> validateAdditionalFilesForBackup(ArrayList<Path> additionalFiles) {
        ArrayList<Path> validatedFiles = new ArrayList<>();
        for (Path file : additionalFiles) {
            Path backupPath = file.resolveSibling(file.getFileName() + BACKUP_EXTENSION);
            if (Files.exists(backupPath)) {
                Logger.print(String.format("Backup of the file '%s' already exists. Skipping backup.", file.getFileName()));
            } else {
                validatedFiles.add(file);
            }
        }
        return validatedFiles;
    }

    /**
     * Validates a list of additional files for restore, ensuring that backup files exist for each.
     * @param additionalFiles The list of additional files to validate.
     * @return An ArrayList of validated additional files with existing backups.
     */
    private static ArrayList<Path> validateAdditionalFilesForRestore(ArrayList<Path> additionalFiles) {
        ArrayList<Path> validatedFiles = new ArrayList<>();
        for (Path file : additionalFiles) {
            Path backupPath = file.resolveSibling(file.getFileName() + BACKUP_EXTENSION);
            if (!Files.exists(backupPath)) {
                Logger.print(String.format("Backup file for '%s' not found. Skipping restore...", file.getFileName()));
            } else {
                validatedFiles.add(file);
            }
        }
        return validatedFiles;
    }

    /**
     * Creating a backup file
     * @param pathToClassFile path to the file with '.class' extension
     * @throws IOException in cases of unsuccessful backup creation
     */
    public static void createBackup(String pathToClassFile) throws IOException {
        Logger.print(String.format("Trying to create a restore point for a file '%s'...", pathToClassFile));

        validateClassFilePath(pathToClassFile);

        Path originalFilePath = getFullPath(pathToClassFile);
        Path backupFilePath = getBackupPath(originalFilePath);

        Path directoryPath = originalFilePath.getParent();

        ArrayList<Path> additionalFiles = validateAdditionalFilesForBackup(getAdditionalFiles(directoryPath, originalFilePath));

        if (Files.exists(backupFilePath)) {
            Logger.print(String.format("Backup of the file '%s' already exists. Skipping backup.", originalFilePath.getFileName()));
            return;
        }

        try {
            Files.copy(originalFilePath, backupFilePath);

            Logger.print(String.format("Backup for file '%s' saved!", originalFilePath.getFileName()));

            for (Path file : additionalFiles) {
                Path backupPath = file.resolveSibling(file.getFileName() + BACKUP_EXTENSION);
                Files.copy(file, backupPath);

                Logger.print(String.format("Backup for file '%s' saved!", file.getFileName()));
            }

        } catch (IOException e) {
            Logger.print("Error while creating backup file.");
            throw e;
        }

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

        Path directoryPath = originalFilePath.getParent();

        ArrayList<Path> additionalFiles = validateAdditionalFilesForRestore(getAdditionalFiles(directoryPath, originalFilePath));

        if (!Files.exists(backupFilePath)) {
            Logger.print(String.format("Backup file for '%s' not found. Skipping restore", originalFilePath.getFileName()));
            return;
        }

        try {
            Files.move(backupFilePath, originalFilePath, StandardCopyOption.REPLACE_EXISTING);

            Logger.print(String.format("File '%s' was restored!", originalFilePath.getFileName()));

            for (Path file : additionalFiles) {
                Path backupPath = file.resolveSibling(file.getFileName() + BACKUP_EXTENSION);
                Files.move(backupPath, file, StandardCopyOption.REPLACE_EXISTING);

                Logger.print(String.format("File '%s' was restored!", file.getFileName()));
            }
        } catch (IOException e) {
            Logger.print("Error when restoring the game file.");
            throw e;
        }

    }

    /**
     * Checking the path for backup capability
     * @param path path to file
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
