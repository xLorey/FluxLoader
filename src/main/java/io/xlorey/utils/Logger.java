package io.xlorey.utils;

import lombok.experimental.UtilityClass;

/**
 * A set of tools to simplify logging
 */
@UtilityClass
public class Logger {
    private static final int WIDTH = 50;
    /**
     * Outputting a message to the console/logs
     * @param text message
     */
    public static void print(String text) {
        System.out.printf("[%s]: %s%n", Constants.FLUX_NAME, text);
    }

    /**
     * Displaying basic information about the project
     */
    public static void printCredits() {
        int width = 50;
        char symbol = '#';
        printFilledLine(symbol, width);
        printCenteredText(symbol, width, "", true);
        printCenteredText(symbol, width, Constants.FLUX_NAME, true);
        printCenteredText(symbol, width, String.format("v%s",Constants.FLUX_VERSION), true);
        printCenteredText(symbol, width, "", true);
        printCenteredText(symbol, width, Constants.GITHUB_LINK, true);
        printCenteredText(symbol, width, Constants.DISCORD_LINK, true);
        printCenteredText(symbol, width, "", true);
        printFilledLine(symbol, width);

        System.out.println();
    }

    /**
     * Display a message in the middle with a given line width
     * @param symbol border symbol
     * @param width line width
     * @param text message
     * @param isBordered use boundaries
     */
    public static void printCenteredText(char symbol, int width, String text, boolean isBordered) {
        String border = isBordered ? String.valueOf(symbol) : "";
        int textWidth = text.length() + (isBordered ? 2 : 0);
        int leftPadding = (width - textWidth) / 2;
        int rightPadding = width - leftPadding - textWidth;

        String paddedString = String.format("%s%" + leftPadding + "s%s%" + rightPadding + "s%s", border, "", text, "", border);
        System.out.println(paddedString);
    }

    /**
     * Outputting the completed line to the console
     * @param symbol fill character
     * @param width fill width
     */
    public static void printFilledLine(char symbol, int width) {
        System.out.println(symbol + String.format("%" + (width - 2) + "s", "").replace(' ', symbol) + symbol);
    }
}
