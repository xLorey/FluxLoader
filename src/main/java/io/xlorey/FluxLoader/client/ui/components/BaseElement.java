package io.xlorey.FluxLoader.client.ui.components;

import zombie.SoundManager;
import zombie.input.Mouse;
import zombie.ui.UIElement;
import zombie.ui.UIManager;

/**
 * Basic UI element, which is the basis for other custom UI elements
 */
public class BaseElement extends UIElement {
    /**
     * Element mouse hover state flag
     */
    private boolean isHovered = false;

    /**
     * Flag indicating whether the left mouse button (LMB) is pressed inside element
     */
    private boolean isPressed = false;

    /**
     * Action performed on click
     */
    private Runnable clickFunction;

    /**
     * The name of the sound that plays when you click on an element
     */
    private String clickSound = "UIActivateButton";

    /**
     * Constructor for creating a base element
     */
    public BaseElement() {
        super();
        createChildren();
    }

    /**
     * Checks whether the left mouse button is pressed (LMB)
     * @return {@code true} if LMB is pressed, otherwise {@code false}
     */
    public boolean isPressed() {
        return isPressed;
    }

    /**
     * Setting the sound that will be played when you click on an element
     * @param clickSound sound name, default "UIActivateButton"
     */
    public void setClickSound(String clickSound) {
        this.clickSound = clickSound;
    }

    /**
     * Calls the click processing function, if specified
     */
    public void onClick() {
        if (clickFunction == null) return;

        clickFunction.run();
    }

    /**
     * Sets the function that will be executed when clicked.
     * This method allows you to specify a function (action) that will be called upon a click event.
     * The function is passed as an object that implements the {@link Runnable} interface.
     * Once installed, this function can be called by the {@code onClick()} method.
     * @param function Functional interface {@link Runnable} defining the action to perform when clicked.
     */
    public void setClickFunction(Runnable function) {
        clickFunction = function;
    }

    /**
     * Handles the left mouse button press event
     * @param posX The horizontal X coordinate of the mouse cursor at the moment of clicking
     * @param posY The vertical Y coordinate of the mouse cursor at the moment of clicking
     * @return Returns the result of calling the underlying event implementation
     */
    @Override
    public Boolean onMouseDown(double posX, double posY) {
        isPressed = true;
        bringToTop();
        return true;
    }

    @Override
    public void onMouseUpOutside(double posX, double posY) {
        isPressed = false;
    }

    /**
     * Handles the left mouse button release event
     * @param posX The horizontal X coordinate of the mouse cursor at the moment of release
     * @param posY The vertical Y coordinate of the mouse cursor at the moment of release
     * @return Returns the result of calling the underlying event implementation
     */
    @Override
    public Boolean onMouseUp(double posX, double posY) {
        onClick();
        SoundManager.instance.playUISound(clickSound);

        isPressed = false;

        return true;
    }

    /**
     * Updates the hover state of an element based on the current mouse cursor position
     */
    private void updateHoverState() {
        double elementX = getAbsoluteX();
        double elementY = getAbsoluteY();

        int mouseX = Mouse.getXA();
        int mouseY = Mouse.getYA();

        isHovered = mouseX >= elementX && mouseX <= elementX + width && mouseY >= elementY && mouseY <= elementY + height;
    }

    /**
     * Update element every frame
     */
    @Override
    public void update() {
        super.update();
        updateHoverState();
    }

    /**
     * Creates and configures child elements.
     * This method provides the ability to initialize and configure child elements.
     * By default, the method is empty, but it can be overridden in subclasses to add specific
     * logic for creating and configuring child elements.
     */
    public void createChildren(){}

    /**
     * Adds the current element to the screen.
     * This method registers the element with the user interface manager (UIManager),
     * which makes it visible and active on the screen.
     */
    public void addToScreen() {
        UIManager.AddUI(this);
    }

    /**
     * Removes the current item from the screen.
     * This method removes an element from the UI Manager (UIManager),
     * which makes it invisible and inactive on the screen.
     */
    public void removeFromScreen() {
        UIManager.RemoveElement(this);
    }

    /**
     * Method for pre-rendering, called every frame before main rendering
     */
    public void prerender() {

    }

    /**
     * The process of rendering an element, called every frame
     */
    @Override
    public void render() {
        prerender();
    }

    /**
     * Draws a rectangle of the specified color and transparency.
     * @param posX the horizontal X coordinate of the top left corner of the rectangle.
     * @param posY the vertical Y coordinate of the upper left corner of the rectangle.
     * @param width the width of the rectangle in pixels.
     * @param height the height of the rectangle in pixels.
     * @param r red component of the color, value from 0.0 to 1.0.
     * @param g green component of the color, value from 0.0 to 1.0.
     * @param b blue component of the color, value from 0.0 to 1.0.
     * @param a the alpha component for transparency, a value from 0.0 (fully transparent) to 1.0 (fully opaque).
     */
    public void drawRect(int posX, int posY, int width, int height, float r, float g, float b, float a) {
        DrawTextureScaledColor(null, (double) posX, (double) posY, (double) width, (double) height, (double) r, (double) g, (double) b, (double) a);
    }

    /**
     * Draws the borders of a rectangle of the specified color and transparency.
     * @param posX the horizontal X coordinate of the top left corner of the rectangle.
     * @param posY the vertical Y coordinate of the upper left corner of the rectangle.
     * @param width the width of the rectangle in pixels.
     * @param height the height of the rectangle in pixels.
     * @param r red component of the color, value from 0.0 to 1.0.
     * @param g green component of the color, value from 0.0 to 1.0.
     * @param b blue component of the color, value from 0.0 to 1.0.
     * @param a the alpha component for transparency, a value from 0.0 (fully transparent) to 1.0 (fully opaque).
     */
    public void drawRectBorder(int posX, int posY, int width, int height, float r, float g, float b, float a) {
        DrawTextureScaledColor(null, (double) posX, (double) posY, (double) 1, (double) height, (double) r, (double) g, (double) b, (double) a);
        DrawTextureScaledColor(null, (double) posX + 1, (double) posY, (double) width - 2, 1.0, (double) r, (double) g, (double) b, (double) a);
        DrawTextureScaledColor(null, (double) posX + width - 1, (double) posY, (double) 1, (double) height, (double) r, (double) g, (double) b, (double) a);
        DrawTextureScaledColor(null, (double) posX + 1, (double) posY + height - 1, (double) width - 2, 1.0, (double) r, (double) g, (double) b, (double) a);
    }

    /**
     * Checks if the mouse cursor is over an element.
     * @return {@code true} if the element is hovered, otherwise {@code false}.
     */
    public boolean isHovered() {
        return isHovered;
    }
}
