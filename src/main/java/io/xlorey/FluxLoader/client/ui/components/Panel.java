package io.xlorey.FluxLoader.client.ui.components;

import zombie.core.Color;

/**
 * Basic panel element
 */
public class Panel extends BaseElement {
    /**
     * Panel background color
     */
    public final Color backgroundColor;

    /**
     * Creates a panel with specified location and color parameters
     * @param posX horizontal X coordinate of the top left corner of the panel.
     * @param posY the vertical Y coordinate of the top left corner of the panel.
     * @param width the width of the panel in pixels.
     * @param height the height of the panel in pixels.
     * @param r red component of the color, value from 0.0 to 1.0.
     * @param g green component of the color, value from 0.0 to 1.0.
     * @param b blue component of the color, value from 0.0 to 1.0.
     * @param a the alpha component for transparency, a value from 0.0 (fully transparent) to 1.0 (fully opaque).
     */
    public Panel(int posX, int posY, int width, int height, float r, float g, float b, float a) {
        this.backgroundColor = new Color(r, g, b, a);
        this.x = posX;
        this.y = posY;
        this.width = width;
        this.height = height;
    }

    /**
     * Drawing the panel
     */
    @Override
    public void render() {
        super.render();

        drawRect(0, 0, (int) width, (int) height, backgroundColor.r, backgroundColor.r, backgroundColor.r, backgroundColor.r);
    }
}
