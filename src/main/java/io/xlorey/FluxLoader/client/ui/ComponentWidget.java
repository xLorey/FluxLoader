package io.xlorey.FluxLoader.client.ui;

import io.xlorey.FluxLoader.interfaces.IWidget;

/**
 * A basic UI widget template that is a component of the main ScreenWidget
 */
public class ComponentWidget implements IWidget {
    /**
     * Flag indicating whether the widget is displayed
     */
    private boolean isVisible = true;

    /**
     * Update the widget state.
     * This method is called before the main rendering of the widget
     */
    @Override
    public void update() {

    }

    /**
     * Render the widget.
     * This method should contain the logic for drawing the widget on the screen.
     */
    @Override
    public void render() {

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
