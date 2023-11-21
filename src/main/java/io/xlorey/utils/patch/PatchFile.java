package io.xlorey.utils.patch;

import java.io.IOException;

/**
 * Base file modification class
 */
public class PatchFile {
    /**
     * Path to the file being modified
     */
    public String filePath = "";

    /**
     * Injector constructor
     * @param filePath path to file
     */
    public PatchFile(String filePath){
        this.filePath = filePath;
    }

    /**
     * Making changes to a file
     */
    public void inject() throws Exception {
        BackupTools.createBackup(filePath);
    }

    /**
     * Rollback changes
     */
    public void rollBackChanges() throws IOException {
        BackupTools.restoreFile(filePath);
    }
}
