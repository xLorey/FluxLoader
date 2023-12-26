# Console/server commands

Flux Loader allows you to create custom commands in a similar way to how it is implemented in the game. They are implemented exclusively on the server side.

## Commands creation

To create a command, you need to create a separate class, in our case `Example`, and implement the `ICommand` interface:

```java
/**
 * An annotation that includes the name of the command (without slashes or other prefixes)
 */
@CommandName(command = "Example")
/**
 * An annotation that indicates the required access level for a user.
 * Users with a lower access level than specified will not have access to the functionality.
 */
@CommandAccessLevel(accessLevel = AccessLevel.ADMIN)
/**
 * An annotation to specify the scope where a command can be executed.
 */
@CommandExecutionScope(scope = CommandScope.BOTH)
/**
 * An annotation that provides the text to be output to chat when the command is used
 */
@CommandChatReturn(text = "The command was executed")
/**
 * Implementing a new server command
 */
public class ExampleCommands implements ICommand {
    /**
     * Executing a command
     * @param udpConnection connection of the object that called the command. If the server - will be equal to null
     * @param strings passed arguments
     */
    @Override
    public void onInvoke(UdpConnection udpConnection, String[] strings) {

    }
}
```
`CommandName` is the name of the command that will be used in the chat/console

`CommandAccessLevel` - required access level of the caller, selection from enum `AccessLevel`

`CommandExecutionScope` - the place where the command should be executed server console/chat, selection from the `CommandScope` enum

`CommandChatReturn` - the text that the command will display to the calling party in the chat upon successful execution, can be left empty.

All these annotations are integral and without them an exception will be thrown when registering the command. To register a command, you need to sign it at the entry point:
```java
/**
 * Implementing a server plugin
 */
public class ServerPlugin extends Plugin {
    /**
     * Plugin entry point. Called when a plugin is loaded via FluxLoader.
     */
    @Override
    public void onInitialize() {
        try {
            CommandsManager.addCommand(new ExampleCommands());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```

It is imperative to wrap it in a try-catch, since if the command already exists (the name itself) or what is missing, an exception will be thrown. The command is called using `/` if this is a server chat, and without it if this is a console.