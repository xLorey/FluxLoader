package io.xlorey.FluxLoader.client.api;

import zombie.debug.LineDrawer;

/**
 * The RenderTools class provides static utilities for rendering various graphical elements on the screen.
 */
public class RenderTools {
    /**
     * Draws a line on the screen.
     * @param startX Start X coordinate.
     * @param startY Start Y coordinate.
     * @param endX The ending X coordinate.
     * @param endY End Y coordinate.
     * @param r The red component of the color.
     * @param g The green component of the color.
     * @param b The blue component of the color.
     * @param a Alpha channel (transparency).
     * @param thickness Line thickness.
     */
    public static void drawLine(int startX, int startY, int endX, int endY, float r, float g, float b, float a, int thickness) {
        LineDrawer.drawLine(startX, startY, endX, endY, r, g, b, a, thickness);
    }

    /**
     * Draws a circle on the screen.
     * @param posX X coordinate of the circle center.
     * @param posY Y coordinate of the circle center.
     * @param radius The radius of the circle.
     * @param segments The number of segments (sides) of the circle.
     * @param r The red component of the color.
     * @param g The green component of the color.
     * @param b The blue component of the color.
     */
    public static void drawCircle(float posX, float posY, float radius, int segments, float r, float g, float b) {
        LineDrawer.drawCircle(posX, posY, radius, segments, r, g, b);
    }

    /**
     * Draws an arc on the screen.
     * @param posX X coordinate of the arc center.
     * @param posY Y coordinate of the arc center.
     * @param thickness The thickness of the arc.
     * @param radius Radius of the arc.
     * @param startAngle The starting angle of the arc.
     * @param endAngle End angle of the arc.
     * @param segments Number of arc segments.
     * @param r The red component of the color.
     * @param g The green component of the color.
     * @param b The blue component of the color.
     * @param a Alpha channel (transparency).
     */
    public static void drawArc(float posX, float posY, float thickness, float radius, float startAngle, float endAngle, int segments, float r, float g, float b, float a) {
        LineDrawer.drawArc(posX, posY, thickness, radius, startAngle, endAngle, segments, r, g, b, a);
    }

    /**
     * Draws a rectangle on the screen.
     * @param posX X coordinate of the upper left corner of the rectangle.
     * @param posY Y coordinate of the upper left corner of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param r The red component of the color.
     * @param g The green component of the color.
     * @param b The blue component of the color.
     * @param a Alpha channel (transparency).
     * @param thicknessBorder The thickness of the rectangle's border.
     */
    public static void drawRect(float posX, float posY, float width, float height, float r, float g, float b, float a, int thicknessBorder) {
        LineDrawer.drawRect(posX, posY, width, height, r, g, b, a, thicknessBorder);
    }
}
