package io.xlorey.fluxloader.utils;

import io.xlorey.fluxloader.Installer;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: A set of tools for creating backups of game files and restoring them
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@UtilityClass
public class BackupTools {
    private static final String BACKUP_EXTENSION = ".bkup"; // Backup file extensions

    /**
     * Retrieves a list of additional files in the specified folder that are related to the original file.
     * An additional file is considered related if its name contains the name of the original file (excluding its extension),
     * and does not have a backup extension. This method is useful for identifying dependent or related class files,
     * such as inner classes, that should also be backed up or restored along with the original file.
     * @param folderPath       the path to the folder where the search for related files is performed.
     * @param originalFilePath the path to the original file, which is used to identify related files.
     * @return an ArrayList of Paths to the additional files that match the criteria.
     * @throws IOException if an error occurs while accessing the file system.
     */
    private static ArrayList<Path> getAdditionalFiles(Path folderPath, Path originalFilePath) throws IOException {
        ArrayList<Path> additionalFiles = new ArrayList<>();
        try (Stream<Path> files = Files.list(folderPath)) {
            files.forEach(file -> {
                String fileName = file.getFileName().toString().replace(".class", "");
                String originalFileName = originalFilePath.getFileName().toString().replace(".class", "");
                if ((fileName.contains(originalFileName + "$") || fileName.equals(originalFileName)) && !fileName.contains(BACKUP_EXTENSION)){
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
     * Validates a list of additional files to ensure they are suitable for backup. This involves checking
     * each file to determine if a backup already exists. Files with existing backups are excluded from the
     * returned list, as they do not require another backup to be created.
     * @param additionalFiles the list of additional files that were initially identified as candidates for backup.
     * @return an ArrayList of Paths to the files that have been validated for backup, excluding any files
     *         for which a backup already exists.
     */
    private static ArrayList<Path> validateAdditionalFilesForBackup(ArrayList<Path> additionalFiles) {
        ArrayList<Path> validatedFiles = new ArrayList<>();
        for (Path file : additionalFiles) {
            Path backupPath = file.resolveSibling(file.getFileName() + BACKUP_EXTENSION);
            if (Files.exists(backupPath)) {
                Logger.print(String.format("Backup of the file '%s' already exists. Skipping backup...", file.getFileName()));
            } else {
                validatedFiles.add(file);
            }
        }
        return validatedFiles;
    }

    /**
     * Creates backup copies of the specified class file and any additional related files. This method first checks
     * for the existence of the specified class file and verifies that it has not been previously modified (based on
     * a specified annotation). It then identifies additional related files (e.g., inner classes) and creates backup
     * copies of these files, assuming no backups already exist.
     * @param className the fully qualified name of the class to back up, using dots (.) to separate package names.
     * @throws IOException if there is an issue accessing the file system or creating the backup files.
     * @throws Exception if the specified class file does not exist or if modifications are detected in the class file.
     */
    public static void createBackup(String className) throws Exception {
        String fixedClassPath = Installer.getGameFolderPath() + className.replace(".", "/") + ".class";

        Path pathToClassFile = Paths.get(fixedClassPath);
        Logger.print(String.format("Preparing to create a backup file '%s'...", pathToClassFile.getFileName()));

        if (!Files.exists(pathToClassFile))  {
            throw new IOException("Error creating backup file! The file is missing from the game folder: " + pathToClassFile.getFileName());
        }

        if (PatchTools.isClassHasAnnotationInMethods(className, "io.xlorey.fluxloader.annotations.Modified")){
            throw new Exception("Error creating backup file! Traces of file modification were found: " + pathToClassFile.getFileName());
        };

        Path directoryPath = pathToClassFile.getParent();

        ArrayList<Path> additionalFiles = validateAdditionalFilesForBackup(getAdditionalFiles(directoryPath, pathToClassFile));

        try {
            for (Path file : additionalFiles) {
                Path backupPath = file.resolveSibling(file.getFileName() + BACKUP_EXTENSION);
                Files.copy(file, backupPath);

                Logger.print(String.format("Creation of backup file '%s' was successful!", file.getFileName()));
            }

        } catch (IOException e) {
            Logger.print("Error while creating backup file!");
            throw e;
        }

    }

    /**
     * Validates a list of additional files to ensure they are suitable for restoration. This involves checking
     * each file to determine if a corresponding backup exists. Only files with existing backups are included in
     * the returned list, as these are the files that can be restored from their backups.
     * @param additionalFiles the list of additional files that were initially identified as candidates for restoration.
     * @return an ArrayList of Paths to the files that have been validated for restoration, including only files
     *         for which a backup exists.
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
     * Restores the specified class file and any additional related files from their backups. This method identifies
     * additional related files (e.g., inner classes) based on the specified class file and restores them from their
     * backups, overwriting the current files. This is useful for reverting changes made to a class and its related files.
     * @param className the fully qualified name of the class to restore, using dots (.) to separate package names.
     * @throws IOException if there is an issue accessing the file system or restoring the files from their backups.
     */
    public static void restoreFile(String className) throws IOException {
        String fixedClassPath = Installer.getGameFolderPath() + className.replace(".", "/") + ".class";

        Path pathToClassFile = Paths.get(fixedClassPath);

        Logger.print(String.format("Preparing to restore a file '%s'...", pathToClassFile.getFileName()));

        Path directoryPath = pathToClassFile.getParent();

        ArrayList<Path> additionalFiles = validateAdditionalFilesForRestore(getAdditionalFiles(directoryPath, pathToClassFile));

        try {
            for (Path file : additionalFiles) {
                Path backupPath = file.resolveSibling(file.getFileName() + BACKUP_EXTENSION);
                Files.move(backupPath, file, StandardCopyOption.REPLACE_EXISTING);

                Logger.print(String.format("Game file '%s' was successfully restored!", file.getFileName()));
            }
        } catch (IOException e) {
            Logger.print("Error when restoring the game file.");
            throw e;
        }
    }
}