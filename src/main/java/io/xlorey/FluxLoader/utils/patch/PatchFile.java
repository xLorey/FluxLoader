package io.xlorey.FluxLoader.utils.patch;

import io.xlorey.FluxLoader.utils.BackupTools;

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
     * @exception Exception error when injection into game files failed
     */
    public void inject() throws Exception {
        BackupTools.createBackup(filePath);
    }

    /**
     * Rollback changes
     * @exception IOException error when file recovery fails
     */
    public void rollBackChanges() throws IOException {
        BackupTools.restoreFile(filePath);
    }
}
