package io.xlorey.fluxloader.enums;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 08.02.2024
 * Description: Enumerates the scopes where a command can be executed.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public enum CommandScope {
    /**
     * Indicates that the command is available for execution only in the chat.
     */
    CHAT,

    /**
     * Indicates that the command is available for execution only in the console.
     */
    CONSOLE,

    /**
     * Indicates that the command is available for execution in both chat and console.
     */
    BOTH;
}