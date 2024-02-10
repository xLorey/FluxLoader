package io.xlorey.fluxloader.client.ui;

import imgui.ImGui;
import imgui.ImVec2;
import io.xlorey.fluxloader.client.core.WidgetManager;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 10.02.2024
 * Description: Basic template for custom interface elements.
 *              This class defines essential methods for managing and rendering custom UI widgets.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public abstract class Widget {
    /**
     * Flag indicating whether the mouse cursor is inside the widget
     */
    private boolean isWidgetHovered = false;

    /**
     * Flag indicating whether the widget is displayed
     */
    private boolean isVisible = true;

    /**
     * Update the widget state.
     * This method is called before the main rendering of the widget
     */
    public void update() {
        if (!isVisible()) {
            isWidgetHovered = false;
        }
    }

    /**
     * Rendering a custom element
     */
    public void render() {}

    /**
     * Add this widget to the screen draw queue.
     * Invoking this method will schedule the widget for rendering on the screen.
     * It should be used to register the widget in a way that it becomes visible and active.
     */
    public void addToScreen() {
        if (WidgetManager.widgetsRegistry.contains(this)) return;
        WidgetManager.widgetsRegistry.add(this);
    }

    /**
     * Remove this widget from the screen draw queue.
     * This method should unregister the widget from being drawn on the screen.
     * Use it to hide the widget or stop its active functions on the UI.
     */
    public void removeFromScreen() {
        if (!WidgetManager.widgetsRegistry.contains(this)) return;
        WidgetManager.widgetsRegistry.remove(this);
    }

    /**
     * Check if the widget is currently visible on the screen.
     * This method should return true if the widget is visible and false otherwise.
     * @return boolean - true if the widget is visible, false if it is not.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Set the visibility of the widget.
     * @param visible boolean - the desired visibility state of the widget.
     *                True to make the widget visible, false to hide it.
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    /**
     * Captures mouse focus, redirecting mouse interaction to the current element.
     * This method is used to temporarily limit the user's interaction with others user interface elements.
     */
    public void captureMouseFocus() {
        ImVec2 windowSize = ImGui.getWindowSize();
        ImVec2 windowPos = ImGui.getWindowPos();

        ImVec2 mousePos = ImGui.getMousePos();

        isWidgetHovered = mousePos.x >= windowPos.x && mousePos.y >= windowPos.y &&
                mousePos.x <= windowPos.x + windowSize.x && mousePos.y <= windowPos.y + windowSize.y;
    }

    /**
     * Checks whether the mouse cursor is within the given widget.
     * This method is useful for determining whether the mouse is over a widget,
     * which can be used to implement various interactions
     * user interface.
     * @return true if the mouse cursor is inside the widget, false otherwise.
     */
    public boolean isWidgetHovered() {
        return isWidgetHovered;
    }
}