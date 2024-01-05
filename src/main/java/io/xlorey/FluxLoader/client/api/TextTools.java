package io.xlorey.FluxLoader.client.api;

import zombie.ui.TextManager;
import zombie.ui.UIFont;

/**
 * A set of tools for manipulating text and rendering it
 */
public class TextTools {
    /**
     * Drawing colored text at specified coordinates
     * @param text the text to be drawn. Must not be null
     * @param font text font corresponding to enum UIFont
     * @param posX horizontal text position (X-coordinate), measured in pixels
     * @param posY vertical position of the text (Y-coordinate), measured in pixels
     * @param r red component of the text color, value from 0.0 to 1.0
     * @param g green component of the text color, value from 0.0 to 1.0
     * @param b blue component of the text color, value from 0.0 to 1.0
     * @param a alpha component of the text color, value from 0.0 to 1.0
     */
    public static void drawText(String text, UIFont font, float posX, float posY, float r, float g, float b, float a) {
        TextManager.instance.DrawString(font, posX, posY, text, r, g, b, a);
    }

    /**
     * Draws text with a shadow.
     * @param text The text to draw.
     * @param font The font for the text.
     * @param x X coordinate of the text.
     * @param y Y coordinate of the text.
     * @param r The red component of the text color.
     * @param g The green component of the text color.
     * @param b The blue component of the text color.
     * @param a The alpha channel (transparency) of the text.
     * @param shadowThickness The thickness of the shadow.
     */
    public static void drawTextWithShadow(String text, UIFont font, float x, float y, float r, float g, float b, float a, float shadowThickness) {
        drawText(text, font, x + shadowThickness, y + shadowThickness, 0.0f, 0.0f, 0.0f, a);
        drawText(text, font, x, y, r, g, b, a);
    }

    /**
     * Draws text centered without a shadow.
     * @param text The text to draw.
     * @param font The font for the text.
     * @param x X coordinate of the text center.
     * @param y Y coordinate of the text center.
     * @param r The red component of the text color.
     * @param g The green component of the text color.
     * @param b The blue component of the text color.
     * @param a The alpha channel (transparency) of the text.
     */
    public static void drawTextCenter(String text, UIFont font, float x, float y, float r, float g, float b, float a) {
        TextManager.instance.DrawStringCentre(font, x, y, text, r, g, b, a);
    }

    /**
     * Draws text centered with a shadow and the specified thickness.
     * @param text The text to draw.
     * @param font The font for the text.
     * @param x X coordinate of the text center.
     * @param y Y coordinate of the text center.
     * @param r The red component of the text color.
     * @param g The green component of the text color.
     * @param b The blue component of the text color.
     * @param a The alpha channel (transparency) of the text.
     * @param thickness The thickness of the text shadow.
     */
    public static void drawTextCenterWithShadow(String text, UIFont font, float x, float y, float r, float g, float b, float a, float thickness) {
        drawTextCenter(text, font, x + thickness, y + thickness, 0.0f, 0.0f, 0.0f, a);
        drawTextCenter(text, font, x, y, r, g, b, a);
    }

    /**
     * Draws text on the right without a shadow.
     * @param text The text to draw.
     * @param font The font for the text.
     * @param x X coordinate of the text center.
     * @param y Y coordinate of the text center.
     * @param r The red component of the text color.
     * @param g The green component of the text color.
     * @param b The blue component of the text color.
     * @param a The alpha channel (transparency) of the text.
     */
    public static void drawTextRight(String text, UIFont font, float x, float y, float r, float g, float b, float a) {
        TextManager.instance.DrawStringRight(font, x, y, text, r, g, b, a);
    }

    /**
     * Draws text on the right with a shadow.
     * @param text The text to draw.
     * @param font The font for the text.
     * @param x X coordinate of the right edge of the text.
     * @param y Y coordinate of the right edge of the text.
     * @param r The red component of the text color.
     * @param g The green component of the text color.
     * @param b The blue component of the text color.
     * @param a The alpha channel (transparency) of the text.
     * @param shadowThickness The thickness of the shadow.
     */
    public static void drawTextRightWithShadow(String text, UIFont font, float x, float y, float r, float g, float b, float a, float shadowThickness) {
        drawTextRight(text, font, x + shadowThickness, y + shadowThickness, 0.0f, 0.0f, 0.0f, a);
        drawTextRight(text, font, x, y, r, g, b, a);
    }

    /**
     * Getting line height from a given font
     * @param font text font corresponding to enum UIFont
     * @return line height
     */
    public static int getFontHeight(UIFont font){
        return TextManager.instance.getFontHeight(font);
    }

    /**
     * Returns the width of the given text in the specified font
     * @param text The text for the dimension
     * @param font The font used for the text
     * @return The width of the text in pixels
     */
    public static int getTextWidth(String text, UIFont font) {
        return TextManager.instance.MeasureStringX(font, text);
    }

    /**
     * Returns the height of the given text in the specified font
     * @param text The text for the dimension
     * @param font The font used for the text
     * @return The height of the text in pixels
     */
    public static int getTextHeight(String text, UIFont font) {
        return TextManager.instance.MeasureStringY(font, text);
    }
}
