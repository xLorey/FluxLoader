# FluxLoader v0.3.0
- Changed `onChatServerMessage` event arguments
- Removed `onChatWhisperMessage` event
- Fixed calling the `onServerShutdown` event
- Added unique events (the only ones) with the return of the object: annotation `SubscribeSingleEvent` and method `invokeSingleEventAndReturn`

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