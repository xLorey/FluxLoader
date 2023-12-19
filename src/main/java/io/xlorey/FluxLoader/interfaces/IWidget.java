package io.xlorey.FluxLoader.interfaces;

/**
 * Basic template for custom interface elements.
 * This interface defines essential methods for managing and rendering custom UI widgets.
 */
public interface IWidget {
    /**
     * Update the widget state.
     * This method is called before the main rendering of the widget
     */
    void update();
    /**
     * Render the widget.
     * This method should contain the logic for drawing the widget on the screen.
     */
    void render();

    /**
     * Check if the widget is currently visible on the screen.
     * This method should return true if the widget is visible and false otherwise.
     * @return boolean - true if the widget is visible, false if it is not.
     */
    boolean isVisible();

    /**
     * Set the visibility of the widget.
     * @param visible boolean - the desired visibility state of the widget.
     *                True to make the widget visible, false to hide it.
     */
    void setVisible(boolean visible);
}
