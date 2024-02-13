package io.xlorey.fluxloader.interfaces;

import imgui.ImVec2;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 10.02.2024
 * Description: Class template for implementing plugin controls
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public interface IControlsWidget {
    /**
     * Rendering controls in the plugin configuration menu
     * @param parentSize parent panel dimensions
     */
    void render(ImVec2 parentSize);
}