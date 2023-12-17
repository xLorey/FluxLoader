package io.xlorey.FluxLoader.client.ui;

import io.xlorey.FluxLoader.client.core.WidgetManager;
import io.xlorey.FluxLoader.interfaces.IWidget;

import java.util.ArrayList;

/**
 * A basic UI widget class that can be added to display on screen independently of other widgets
 */
public class ScreenWidget implements IWidget {
    /**
     * Flag indicating whether the widget is displayed
     */
    private boolean isVisible = true;

    /**
     * Registry of child widgets, in which the state update method will be called
     */
    private final ArrayList<ComponentWidget> registryChildWidgets = new ArrayList<>();

    /**
     * Adding a widget to the registry of child components to update its state (call the update method)
     * @param widget custom component widget
     */
    public void addChild(ComponentWidget widget) {
        registryChildWidgets.add(widget);
    }

    /**
     * Removing a widget from the registry of child components so as not to update its state (call the update method)
     * @param widget custom component widget
     */
    public void removeChild(ComponentWidget widget) {
        registryChildWidgets.add(widget);
    }

    /**
     * Update the widget state.
     * This method is called before the main rendering of the widget
     */
    @Override
    public void update() {
        for (ComponentWidget widget : registryChildWidgets) {
            widget.update();
        }
    }

    /**
     * Rendering a custom element
     */
    @Override
    public void render() {

    }

    /**
     * Add this widget to the screen draw queue.
     * Invoking this method will schedule the widget for rendering on the screen.
     * It should be used to register the widget in a way that it becomes visible and active.
     */
    public void addToScreenDraw() {
        if (WidgetManager.widgetsRegistry.contains(this)) return;
        WidgetManager.widgetsRegistry.add(this);
    }

    /**
     * Remove this widget from the screen draw queue.
     * This method should unregister the widget from being drawn on the screen.
     * Use it to hide the widget or stop its active functions on the UI.
     */
    public void removeFromScreenDraw() {
        if (!WidgetManager.widgetsRegistry.contains(this)) return;
        WidgetManager.widgetsRegistry.remove(this);
    }

    /**
     * Check if the widget is currently visible on the screen.
     * This method should return true if the widget is visible and false otherwise.
     *
     * @return boolean - true if the widget is visible, false if it is not.
     */
    @Override
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
}
