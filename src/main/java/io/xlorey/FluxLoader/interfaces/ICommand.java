package io.xlorey.FluxLoader.interfaces;

import zombie.core.raknet.UdpConnection;

/**
 * Interface for implementing a new command
 */
public interface ICommand {
    /**
     * A flag that indicates whether the command should be executed in the chat when entered by the player
     * @return true - yes, false - no
     */
    boolean isAllowChatExecute();

    /**
     * A flag that indicates whether the command should be executed in the console when entered by the player
     * @return true - yes, false - no
     */
    boolean isAllowConsoleExecute();
    /**
     * Getting the command name
     * @return command name without slash
     */
    String getCommandName();

    /**
     * Receiving text for output to when calling a command
     * @return text that will be displayed in chat when calling the command
     */
    String getAfterInvokeText();

    /**
     * Performing a console command action
     * @param args arguments of the received command
     */
    void onInvokeConsoleCommand(String[] args);

    /**
     * Performing a chat command action
     * @param playerConnection player connection
     * @param args arguments of the received command
     */
    void onInvokeChatCommand(UdpConnection playerConnection, String[] args);
}
