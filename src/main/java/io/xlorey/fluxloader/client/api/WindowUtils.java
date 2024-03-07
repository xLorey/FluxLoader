package io.xlorey.fluxloader.client.api;

import lombok.experimental.UtilityClass;
import zombie.core.Core;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.03.2024
 * Description: A set of tools for managing the game window
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@UtilityClass
public class WindowUtils {
    /**
     * Returns the height of the window.
     * @return The height of the window.
     */
    public static int getWindowHeight() {
        return Core.getInstance().getScreenHeight();
    }

    /**
     * Returns the width of the window.
     * @return The width of the window.
     */
    public static int getWindowWidth() {
        return Core.getInstance().getScreenWidth();
    }
}
