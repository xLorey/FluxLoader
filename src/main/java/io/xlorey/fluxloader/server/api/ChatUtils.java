package io.xlorey.fluxloader.server.api;

import lombok.experimental.UtilityClass;
import zombie.core.raknet.UdpConnection;
import zombie.network.chat.ChatServer;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 08.02.2024
 * Description: A set of tools for chat management
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@UtilityClass
public class ChatUtils {
    /**
     * Sending a message to a general chat for all users
     * @param text Message text
     */
    public static void sendMessageToAll(String text) {
        ChatServer.getInstance().sendMessageToServerChat(text);
    }

    /**
     * Sending a chat message to a specific user
     * @param playerConnection player connection
     * @param text Message text
     */
    public static void sendMessageToPlayer(UdpConnection playerConnection, String text) {
        ChatServer.getInstance().sendMessageToServerChat(playerConnection, text);
    }
}