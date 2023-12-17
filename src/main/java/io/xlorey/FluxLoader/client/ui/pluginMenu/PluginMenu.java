package io.xlorey.FluxLoader.client.ui.pluginMenu;

import imgui.ImGui;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImBoolean;
import io.xlorey.FluxLoader.client.ui.ScreenWidget;
import zombie.GameWindow;
import zombie.gameStates.MainScreenState;

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
     * Rendering the plugin menu
     */
    @Override
    public void render() {
        ImGui.setNextWindowSize(300, 400);
        ImGui.begin("Plugins Menu", isOpened,ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse);

        ImGui.end();
    }

    /**
     * Sets the visibility state of the plugins menu.
     * This method allows you to programmatically open or close the plugin menu.
     * @param open true to open the plugins menu; false to close.
     */
    public static void setOpen(boolean open) {
        isOpened.set(open);
    }

    /**
     * Returns the current visibility state of the plugins menu.
     * This method allows you to determine whether the plugins menu is currently open.
     * @return true if the plugins menu is open; false if closed.
     */
    public static boolean isOpened() {
        return isOpened.get();
    }
}
