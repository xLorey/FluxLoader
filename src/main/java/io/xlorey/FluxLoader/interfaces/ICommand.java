package io.xlorey.FluxLoader.interfaces;

import zombie.core.raknet.UdpConnection;

/**
 * Interface for implementing a new command.
 * Implementing classes must use the @CommandName annotation to specify the command name.
 * They may also use the @UserAccessLevel annotation to specify the required access level for the command.
 * The @CommandChatReturn annotation can be used to provide text that will be displayed in chat when the command is invoked.
 * The @CommandExecutionScope annotation should be used to define where the command is available (e.g., in chat, in console, or both).
 */
public interface ICommand {
    /**
     * Performing a chat command action
     * @param playerConnection player connection, if called from the console, the connection will return as null
     * @param args arguments of the received command
     */
    void onInvoke(UdpConnection playerConnection, String[] args);
}
