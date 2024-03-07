package io.xlorey.fluxloader.shared;

import io.xlorey.fluxloader.utils.Logger;
import lombok.experimental.UtilityClass;
import zombie.ZomboidFileSystem;
import zombie.network.CoopMaster;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Author: Deknil
 * GitHub: <a href="https://github.com/Deknil">https://github.com/Deknil</a>
 * Date: 27.02.2024
 * Description: Provides utility methods for managing Lua scripts.
 * <p> FluxLoader © 2024. All rights reserved. </p>
 */
@UtilityClass
public class LuaManager {
    /**
     * Executes a Lua script located at the specified path.
     * @param path The path to the Lua script.
     * @param rewriteEvents Determines whether to rewrite events in the Lua script.
     */
    public static void runLua(String path, boolean rewriteEvents) {
        try {
            zombie.Lua.LuaManager.RunLua(path, rewriteEvents);
        } catch (Exception e) {
            Logger.print(String.format("A critical error occurred while loading lua file '%s'!", path));
            e.printStackTrace();
        }
    }

    /**
     * Searches for Lua files recursively in the specified folder and its subfolders,
     * adds them to the paths collection, and then runs each Lua file.
     * After all files are loaded, fires the `OnLuaFilesLoaded` event
     * @param folderPath The path to the folder containing Lua files.
     * @param rewriteEvents A boolean indicating whether to rewrite events.
     * @throws NullPointerException if the specified folder path is null.
     * @throws IllegalArgumentException if the specified folder path is empty.
     * @throws RuntimeException if an error occurs while accessing the 'paths' field or running Lua files.
     */
    public static void loadLuaFromFolder(String folderPath, boolean rewriteEvents) {
        Path pathFolder = Paths.get(folderPath);
        String basePath = pathFolder.normalize().toAbsolutePath().toString().replace("\\", "/");

        if (!Files.exists(pathFolder)) {
            Logger.print(String.format("Path '%s' does not exist. Lua scripts cannot be loaded from this path.", folderPath));
            return;
        }

        if (!Files.isDirectory(pathFolder)) {
            Logger.print(String.format("Path '%s' is not a directory. Lua scripts cannot be loaded from this path.", folderPath));
            return;
        }

        if (!basePath.endsWith("/")) {
            basePath += "/";
        }

        addLuaPath(basePath);

        HashSet<String> luaFiles = findLuaFiles(basePath);

        for (String luaFile : luaFiles) {
            String fixedLuaPath = luaFile.replace("\\", "/");
            runLua(fixedLuaPath, rewriteEvents);
            CoopMaster.instance.update();
        }

        EventManager.invokeEvent("onLuaFilesLoaded", folderPath);
    }

    /**
     * Adds the specified folder to the active Lua path.
     * Adding a folder will allow lua files to access other lua files inside the folder via 'require'
     * @param folder the folder to add to the Lua path
     */
    public static void addLuaActiveFolder(File folder) {
        String basePath = folder.toPath().normalize().toAbsolutePath().toString().replace("\\", "/");

        if (!folder.exists() || !folder.isDirectory()) return;

        if (!basePath.endsWith("/")) {
            basePath += "/";
        }

        addLuaPath(basePath);
        findLuaFiles(basePath);
    }

    /**
     * Adds the specified base path to the Lua paths if it's not already included.
     * @param basePath the base path to add to the Lua paths
     */
    @SuppressWarnings("unchecked")
    private static synchronized void addLuaPath(String basePath) {
        try {
            Class<?> luaManagerClass = zombie.Lua.LuaManager.class;
            Field pathsField = luaManagerClass.getDeclaredField("paths");
            pathsField.setAccessible(true);
            ArrayList<String> paths = (ArrayList<String>) pathsField.get(null);
            if (paths == null) return;

            if (!paths.contains(basePath)) {
                paths.add(basePath);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Logger.print(String.format("Critical error when adding Lua file path '%s' to the active list!", basePath));
            e.printStackTrace();
        }
    }

    /**
     * Recursively finds all Lua files within the specified folder and its subfolders.
     * @param folder The path to the folder to search for Lua files.
     * @return A set containing the absolute paths of all Lua files found.
     * @throws NullPointerException if the specified folder path is null.
     * @throws IllegalArgumentException if the specified folder path is empty.
     */
    private static HashSet<String> findLuaFiles(String folder) {
        ArrayList<String> luaFiles = new ArrayList<>();
        findLuaFilesRecursive(new File(folder), luaFiles);

        for (String luaPath : luaFiles) {
            Path folderPath = Paths.get(folder);
            Path filePath = Paths.get(luaPath);

            String keyPath = folderPath.resolve(folderPath.relativize(filePath)).toString().replace("\\", "/");

            ZomboidFileSystem.instance.ActiveFileMap.put(keyPath.toLowerCase(Locale.ENGLISH), luaPath.replace("\\", "/"));
        }

        Collections.sort(luaFiles);
        return new HashSet<>(luaFiles);
    }

    /**
     * Recursively searches for Lua files within the specified folder and its subfolders.
     * @param folder The folder to search for Lua files.
     * @param luaFiles The list to which the absolute paths of found Lua files will be added.
     * @throws NullPointerException if the specified folder is null.
     */
    private static void findLuaFilesRecursive(File folder, ArrayList<String> luaFiles) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    findLuaFilesRecursive(file, luaFiles);
                }
            }
        } else if (folder.isFile() && folder.getName().toLowerCase().endsWith(".lua")) {
            luaFiles.add(folder.getAbsolutePath());
        }
    }
}