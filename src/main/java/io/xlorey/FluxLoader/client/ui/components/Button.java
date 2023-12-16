package io.xlorey.FluxLoader.client.ui.components;

import io.xlorey.FluxLoader.client.api.TextTools;
import zombie.core.Color;
import zombie.ui.UIFont;

/**
 * Button element base class
 */
public class Button extends Panel{
    /**
     * Button text
     */
    private String buttonText = "";

    /**
     * Font for displayed text on the button
     */
    private final UIFont textFont;

    /**
     * Display text color
     */
    public Color textColor = new Color(1f, 1f, 1f, 1f);

    /**
     * Button border color
     */
    public Color borderColor = new Color(1f, 1f, 1f, 1f);

    /**
     * Button hover color
     */
    public Color hoverColor = new Color(0.4f, 0.4f, 0.4f, 1f);

    /**
     * Creates a button with the specified location and color options
     * @param text text displayed on the button
     * @param font font of the text displayed on the button
     * @param posX the horizontal X coordinate of the button's top left corner
     * @param posY the vertical Y coordinate of the top left corner of the button
     * @param width the width of the button in pixels
     * @param height the height of the button in pixels
     * @param r red component of the color, value from 0.0 to 1.0
     * @param g green component of the color, value from 0.0 to 1.0
     * @param b blue component of the color, value from 0.0 to 1.0
     * @param a the alpha component for transparency, a value from 0.0 (fully transparent) to 1.0 (fully opaque)
     */
    public Button(String text, UIFont font, int posX, int posY, int width, int height, float r, float g, float b, float a) {
        super(posX, posY, width, height, r, g, b, a);

        this.buttonText = text;
        this.textFont = font;
    }

    /**
     * Gets the text displayed on the button
     * @return Current button text
     */
    public String getButtonText() {
        return buttonText;
    }

    /**
     * Sets the text that will be displayed on the button
     * @param buttonText New text to display on the button
     */
    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    /**
     * Rendering a button
     */
    @Override
    public void render() {
        int textWidth = TextTools.getTextWidth(buttonText, textFont);
        int textHeight = TextTools.getTextHeight(buttonText, textFont);

        Color fillColor = isHovered() ? hoverColor : backgroundColor;

        if (isPressed()) {
            fillColor = new Color(fillColor.r * 0.5f, fillColor.g * 0.5f, fillColor.b * 0.5f, fillColor.a);
        }

        drawRect(0, 0, (int) width, (int) height, fillColor.r, fillColor.g, fillColor.b, fillColor.a);
        drawRectBorder(0, 0, (int) width, (int) height, borderColor.r, borderColor.g, borderColor.b, borderColor.a);

        int textX = (int) (width - textWidth) / 2;
        int textY = (int) (height - textHeight) / 2;
        DrawText(textFont, buttonText, textX, textY, textColor.r, textColor.g, textColor.b, textColor.a);
    }

}
