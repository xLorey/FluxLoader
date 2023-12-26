package io.xlorey.FluxLoader.client.ui.pluginMenu;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import io.xlorey.FluxLoader.client.ui.ScreenWidget;
import io.xlorey.FluxLoader.interfaces.IControlsWidget;
import io.xlorey.FluxLoader.plugin.Metadata;
import io.xlorey.FluxLoader.plugin.Plugin;
import io.xlorey.FluxLoader.shared.PluginManager;
import io.xlorey.FluxLoader.utils.Constants;
import zombie.GameWindow;
import zombie.core.textures.Texture;
import zombie.gameStates.MainScreenState;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Plugin management menu
 */
public class PluginMenu extends ScreenWidget {
    /**
     * Flag indicating whether the window is open
     */
    private static final ImBoolean isOpened = new ImBoolean(false);

    /**
     * ID of the selected plugin in the menu
     */
    private String selectedPluginId;

    /**
     * Loaded client plugins
     */
    private final Map<String, Plugin> loadedPlugins = PluginManager.getLoadedClientPlugins();

    /**
     * Initializing the widget
     */
    public PluginMenu(){
        if (!loadedPlugins.isEmpty()) {
            selectedPluginId = loadedPlugins.keySet().iterator().next();
        }
    }

    /**
     * Update window state
     */
    @Override
    public void update() {
        super.update();

        setVisible((GameWindow.states.current instanceof MainScreenState) && isOpened());
    }

    /**
     * Sets the visibility state of the plugin's menu.
     * This method allows you to programmatically open or close the plugin menu.
     * @param open true to open the plugins menu; false to close.
     */
    public static void setOpen(boolean open) {
        isOpened.set(open);
    }

    /**
     * Returns the current visibility state of the plugin's menu.
     * This method allows you to determine whether the plugins menu is currently open.
     * @return true if the plugins menu is open; false if closed.
     */
    public static boolean isOpened() {
        return isOpened.get();
    }

    /**
     * Rendering the plugin menu
     */
    @Override
    public void render() {
        ImGui.setNextWindowSize(650, 400);

        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 8);
        ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 10, 10);
        ImGui.pushStyleVar(ImGuiStyleVar.ScrollbarSize, 3);

        ImGui.pushStyleColor(ImGuiCol.WindowBg, 12, 12, 12, 255);
        ImGui.pushStyleColor(ImGuiCol.TitleBg, 30, 30, 30, 255);
        ImGui.pushStyleColor(ImGuiCol.TitleBgActive, 30, 30, 30, 255);

        int emeraldActive = ImGui.getColorU32(0.0f, 0.55f, 0.55f, 1.0f);
        int emeraldHovered = ImGui.getColorU32(0.0f, 0.65f, 0.65f, 1.0f);
        int emeraldNormal = ImGui.getColorU32(0.0f, 0.60f, 0.60f, 1.0f);

        ImGui.pushStyleColor(ImGuiCol.ButtonActive, emeraldActive);
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, emeraldHovered);
        ImGui.pushStyleColor(ImGuiCol.Button, emeraldNormal);

        ImGui.begin(Constants.FLUX_NAME + " - Plugins", isOpened, ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse);

        captureMouseFocus();
        
        if(loadedPlugins.isEmpty()) {
            String text = "No client plugins found";
            float windowWidth = ImGui.getWindowWidth();
            float windowHeight = ImGui.getWindowHeight();
            float textWidth = ImGui.calcTextSize(text).x;
            float textHeight = ImGui.calcTextSize(text).y;

            ImGui.setCursorPosX((windowWidth - textWidth) * 0.5f);
            ImGui.setCursorPosY((windowHeight - textHeight) * 0.5f);
            ImGui.text(text);
        } else {
            renderPluginsInfo();
        }

        ImGui.end();

        ImGui.popStyleColor(6);
        ImGui.popStyleVar(3);
    }

    /**
     * Rendering information about plugins
     */
    private void renderPluginsInfo() {
        ImGui.columns(2);

        ImGui.setColumnWidth(0, 256);

        ImGui.beginChild("#LeftPluginMenuColumn", -1, -1, false);
        HashSet<String> displayedPlugin = new HashSet<>();

        for (Map.Entry<String, Plugin> entry : loadedPlugins.entrySet()) {
            Plugin plugin = entry.getValue();
            Metadata metadata = plugin.getMetadata();
            String mapKey = entry.getKey();
            String metadataKey = metadata.getId() + ":" + metadata.getVersion();

            if (!displayedPlugin.contains(metadataKey)) {
                boolean isSelected = selectedPluginId != null && selectedPluginId.equals(mapKey);
                if (!isSelected) {
                    ImGui.pushStyleColor(ImGuiCol.Button, 0, 0, 0, 0);
                }

                if (ImGui.button(plugin.getMetadata().getName(), -1, 30)) {
                    selectedPluginId = mapKey;
                }

                if (!isSelected) {
                    ImGui.popStyleColor();
                }
                displayedPlugin.add(metadataKey);
            }
        }

        ImGui.endChild();

        ImGui.nextColumn();

        ImGui.beginChild("#RightPluginMenuColumn", -1, -1, false);
        if (selectedPluginId != null) {
            Plugin selectedPlugin = loadedPlugins.get(selectedPluginId);
            Metadata metadata = selectedPlugin.getMetadata();

            Texture icon = PluginManager.getPluginIconRegistry().get(metadata.getId());
            float iconSize = 256;
            float windowWidth = ImGui.getWindowWidth();
            float iconPosX = (windowWidth - iconSize) * 0.5f;

            ImGui.setCursorPosX(iconPosX);
            ImGui.image(icon.getID(), iconSize, iconSize);

            ImGui.textWrapped("Name: " + metadata.getName());
            ImGui.textWrapped("Description: " + metadata.getDescription());
            ImGui.textWrapped("ID: " + metadata.getId());
            ImGui.textWrapped("Version: " + metadata.getVersion());
            ImGui.textWrapped("License: " + metadata.getLicense());

            renderListWithTitle("Authors", metadata.getAuthors());
            renderListWithTitle("Contacts", metadata.getContact());

            IControlsWidget controls = PluginManager.getPluginControlsRegistry().get(metadata.getId());
            if (controls != null) {
                ImGui.newLine();
                controls.render();
            }
        }

        ImGui.endChild();

        ImGui.columns(1);
    }

    /**
     * Renders a list of items in ImGui with a given title.
     * Each item in the list is separated by a comma, and a semicolon is appended to the end.
     * @param title The title for the list to be rendered. This title is displayed before the list.
     * @param items The list of strings to be rendered. If the list is null or empty, nothing is rendered.
     */
    private void renderListWithTitle(String title, List<String> items) {
        if (items == null || items.isEmpty()) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            stringBuilder.append(items.get(i));
            if (i < items.size() - 1) {
                stringBuilder.append(", ");
            } else {
                stringBuilder.append(";");
            }
        }

        ImGui.textWrapped(title + ": " + stringBuilder);
    }
}
