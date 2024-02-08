package io.xlorey.fluxloader.interfaces;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Base class of all loader plugins
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public interface IPlugin {
    /**
     * Plugin initialization event
     */
    void onInitialize();

    /**
     * Plugin start event
     */
    void onExecute();

    /**
     * Plugin termination event
     */
    void onTerminate();
}
