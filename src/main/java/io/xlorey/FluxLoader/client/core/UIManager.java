package io.xlorey.FluxLoader.client.core;

import io.xlorey.FluxLoader.client.api.TextTools;
import io.xlorey.FluxLoader.utils.Constants;
import zombie.GameWindow;
import zombie.core.Core;
import zombie.gameStates.MainScreenState;
import zombie.ui.UIFont;

/**
 * Custom UI management system
 */
public class UIManager {
    /**
     * Initializing the UIManager
     */
    public static void init(){
    }

    /**
     * FluxLoader credits rendering update
     */
    public static void drawCredits(){
        UIFont font = UIFont.Small;
        int margin = 15;
        int screenHeight = Core.getInstance().getScreenHeight();
        int fontHeight = TextTools.getFontHeight(font);
        String creditsText = String.format("Modded with %s (v%s)", Constants.FLUX_NAME, Constants.FLUX_VERSION);
        TextTools.drawText(creditsText, font, margin,  screenHeight - fontHeight - margin, 1f, 1f, 1f, 1f);
    }

    /**
     * Updating the UI manager to render its components
     */
    public static void update() {
        if (GameWindow.states.current instanceof MainScreenState){
            drawCredits();
        }
    }
}
