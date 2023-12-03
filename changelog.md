# FluxLoader v0.5.0
- Added a tool for sending a message from the server to a specific user
- Added `onPlayerFullyConnected` event
- Added logging of custom command calls
- Added packet blocking mechanism to `IncomingPacket.blockPacket()`
- Added date and time output via `Logger.print()`
- Fixed error output when calling events
# FluxLoader v0.4.1
- Added new annotations (CommandName, CommandAccessLevel, CommandChatReturn, CommandExecutionScope) have been created to indicate various aspects of commands.
- Added AccessLevel and CommandScope enumerations to represent access levels and command scopes, respectively.
- The logic for creating commands has been fixed, now through annotations (closer to the game logic of commands)
- Fixed logging of incorrect arguments when calling an event
- Removed duplicate events `onPlayerDeath` and `onZombieDeath`
- Updated Wiki

# FluxLoader v0.4.0
- Fixed processing of primitive arguments during injection
- Added a tool for injecting event calls using local fields
- Added `onAddIncoming` event
- Added `onPlayerDeath` event
- Added `onZombieDeath` event
- Added `onSendChatCommand` event
- Added `onSendConsoleCommand` event
- Added the ability to implement custom chat commands via `ICommand`

# FluxLoader v0.3.0
- Changed `onChatServerMessage` event arguments
- Removed `onChatWhisperMessage` event
- Fixed calling the `onServerShutdown` event
- Implemented loading plugins in the order of the queue
- Added unique events (the only ones) with the return of the object: annotation `SubscribeSingleEvent` and method `invokeSingleEventAndReturn`
- Added new custom events
- Added API tools for player management and manipulation for the server
- Added API tools for server management

# FluxLoader v0.2.1
- The system for subscribing classes as listeners has been fixed. Now it is not the class itself that is signed, but its instance.
- New event added `onServerShutdown`

# FluxLoader v0.2.0
- Redesigned plugin loading system
- Changed plugin creation template
- Added metadata logic for the 

# FluxLoader v0.1.0
- Beta version of the implementation of the basic functionality of the plugin loader.
- An event system has been created.
- The first plugin loader prototype has been created.
- Installer and uninstaller implemented.