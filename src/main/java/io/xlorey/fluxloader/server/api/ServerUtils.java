package io.xlorey.fluxloader.server.api;

import lombok.experimental.UtilityClass;
import zombie.core.raknet.UdpConnection;
import zombie.network.chat.ChatServer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 08.02.2024
 * Description: A set of tools for server management
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@UtilityClass
public class ServerUtils {
    /**
     * Send a message to the server chat
     * @param text Message text
     */
    public static void sendServerChatMessage(String text) {
        ChatServer.getInstance().sendMessageToServerChat(text);
    }

    /**
     * Sending a message to a specific user on behalf of the server
     * @param playerConnection player connection
     * @param text Message text
     */
    public static void sendServerChatMessage(UdpConnection playerConnection, String text) {
        ChatServer.getInstance().sendMessageToServerChat(playerConnection, text);
    }
}