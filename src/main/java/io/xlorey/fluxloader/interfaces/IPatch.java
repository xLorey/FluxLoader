package io.xlorey.fluxloader.interfaces;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Interface for patcher classes
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public interface IPatch {
    /**
     * Getting the full name of the class that needs to be modified.
     * @return the full class name, e.g. 'zombie.GameWindow'
     */
    String getClassName();

    /**
     * The patch method, which is called to make changes to the class
     */
    void patch();
}
