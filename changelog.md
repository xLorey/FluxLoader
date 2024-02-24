# FluxLoader v0.9.0
- Added methods for getting and setting lists of specific types for plugin configs
- Added new methods for configuration files
- Added empty character constant and methods for colorizing chat messages
- Changed the logic for saving and loading the standard configuration file
- Fixed API provisions for chat

# FluxLoader v0.8.3
- Fixed `onPlayerDisconnect` event

# FluxLoader v0.8.2
- Rolling back classLoader's close fixes (works 50%, sometimes has problems)

# FluxLoader v0.8.1
- Fixed Patchers (extra characters removed)
- Fixed plugin loading (added resource release after loading)

# FluxLoader v0.8.0
- Added missing `Configuration` data types
- Added a method to the `Plugin` class to get the config folder inside the folder with all plugins (`./plugins/{pluginName}/...`)
- Added item IDs to `Wiki`
- Added plugin metadata structure version tracking
- Added a centralized registry for all plugin data
- Added implementation of a service manager for communication between plugins
- Added the ability to set the priority for the event handler
- Added display of administrator nickname in player ban and kick events
- Changed basic widget templates
- The event and patch system has been rewritten
- Refactored plugin loader
- The plugin class loader for services has been changed

# FluxLoader v0.7.1
- Fixed creation of plugins folder

# FluxLoader v0.7.0
- Added a log of all changes to game files
- Added additional JAR manifest attributes
- Added a new feature to check for the presence of the Modified annotation in methods of class files, enhancing security and integrity checks.
- The logic for creating logs has been changed, now you can create custom loggers (for individual files)
- Changed the logic for modifying game files
- Moved the call to the `onServerShutdown` event to the `quit` command
- Removed packet blocking logic due to its inoperability
- Fixed tools for kicking and banning players
- Fixed critical plugin loading errors

# FluxLoader v0.6.2
- Fixed a critical error loading plugins on the server side
- Fixed error output when calling events
- Fixed class loading issue for static utility classes in plugins
- Removed the extra log when blocking the package

# FluxLoader v0.6.1
- Fixed missing `onServerShutdown` events in `main` method
- Fixed missing `onPlayerBan` events when using commands
- Fixed missing `onPlayerKick` events when using commands
- Fixed loading of plugins for the co-op server
- Fixed output of plugin execute/terminate logs

# FluxLoader v0.6.0
- Added `onExecute` and `onTerminate` events for plugins. On the server: `onExecute` is called at startup and `onTerminate` - at shutdown. On the client: `onExecute` and `onTerminate` called when entering a single player game (if enabled in the main menu).
- Added a registry of all loaded plugins
- Added client/server events handler
- Added patcher for nested classes
- Added tools for manipulating text on the client
- Added/changed custom UI logic, now based on ImGui
- Added a button to open/close the menu and the plugin management menu itself
- Added basic templates for UI widgets
- Added configuration system for the plugin
- Added the ability to create plugin widget controls in the menu
- Added a method for capturing mouse events in widgets
- Fix `Zip Slip` vulnerability in `JarTools` extraction method (#9)
- Fixed (separated) logic for loading plugins from the server and client sides

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
- Added metadata logic for the plugin

# FluxLoader v0.1.0
- Beta version of the implementation of the basic functionality of the plugin loader.
- An event system has been created.
- The first plugin loader prototype has been created.
- Installer and uninstaller implemented.