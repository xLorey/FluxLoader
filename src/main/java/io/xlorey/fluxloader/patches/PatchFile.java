package io.xlorey.fluxloader.patches;

import io.xlorey.fluxloader.interfaces.IPatch;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Template class for game class patchers
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class PatchFile implements IPatch {
    private final String className; // class name

    /**
     * Patcher constructor
     * @param className the full class name, e.g. 'zombie.GameWindow'
     */
    public PatchFile(String className) {
        this.className = className;
    }

    /**
     * Getting the full name of the class that needs to be modified.
     * @return the full class name, e.g. 'zombie.GameWindow'
     */
    @Override
    public String getClassName() {
        return className;
    }

    /**
     * The patch method, which is called to make changes to the class
     */
    @Override
    public void patch() {}
}
