package io.xlorey.FluxLoader.client.ui.pluginMenu;

import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import io.xlorey.FluxLoader.client.ui.ScreenWidget;
import io.xlorey.FluxLoader.plugin.Metadata;
import io.xlorey.FluxLoader.plugin.Plugin;
import io.xlorey.FluxLoader.shared.PluginManager;
import io.xlorey.FluxLoader.utils.Constants;
import io.xlorey.FluxLoader.utils.Logger;
import zombie.GameWindow;
import zombie.core.textures.Texture;
import zombie.gameStates.MainScreenState;

import java.util.HashMap;
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
        HashMap<String, Plugin> clientPlugins = PluginManager.getLoadedClientPlugins();
        ImGui.setNextWindowSize(500, 400);

        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 8);
        ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 10, 10);

        ImGui.pushStyleColor(ImGuiCol.WindowBg, 12, 12, 12, 255);
        ImGui.pushStyleColor(ImGuiCol.TitleBg, 30, 30, 30, 255);
        ImGui.pushStyleColor(ImGuiCol.TitleBgActive, 30, 30, 30, 255);
        ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0, 0, 0, 0);
        ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0, 0, 0, 0);

        ImGui.begin(Constants.FLUX_NAME + " - Plugins", isOpened, ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse);
            if(clientPlugins.isEmpty()) {
                String text = "No client plugins found";
                float windowWidth = ImGui.getWindowWidth();
                float windowHeight = ImGui.getWindowHeight();
                float textWidth = ImGui.calcTextSize(text).x;
                float textHeight = ImGui.calcTextSize(text).y;

                ImGui.setCursorPosX((windowWidth - textWidth) * 0.5f);
                ImGui.setCursorPosY((windowHeight - textHeight) * 0.5f);
                ImGui.text(text);
            } else {
                renderPluginsInfo(clientPlugins);
            }
        ImGui.end();

        ImGui.popStyleColor(5);
        ImGui.popStyleVar(2);
    }

    /**
     * Rendering information about plugins
     * @param clientPlugins loaded client plugins
     */
    private void renderPluginsInfo(HashMap<String, Plugin> clientPlugins) {
        HashSet<String> displayedMetadata = new HashSet<>();

        for (Map.Entry<String, Plugin> entry : clientPlugins.entrySet()) {
            Plugin plugin = entry.getValue();
            Metadata metadata = plugin.getMetadata();
            String metadataKey = metadata.getId() + ":" + metadata.getVersion();
            if (!displayedMetadata.contains(metadataKey)) {
                Texture icon = PluginManager.getPluginIconRegistry().get(metadata.getId());
                if (icon != null) {
                    ImGui.image(icon.getID(), 64, 64);
                    ImGui.sameLine();
                }

                ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 0, 25);
                ImGui.pushStyleColor(ImGuiCol.HeaderActive, 25, 25, 25, 255);
                ImGui.pushStyleColor(ImGuiCol.HeaderHovered, 40, 40, 40, 255);
                ImGui.pushStyleColor(ImGuiCol.Header, 30, 30, 30, 255);

                if (ImGui.collapsingHeader(metadata.getName())) {
                    ImGui.text("Plugin description: " + metadata.getDescription());
                    ImGui.text("ID: " + metadata.getId());
                    ImGui.text("Version: " + metadata.getVersion());
                    ImGui.text("License: " + metadata.getLicense());

                    renderListWithTitle("Authors", metadata.getAuthors());
                    renderListWithTitle("Contacts", metadata.getContact());
                }

                ImGui.popStyleVar();
                ImGui.popStyleColor(3);

                displayedMetadata.add(metadataKey);
            }
        }
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

        ImGui.text(title + ": " + stringBuilder);
    }
}
