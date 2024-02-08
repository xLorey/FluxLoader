package io.xlorey.fluxloader.client.states;

import io.xlorey.fluxloader.utils.Constants;
import zombie.GameTime;
import zombie.core.Core;
import zombie.core.SpriteRenderer;
import zombie.core.textures.Texture;
import zombie.gameStates.GameState;
import zombie.gameStates.GameStateMachine;
import zombie.input.GameKeyboard;
import zombie.input.Mouse;
import zombie.ui.UIManager;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: New state when starting the game - showing the bootloader logo
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class FluxLogoState extends GameState {

    /**
     * Current transparency value
     */
    private float alpha = 0.0f;

    /**
     * Logo display time
     */
    private float logoDisplayTime = 40.0f;

    /**
     * Current stage of logo display
     */
    private int stage = 0;

    /**
     * Target transparency value
     */
    private float targetAlpha = 0.0f;

    /**
     * Flag indicating whether rendering is necessary
     */
    private boolean noRender = false;

    /**
     * FluxLoader Logo
     */
    private final LogoElement fluxLogo = new LogoElement(Constants.PATH_FLUXLOADER_LOGO);


    /**
     * Default constructor
     */
    public FluxLogoState() {
    }

    /**
     * Method called when state is entered
     */
    public void enter() {
        UIManager.bSuspend = true;
        alpha = 0.0f;
        targetAlpha = 1.0f;
    }

    /**
     * Method called when exiting state
     */
    public void exit() {
        UIManager.bSuspend = false;
    }

    /**
     * Method that renders state elements
     */
    public void render() {
        Core core = Core.getInstance();
        if (noRender) {
            core.StartFrameUI();
            SpriteRenderer.instance.renderi(null, 0, 0, core.getOffscreenWidth(0), core.getOffscreenHeight(0), 0.0f, 0.0f, 0.0f, 1.0f, null);
            core.EndFrame();
        } else {
            core.StartFrameUI();
            core.EndFrame();

            boolean tempUseUIFBO = UIManager.useUIFBO;
            UIManager.useUIFBO = false;
            core.StartFrameUI();
            SpriteRenderer.instance.renderi(null, 0, 0, core.getOffscreenWidth(0), core.getOffscreenHeight(0), 0.0f, 0.0f, 0.0f, 1.0f, null);
            fluxLogo.centerOnScreen();
            fluxLogo.render(alpha);
            core.EndFrameUI();
            UIManager.useUIFBO = tempUseUIFBO;
        }
    }

    /**
     * Method that performs a state update
     */
    public GameStateMachine.StateAction update() {
        if (Mouse.isLeftDown() || GameKeyboard.isKeyDown(28) || GameKeyboard.isKeyDown(57) || GameKeyboard.isKeyDown(1)) {
            stage = 2;
        }

        GameTime gameTime = GameTime.getInstance();
        switch (stage) {
            case 0 -> {
                targetAlpha = 1.0f;
                if (alpha == 1.0f) {
                    stage = 1;
                }
            }
            case 1 -> {
                logoDisplayTime -= gameTime.getMultiplier() / 1.6f;
                if (logoDisplayTime <= 0.0f) {
                    stage = 2;
                }
            }
            case 2 -> {
                targetAlpha = 0.0f;
                if (alpha == 0.0f) {
                    noRender = true;
                    return GameStateMachine.StateAction.Continue;
                }
            }
        }

        updateAlpha(gameTime);

        return GameStateMachine.StateAction.Remain;
    }

    /**
     * Updates the current transparency value.
     * @param gameTime GameTime object to access time acceleration factor
     */
    private void updateAlpha(GameTime gameTime) {
        float alphaStep = 0.02f;
        float deltaTime = alphaStep * gameTime.getMultiplier();
        if (alpha < targetAlpha) {
            alpha += deltaTime;
            if (alpha > targetAlpha) {
                alpha = targetAlpha;
            }
        } else if (alpha > targetAlpha) {
            alpha -= deltaTime;
            if (stage == 2) {
                alpha -= deltaTime;
            }
            if (alpha < targetAlpha) {
                alpha = targetAlpha;
            }
        }
    }

    /**
     * Nested class describing a logo element
     */
    private static final class LogoElement {
        private final Texture texture;
        private int x;
        private int y;
        private int width;
        private int height;

        /**
         * Constructor with parameters
         * @param texturePath - path to logo texture
         */
        LogoElement(String texturePath) {
            texture = Texture.getSharedTexture(texturePath);
            if (texture != null) {
                width = texture.getWidth() / 3;
                height = texture.getHeight() / 3;
            }
        }

        /**
         * Method that aligns the logo to the center of the screen
         */
        void centerOnScreen() {
            Core core = Core.getInstance();
            x = (core.getScreenWidth() - width) / 2;
            y = (core.getScreenHeight() - height) / 2;
        }

        /**
         * Method that renders the logo
         * @param alpha - logo transparency
         */
        void render(float alpha) {
            if (texture != null && texture.isReady()) {
                SpriteRenderer.instance.renderi(texture, x, y, width, height, 1.0f, 1.0f, 1.0f, alpha, null);
            }
        }
    }
}