package io.xlorey.fluxloader.client.core;

import imgui.ImFontConfig;
import imgui.ImFontGlyphRangesBuilder;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import io.xlorey.fluxloader.client.api.TextUtils;
import io.xlorey.fluxloader.client.core.fonts.FontAwesomeIcons;
import io.xlorey.fluxloader.client.ui.Widget;
import io.xlorey.fluxloader.client.ui.menu.Button;
import io.xlorey.fluxloader.client.ui.menu.Menu;
import io.xlorey.fluxloader.plugin.PluginRegistry;
import io.xlorey.fluxloader.utils.Constants;
import org.lwjglx.opengl.Display;
import zombie.GameWindow;
import zombie.core.Core;
import zombie.core.opengl.RenderThread;
import zombie.gameStates.MainScreenState;
import zombie.ui.UIFont;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 10.02.2024
 * Description: Custom UI management system
 * <p>FluxLoader © 2024. All rights reserved.</p>
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
    public static ArrayList<Widget> widgetsRegistry = new ArrayList<>();

    /**
     * Flag showing mouse capture for ImGui widgets (prevents events for standard UI)
     */
    private static boolean isMouseCapture = false;

    /**
     * Returns the value of the mouse capture flag.
     * @return true if the mouse is captured, false otherwise.
     */
    public static boolean isMouseCapture() {
        return isMouseCapture;
    }

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

        initFonts(io);

        imGuiGlfw.init(windowHandler, true);
        imGuiGl3.init("#version 330 core");

        isImGuiInit = true;
    }

    /**
     * Initializing fonts
     * @param io ImGui IO object
     */
    private static void initFonts(ImGuiIO io) {
        io.getFonts().addFontDefault();

        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder(); // Glyphs ranges provide
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesDefault());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesCyrillic());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesJapanese());
        rangesBuilder.addRanges(FontAwesomeIcons._IconRange);

        final ImFontConfig fontConfig = new ImFontConfig();
        fontConfig.setMergeMode(true);

        final short[] glyphRanges = rangesBuilder.buildRanges();
        io.getFonts().addFontFromMemoryTTF(loadFromResources("Arial.ttf"), 14, fontConfig, glyphRanges); // cyrillic glyphs
        io.getFonts().addFontFromMemoryTTF(loadFromResources("NotoSansCJKjp-Medium.otf"), 14, fontConfig, glyphRanges); // japanese glyphs
        io.getFonts().addFontFromMemoryTTF(loadFromResources("fa-regular-400.ttf"), 14, fontConfig, glyphRanges); // font awesome
        io.getFonts().addFontFromMemoryTTF(loadFromResources("fa-solid-900.ttf"), 14, fontConfig, glyphRanges); // font awesome
        io.getFonts().build();

        fontConfig.destroy();
    }

    /**
     * Loading fonts from the FluxLoader resource folder
     * @param name font name
     * @return byte array of the loaded font
     */
    private static byte[] loadFromResources(String name) {
        try {
            return Files.readAllBytes(Paths.get(Constants.PATH_FLUXLOADER_RESOURCES, "fonts", name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loading a custom plugin management menu
     */
    private static void loadPluginsMenu() {
        Button screenMenuButton = new Button();
        screenMenuButton.addToScreen();

        Menu pluginMenu = new Menu();
        pluginMenu.addToScreen();
    }
    /**
     * FluxLoader credits rendering update
     */
    public static void drawCredits() {
        if (GameWindow.states.current instanceof MainScreenState){
            UIFont font = UIFont.Small;
            int margin = 10;
            int screenHeight = Core.getInstance().getScreenHeight();
            int fontHeight = TextUtils.getFontHeight(font);
            int serverCount = PluginRegistry.getLoadedServerPlugins().size();
            int clientCount = PluginRegistry.getLoadedClientPlugins().size();
            int totalPlugins = clientCount + serverCount;
            String creditsText = String.format("Patched with %s v%s", Constants.FLUX_NAME, Constants.FLUX_VERSION);
            String pluginsText = String.format("Plugins: %s (Client: %s | Server: %s)", totalPlugins, clientCount, serverCount);
            TextUtils.drawText(creditsText, font, margin,  screenHeight - fontHeight * 2 - margin, 1f, 1f, 1f, 0.5f);
            TextUtils.drawText(pluginsText, font, margin,  screenHeight - fontHeight - margin, 1f, 1f, 1f, 0.5f);
        }
    }

    /**
     * Handling drawing of ImGui elements
     */
    public static void drawImGui() {
        if (!isImGuiInit) return;

        imGuiGlfw.newFrame();
        ImGui.newFrame();

        boolean isScreenWidgetHovered = false;

        for (Widget widget : widgetsRegistry) {
            widget.update();

            if (widget.isVisible()) {
                widget.render();
            }

            if(widget.isWidgetHovered()) {
                isScreenWidgetHovered = true;
                isMouseCapture = true;
            }
        }

        if (!isScreenWidgetHovered) {
            isMouseCapture = false;
        }

        ImGui.render();

        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }
}