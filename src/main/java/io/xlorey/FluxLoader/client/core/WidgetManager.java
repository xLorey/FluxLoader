package io.xlorey.FluxLoader.client.core;

import imgui.*;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import io.xlorey.FluxLoader.client.api.TextTools;
import io.xlorey.FluxLoader.client.fonts.FontData;
import io.xlorey.FluxLoader.client.fonts.FontAwesomeIcons;
import io.xlorey.FluxLoader.client.ui.ScreenWidget;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
     * Flag showing mouse capture for ImGui widgets (prevents events for standard UI)
     */
    private static boolean isMouseCapture = false;

    /**
     * Custom font data fonts
     */
    private static final Map<String, FontData> customFontsData = new HashMap<>();

    /**
     * Loaded custom fonts
     */
    private static final Map<String, ImFont> customFonts = new HashMap<>();

    /**
     * Change mouse capture state for ImGui widgets. Used only inside widgets via the captureMouseFocus method
     */
    public static void captureMouse() {
        isMouseCapture = true;
    }

    /**
     * Returns the value of the mouse capture flag.
     * @return true if the mouse is captured, false otherwise.
     */
    public static boolean isMouseCapture() {
        return isMouseCapture;
    }

    /**
     * Release the mouse from being captured by ImGui widgets
     */
    public static void releaseMouse() {
        isMouseCapture = false;
    }

    /**
     * Initializing the UI Manager
     */
    public static void init(){
        RenderThread.invokeOnRenderContext(WidgetManager::initImGui);
        loadPluginsMenu();
    }

    /**
     * Initializes and adds fonts to ImGui.
     * Adds a standard font, as well as fonts for Cyrillic, Japanese characters and FontAwesome icons.
     * Fonts are merged to provide support for different glyph ranges in a single font.
     * @param io An ImGuiIO object used to configure fonts.
     */
    private static void initFonts(final ImGuiIO io) {
        // Add default font for latin glyphs
        io.getFonts().addFontDefault();

        // Create a builder for glyph ranges
        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder();
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesDefault()); // Latin
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesCyrillic()); // Cyrillic
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesJapanese()); // Japanese
        rangesBuilder.addRanges(FontAwesomeIcons._IconRange); // FontAwesome icons

        // Create a configuration for additional fonts
        final ImFontConfig fontConfig = new ImFontConfig();
        fontConfig.setMergeMode(true); // Enable merge mode

        // Build glyph ranges
        final short[] glyphRanges = rangesBuilder.buildRanges();

        // Add custom fonts with merged glyph ranges
        io.getFonts().addFontFromMemoryTTF(loadFont("Tahoma.ttf"), 14, fontConfig, glyphRanges); // Cyrillic glyphs
        io.getFonts().addFontFromMemoryTTF(loadFont("NotoSansCJKjp-Medium.otf"), 14, fontConfig, glyphRanges); // Japanese glyphs
        io.getFonts().addFontFromMemoryTTF(loadFont("fa-regular-400.ttf"), 14, fontConfig, glyphRanges); // FontAwesome regular
        io.getFonts().addFontFromMemoryTTF(loadFont("fa-solid-900.ttf"), 14, fontConfig, glyphRanges); // FontAwesome solid

        // Add custom fonts
        for (Map.Entry<String, FontData> entry : customFontsData.entrySet()) {
            String fontName = entry.getKey();
            FontData fontData = entry.getValue();

            // Use the same fontConfig and glyphRanges for custom fonts
            ImFont font = io.getFonts().addFontFromMemoryTTF(fontData.getFontData(), fontData.getSize(), fontConfig, glyphRanges);

            customFonts.put(fontName, font);
        }

        io.getFonts().build();
        fontConfig.destroy();
    }

    /**
     * Gets a custom font by its name.
     * @param fontName The name of the font to get.
     * @return An ImFont object representing the custom font, or null,
     * if a font with the specified name is not found.
     */
    public static ImFont getCustomFont(String fontName) {
        return customFonts.getOrDefault(fontName, null);
    }

    /**
     * Loads a font from a file.
     * @param name The name of the font file.
     * @return A byte array containing the font data.
     */
    private static byte[] loadFont(String name) {
        try {
            String fontPath = "io/xlorey/FluxLoader/media/fonts/" + name;
            return Files.readAllBytes(Paths.get(fontPath));
        } catch (IOException e) {
            throw new RuntimeException("Error loading font: " + name, e);
        }
    }

    /**
     * Loads font data from the specified URL.
     * @param url URL to the font file.
     * @return A byte array containing the font data.
     */
    private static byte[] loadFont(URL url) {
        try (InputStream is = url.openStream()) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error loading font from URL: " + url, e);
        }
    }

    /**
     * Loads and adds a font to ImGui from the resource specified by path.
     * This method is intended for use with fonts embedded in the application JAR file.
     * @param path Path to the font resource in the JAR file.
     * @param fontName Font name (Its ID)
     * @param size Font size.
     */
    public static void addCustomFont(URL path, String fontName, float size) {
        final ImFontConfig fontConfig = new ImFontConfig();

        byte[] fontData = loadFont(path);

        FontData font = new FontData(fontName, size, fontData);

        customFontsData.put(fontName, font);

        fontConfig.destroy();
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

        boolean isScreenWidgetHovered = false;

        for (IWidget widget : widgetsRegistry) {
            widget.update();

            if (widget.isVisible()) {
                widget.render();
            }
            if (widget instanceof ScreenWidget screenWidget) {
                if(screenWidget.isWidgetHovered()) {
                    isScreenWidgetHovered = true;
                    captureMouse();
                }
            }
        }

        if (!isScreenWidgetHovered) {
            releaseMouse();
        }

        ImGui.render();

        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }
}
