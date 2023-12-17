package io.xlorey.FluxLoader.client.core;

import imgui.ImGui;
import imgui.flag.ImGuiMouseCursor;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import io.xlorey.FluxLoader.client.api.TextTools;
import io.xlorey.FluxLoader.utils.Constants;
import org.lwjglx.opengl.Display;
import zombie.GameWindow;
import zombie.core.Core;
import zombie.core.opengl.RenderThread;
import zombie.gameStates.MainScreenState;
import zombie.ui.UIFont;

/**
 * Custom UI management system
 */
public class UIManager {
    /**
     * Pointer to the game window
     */
    private static final long windowHandler = Display.getWindow();

    /**
     * Implementation of ImGui GLFW
     */
    private final static ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();

    /**
     * Implementation of ImGui GL3
     */
    private final static ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    /**
     * Flag indicating the initialization state of ImGui
     */
    private static boolean isImGuiInit = false;

    /**
     * Initializing the UIManager
     */
    public static void init(){
        RenderThread.invokeOnRenderContext(UIManager::initImGui);
    }

    /**
     * Initializing the ImGui context
     */
    private static void initImGui() {
        ImGui.createContext();

        imGuiGlfw.init(windowHandler, true);
        imGuiGl3.init("#version 130");

        isImGuiInit = true;
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

    /**
     * Handling drawing of ImGui elements
     */
    public static void drawImGui() {
        if (!isImGuiInit) return;

        imGuiGlfw.newFrame();
        ImGui.newFrame();

        ImGui.showDemoWindow();

        ImGui.render();

        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }
}
