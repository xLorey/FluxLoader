package io.xlorey.FluxLoader.client.fonts;

import lombok.Getter;

/**
 * The CustomFontData class represents custom font data.
 */
@Getter
public class FontData {
    /**
     *  Byte array with font data
     */
    private final byte[] fontData;
    /**
     * Font name
     */
    private final String name;
    /**
     * Font size
     */
    private final float size;

    /**
     * Constructor for creating a CustomFontData object.
     * @param name The name of the font.
     * @param size Font size.
     * @param fontData A byte array containing the font data.
     */
    public FontData(String name, float size, byte[] fontData) {
        this.name = name;
        this.size = size;
        this.fontData = fontData;
    }
}
