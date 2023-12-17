package io.xlorey.FluxLoader.client.ui.pluginMenu;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import io.xlorey.FluxLoader.client.ui.ScreenWidget;
import io.xlorey.FluxLoader.utils.Constants;
import zombie.GameWindow;
import zombie.core.Core;
import zombie.gameStates.MainScreenState;

/**
 * Implementation of a button to open the plugin control panel
 */
public class MainMenuButton extends ScreenWidget {
    /**
     * Button width
     */
    private final int width = 112;

    /**
     * Button height
     */
    private final int height = 26;

    /**
     * Horizontal button position
     */
    private int posX = 0;

    /**
     * Vertical position of the button
     */
    private int posY = 0;

    /**
     * Update button state
     */
    @Override
    public void update() {
        super.update();

        setVisible(GameWindow.states.current instanceof MainScreenState);

        posX = Core.getInstance().getScreenWidth() - width - 38;
        posY = Core.getInstance().getScreenHeight() - height - 70;
    }

    /**
     * Drawing a button in the main menu
     */
    @Override
    public void render() {
        ImGui.setNextWindowPos(posX, posY);
        ImGui.setNextWindowSize(width, height);

        ImVec2 oldPadding = new ImVec2();
        ImGui.getStyle().getWindowPadding(oldPadding);

        ImGui.getStyle().setWindowPadding(2, 2);

        ImVec2 oldMinSize = new ImVec2();
        ImGui.getStyle().getWindowMinSize(oldMinSize);
        ImGui.getStyle().setWindowMinSize(5, 5);


        ImGui.pushStyleColor(ImGuiCol.Border, 0.7f, 0.7f, 0.7f, 1.0f);
        ImGui.pushStyleVar(ImGuiStyleVar.FrameBorderSize, 1.0f);

        ImGui.begin("##uniqueId", new ImBoolean(false),
                ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoTitleBar |
                        ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoScrollbar |
                        ImGuiWindowFlags.NoCollapse | ImGuiWindowFlags.NoBackground);


            ImGui.pushStyleColor(ImGuiCol.Button, 0.0f, 0.0f, 0.0f, 0.5f);
            ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.5f, 0.5f, 0.5f, 0.5f);
            ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.3f, 0.3f, 0.3f, 0.5f);
            ImGui.pushStyleColor(ImGuiCol.Text, 0.7f, 0.7f, 0.7f, 1.0f);

            if (ImGui.button(Constants.FLUX_NAME, -1, -1)) {
                PluginMenu.setOpen(!PluginMenu.isOpened());
            }

            ImGui.popStyleColor(4);

        ImGui.end();

        ImGui.popStyleVar();
        ImGui.popStyleColor();

        ImGui.getStyle().setWindowMinSize(oldMinSize.x, oldMinSize.y);
        ImGui.getStyle().setWindowPadding(oldPadding.x, oldPadding.y);
    }
}
