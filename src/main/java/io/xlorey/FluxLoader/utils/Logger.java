package io.xlorey.fluxloader.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: A set of tools to simplify logging
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@UtilityClass
public class Logger {
    /**
     * Outputting a message to the console installer
     * @param text message
     */
    public static void print(String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String time = LocalDateTime.now().format(formatter);
        System.out.println(String.format("<%s> | %s", time, text));
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
