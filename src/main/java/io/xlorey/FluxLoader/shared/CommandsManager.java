package io.xlorey.FluxLoader.shared;

import io.xlorey.FluxLoader.interfaces.ICommand;
import io.xlorey.FluxLoader.utils.Logger;
import lombok.experimental.UtilityClass;
import zombie.core.raknet.UdpConnection;

import java.util.Arrays;
import java.util.HashMap;

/**
 * A set of tools for handling custom commands
 */
@UtilityClass
public class CommandsManager {
    /**
     * Repository of all custom commands
     */
    private static final HashMap<String, ICommand> commandsMap = new HashMap<>();

    /**
     * Checking the command name for emptiness and treating extra characters ('/' and '!') as prefixes
     * @param command command class instance
     * @return processed and verified command name as string
     * @throws NullPointerException in case the command name is empty
     */
    private static String validateCommandName(ICommand command) throws NullPointerException {
        String commandName = command.getCommandName();
        if (commandName.isEmpty()) {
            throw new NullPointerException("There is no name specified for the custom command: " + command.getClass());
        }

        if (commandName.startsWith("!") || commandName.startsWith("/")) {
            commandName = commandName.substring(1);
        }
        return commandName;
    }

    /**
     * Adding a command to the repository
     * @param command chat command instance
     * @throws Exception in case an attempt is made to re-register a team (duplicate names)
     */
    public static void addCustomCommand(ICommand command) throws Exception {
        String commandName = validateCommandName(command).toLowerCase();

        if (commandsMap.containsKey(commandName)) {
            throw new Exception(String.format("The '%s' command is already registered in the system!", commandName));
        }
        Logger.print(String.format("Added new custom command: '%s'", commandName), CommandsManager.class.getName());

        commandsMap.put(commandName, command);
    }

    /**
     * Processing custom console commands
     * @param chatCommand command entered by the player
     * @return output text to chat when calling a command or null if there is no such command
     */
    public static String handleCustomConsoleCommand(String chatCommand){
        String[] commandArgs = parseAndValidateCommand(chatCommand);
        if(commandArgs == null) {
            return null;
        }

        ICommand command = commandsMap.get(commandArgs[0]);
        if(command == null || !command.isAllowConsoleExecute()) {
            return null;
        }

        String[] commandArgsToInvoke = Arrays.copyOfRange(commandArgs, 1, commandArgs.length);

        command.onInvokeConsoleCommand(commandArgsToInvoke);
        return command.getAfterInvokeText();
    }

    /**
     * Processing custom chat commands
     * @param playerConnection player connection
     * @param chatCommand command entered by the player
     * @return output text to chat when calling a command or null if there is no such command
     */
    public static String handleCustomChatCommand(UdpConnection playerConnection, String chatCommand){
        String[] commandArgs = parseAndValidateCommand(chatCommand);
        if(commandArgs == null) {
            return null;
        }

        ICommand command = commandsMap.get(commandArgs[0]);
        if(command == null || !command.isAllowChatExecute()) {
            return null;
        }

        String[] commandArgsToInvoke = Arrays.copyOfRange(commandArgs, 1, commandArgs.length);

        command.onInvokeChatCommand(playerConnection, commandArgsToInvoke);
        return command.getAfterInvokeText();
    }

    /**
     * Parses the given chat command string and validates its structure.
     * Splits the chat command into arguments and removes any command prefixes
     * like '!' or '/' from the first argument, which is typically the command name.
     *
     * @param chatCommand The chat command string to be parsed and validated.
     * @return An array of strings, where the first element is the command name
     *         (without prefixes) and the remaining elements are the arguments.
     *         Returns null if the command is invalid, empty, or cannot be parsed.
     */
    private static String[] parseAndValidateCommand(String chatCommand) {
        if(chatCommand == null || chatCommand.isEmpty()) {
            return null;
        }

        String[] commandArgs = chatCommand.split("\\s+");
        if (commandArgs.length == 0 || commandArgs[0].isEmpty()) {
            return null;
        }

        String commandName = commandArgs[0];
        if (commandName.startsWith("!") || commandName.startsWith("/")) {
            commandName = commandName.substring(1);
        }

        if(commandName.isEmpty()) {
            return null;
        }

        commandArgs[0] = commandName.toLowerCase();

        return commandArgs;
    }
}
