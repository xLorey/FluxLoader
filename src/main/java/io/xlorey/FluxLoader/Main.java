package io.xlorey.FluxLoader;


import io.xlorey.FluxLoader.utils.Logger;
import lombok.experimental.UtilityClass;

/**
 * Main application class
 */
@UtilityClass
public class Main {
    /**
     * Application entry point
     * @param args Accepted arguments as flags
     * @exception Exception in cases of unsuccessful installation/uninstallation
     */
    public static void main(String[] args) throws Exception {
        Logger.printCredits();

        if (args.length != 1) {
            Logger.print("You specified an invalid number of flags! Use: '--install' or '--uninstall'");
            return;
        }

        switch (args[0]){
            case "--install" -> Installer.install();
            case "--uninstall" -> Installer.uninstall();
            default -> Logger.print("You specified an unknown flag! Use: '--install' or '--uninstall'");
        }
    }
}