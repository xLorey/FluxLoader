package io.xlorey.fluxloader.utils;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: A set of tools for working with Jar archives
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@UtilityClass
public class JarTools {
    /**
     * Unpacking a Jar archive in a given directory
     * @param whiteListJarPath whitelist of paths (directories and files) that can be unzipped
     * @param jarPath path to the Jar archive to be unzipped
     * @param unpackPath target path where you need to unzip files and folders
     * @exception IOException in case of failure to unpack the Jar file due to path problems
     */
    public static void unpackJar(ArrayList<String> whiteListJarPath, String jarPath, String unpackPath) throws IOException {
        try (JarFile jarFile = new JarFile(jarPath)) {
            File jarFileObj = new File(jarFile.getName());
            String jarFileName = jarFileObj.getName();

            Logger.print(String.format("Trying to unpack '%s' archive...", jarFileName));

            Enumeration<JarEntry> entries = jarFile.entries();

            Path targetPath = Paths.get(unpackPath).toAbsolutePath().normalize();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                if (isPathAllowed(whiteListJarPath, entry.getName())) {
                    Path extractPath = targetPath.resolve(entry.getName()).normalize();

                    if (!extractPath.startsWith(targetPath)) {
                        throw new IOException("Entry is outside of the target dir: " + entry.getName());
                    }

                    if (entry.isDirectory()) {
                        Files.createDirectories(extractPath);
                    } else {
                        try (InputStream inputStream = jarFile.getInputStream(entry)) {
                            Files.copy(inputStream, extractPath, StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                }
            }

            Logger.print(String.format("Unzipping '%s' completed!", jarFileName));
        }
    }

   /**
     * Retrieving a list of file paths in a given folder inside a JAR archive.
     * @param jarPath path to the JAR archive.
     * @param folderPath path to the folder inside the JAR archive.
     * @return a list of file paths.
     * @exception IOException on I/O error.
     */
   public static ArrayList<String> getFilesInJarFolder(String jarPath, String folderPath) throws IOException {
       ArrayList<String> filePaths = new ArrayList<>();

       Path folderPathObj = Paths.get(folderPath).normalize();

       try (JarFile jarFile = new JarFile(jarPath)) {
           Enumeration<JarEntry> entries = jarFile.entries();

           while (entries.hasMoreElements()) {
               JarEntry entry = entries.nextElement();
               String name = entry.getName();

               if (!entry.isDirectory()) {
                   Path entryPath = Paths.get(name).normalize();

                   if (entryPath.startsWith(folderPathObj)) {
                       filePaths.add(name);
                   }
               }
           }
       }

       return filePaths;
   }

    /**
     * Removing files from a Jar archive in the target directory
     * @param whiteListJarPath whitelist of paths that need to be checked for removal
     * @param jarPath path to the jar archive with which to compare deletion paths
     * @param targetPath target path from which files should be deleted
     * @exception IOException in cases of unsuccessful removal
     */
    public static void deleteJarFilesFromDirectory(ArrayList<String> whiteListJarPath, String jarPath, String targetPath) throws IOException {
        try (JarFile jarFile = new JarFile(jarPath)) {
            File jarFileObj = new File(jarFile.getName());
            String jarFileName = jarFileObj.getName();

            Logger.print(String.format("Trying to delete files from '%s' archive...", jarFileName));

            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (isPathAllowed(whiteListJarPath, entry.getName())) {
                    Path pathToDelete = Paths.get(targetPath, entry.getName()).normalize();

                    if (!pathToDelete.startsWith(Paths.get(targetPath).toAbsolutePath())) {
                        Logger.print(String.format("Invalid path: '%s' attempts to escape target path. Skipping deletion.", pathToDelete));
                        continue;
                    }

                    if (Files.exists(pathToDelete)) {
                        deleteRecursively(pathToDelete.toFile());
                    }
                }
            }

            Logger.print(String.format("Deleting archive files '%s' completed!", jarFileName));
        }
    }

    /**
     * Recursively deleting a file/folder
     * @param file file to be deleted
     */
    private static void deleteRecursively(File file) throws IOException {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteRecursively(entry);
                }
            }
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete: " + file);
        }
    }

    /**
     * Checks the path to see if it is whitelisted
     * @param whiteList array of strings - whitelist of paths
     * @param path analyzed path
     * @return true - if the array is empty or the path is whitelisted, false - the path is not whitelisted
     */
    private static boolean isPathAllowed(ArrayList<String> whiteList, String path) {
        if (whiteList.isEmpty()) {
            return true;
        }

        for (String whitePath : whiteList) {
            if (path.startsWith(whitePath)) {
                return true;
            }
        }

        return false;
    }
}