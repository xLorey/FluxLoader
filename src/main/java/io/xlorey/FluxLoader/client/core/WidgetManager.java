package io.xlorey.FluxLoader.client.core;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import io.xlorey.FluxLoader.client.api.TextTools;
import io.xlorey.FluxLoader.client.ui.pluginMenu.MainMenuButton;
import io.xlorey.FluxLoader.client.ui.pluginMenu.PluginMenu;
import io.xlorey.FluxLoader.interfaces.IWidget;
import io.xlorey.FluxLoader.utils.Constants;
import org.lwjglx.opengl.Display;
import zombie.GameWindow;
import zombie.core.Core;
import zombie.core.opengl.RenderThread;
import zombie.gameStates.MainScreenState;
import zombie.ui.UIFont;

import java.util.ArrayList;

/**
 * Custom UI management system
 */
public class WidgetManager {
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
     * A register of all UI elements that should be rendered
     */
    public static ArrayList<IWidget> widgetsRegistry = new ArrayList<>();

    /**
     * Initializing the UI Manager
     */
    public static void init(){
        RenderThread.invokeOnRenderContext(WidgetManager::initImGui);
        loadPluginsMenu();
    }

    /**
     * Initializing the ImGui context
     */
    private static void initImGui() {
        ImGui.createContext();

        ImGuiIO io = ImGui.getIO();
        // Preventing UI Elements from Saving State
        io.setIniFilename(null);

        imGuiGlfw.init(windowHandler, true);
        imGuiGl3.init("#version 130");

        isImGuiInit = true;
    }

    /**
     * Loading a custom plugin management menu
     */
    private static void loadPluginsMenu() {
        MainMenuButton screenMenuButton = new MainMenuButton();
        screenMenuButton.addToScreenDraw();

        PluginMenu pluginMenu = new PluginMenu();
        pluginMenu.addToScreenDraw();
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
        TextTools.drawText(creditsText, font, margin,  screenHeight - fontHeight - margin, 1f, 1f, 1f, 0.5f);
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

        for (IWidget widget : widgetsRegistry) {
            widget.update();

            if (widget.isVisible()) {
                widget.render();
            }
        }

        ImGui.render();

        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }
}
