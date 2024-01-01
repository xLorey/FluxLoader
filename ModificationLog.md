# Modification log

Description of all modifications made to game files

## zombie/commands/serverCommands/BanSteamIDCommand
A call to the `onPlayerBan` event has been added to the `Command` method before the player is finally disconnected from the server

```java
/*
Other code
*/

if (var5.steamID == var2) {
    GameServer.kick(var5, "UI_Policy_Ban", (String)null);

    // Start of modification
    IsoPlayer player = PlayerUtils.getPlayerByUdpConnection(var5);

    if (player != null) {
        EventManager.invokeEvent("onPlayerBan", player, "");
    }

    // End of modification
        
    var5.forceDisconnect("command-ban-sid");
    break;
}

/*
Other code
*/
```

## zombie/commands/serverCommands/BanUserCommand
A call to the `onPlayerBan` event has been added to the `Command` method before the player is finally disconnected from the server

```java
/*
Other code
*/

if (var6.username.equals(var1)) {
    GameServer.kick(var5, "UI_Policy_Ban", (String)null);

    // Start of modification
    IsoPlayer player = PlayerUtils.getPlayerByUdpConnection(var6);

    if (player != null) {
        EventManager.invokeEvent("onPlayerBan", player, this.reason);
    }

    // End of modification

    var6.forceDisconnect("command-ban-ip");
    break;
}

/*
Other code
*/
```

## zombie/commands/serverCommands/KickUserCommand
A call to the `onPlayerKick` event has been added to the `Command` method before the player is finally disconnected from the server

```java
/*
Other code
*/

if (var1.equals(var4.usernames[var5])) {
    GameServer.kick(var5, "UI_Policy_Ban", (String)null);

    // Start of modification
    IsoPlayer player = PlayerUtils.getPlayerByUdpConnection(var4);
    
    if (player != null) {
        EventManager.invokeEvent("onPlayerKick", player, this.reason);
    }

    // End of modification

    var4.forceDisconnect("command-kick");
    GameServer.addDisconnect(var4);
    break;
}

/*
Other code
*/
```

## zombie/commands/serverCommands/QuitCommand
A call to the `onServerShutdown` event has been added to the `Command` method before starting server shutdown processes

```java
protected String Command() {
    // Start of modification
    EventManager.invokeEvent("onServerShutdown");
    // End of modification
        
    /*
    Other code
    */
}
```

## zombie/core/SpriteRenderer
Added a call to the `EventsHandler.onDrawWithRenderThreadHandler()` handler from the client package at the end of the `postRender` method. Necessary for ImGui to work, since the call occurs from the `RenderThread` thread where the `OpenGL` context is defined

```java
public void postRender() {
    /*
    Other code
    */
        
    // Start of modification
    EventsHandler.onDrawWithRenderThreadHandler();
    // End of modification
}
```

## zombie/Lua/LuaEventManager

In each overload of the `triggerEvent` method, a call to the corresponding event passed as the first parameter `var1` is added to its beginning; all other parameters are passed as event arguments

```java
public static void triggerEvent(String var0, <...varsX...>) {
    // Start of modification
    EventManager.invokeEvent(var0, <...varsX...>);
    // End of modification
        
    /*
    Other code
    */
}
```

## zombie/network/chat/ChatServer

The `onChatServerMessage` event is inserted into the `processMessageFromPlayerPacket` method after declaring and processing messages, passing the processed data as an argument

```java
public void processMessageFromPlayerPacket(ByteBuffer var1) {
    /*
    Other code
    */
    ChatBase var4 = (ChatBase)chats.get(var2);
    ChatMessage var5 = var4.unpackMessage(var1);
    
    // Start of modification    
    EventManager.invokeEvent("onChatServerMessage", var4, var5);
    // End of modification
        
    /*
    Other code
    */
}
```

## zombie/network/GameServer

In the `main` method after initializing `LoggerManager`, initialization of the FluxLoader Server Core has been added.
Added `onServerShutdown` event before each `System.exit` call
Added `onServerInitialize` event before the main `while(!bDone)` loop

```java
public static void main(String[] var0) throws Exception {
    /*
    Other code
    */

    DebugLog.init();
    LoggerManager.init();
    DebugLog.log("cachedir set to \"" + ZomboidFileSystem.instance.getCacheDir() + "\"");
    
    // Start of modification   
    io.xlorey.FluxLoader.server.core.Core.init(var0);
    // End of modification
        
    if (bCoop) {
        /*
        Other code
        */
    }
    
    /*
    Other code
    */
        
    // Start of modification  
    EventManager.invokeEvent("onServerInitialize");
    // End of modification
        
    while(!bDone) {
        /*
        Other code
        */
    }

    // Start of modification  
    EventManager.invokeEvent("onServerShutdown");
    // End of modification
        
    System.exit(<EXIT_CODE>);
}
```

At the very beginning of the `addIncoming` method, the `onAddIncoming` event has been added with the passing of all method arguments. This allows you to intercept all incoming packets from the client

```java
public static void addIncoming(short var0, ByteBuffer var1, UdpConnection var2){
    // Start of modification  
    EventManager.invokeEvent("onAddIncoming",var0,var1,var2);
    // End of modification
        
    /*
    Other code
    */
}
```
In the `handleServerCommand` method, a call to the `onSendConsoleCommand` event has been added with transmission of the received command
Added a check for the presence of a corresponding custom command. If it exists, the execution text is returned via `return`

```java
private static String handleServerCommand(String var0, UdpConnection var1){
    if(var0==null){
        return null;
    }else{
        // Start of modification  
        EventManager.invokeEvent("onSendConsoleCommand",var0);
        String customResult=CommandsManager.handleCustomCommand(var1,var0);
        if (customResult != null) return customResult;
        // End of modification
        
        /*
        Other code
        */
    }
    /*
    Other code
    */
}
```

A call to the `onSendChatCommand` event has been added to the `receiveReceiveCommand` method, which intercepts all entered commands
Added a check for the presence of custom commands `CommandsManager.handleCustomCommand`, if exists, all standard commands are ignored.
Added a block on displaying an empty string in chat if, when executing a command, it returns an empty string

```java
static void receiveReceiveCommand(ByteBuffer var0, UdpConnection var1, short var2) {
    String var3 = GameWindow.ReadString(var0);

    // Start of modification 
    EventManager.invokeEvent("onSendChatCommand", var1, var3);

    String var4 = null;
    var4 = CommandsManager.handleCustomCommand(var1, var3);

    if (var4 == null) {
    var4 = handleClientCommand(var3.substring(1), var1);
    }
    // End of modification
        
    /*
    Other code
    */

    // Start of modification 
    if (var4.isEmpty()) return;
    // End of modification
        
    /*
    Other code
    */
}
```

Added `onPlayerConnect` and `onPlayerFullyConnected` events to the beginning and end of the player connection method `receivePlayerConnect`, respectively

```java
private static void receivePlayerConnect(ByteBuffer var0, UdpConnection var1, String var2){
    // Start of modification 
    EventManager.invokeEvent("onPlayerConnect",var0,var1,var2);
    // End of modification
        
    /*
    Other code
    */

    // Start of modification 
    EventManager.invokeEvent("onPlayerFullyConnected", var0, var1, var2);
    // End of modification
}
```

Added `onPlayerDisconnect` event to the beginning of the `disconnectPlayer` method to disconnect a player

```java
public static void disconnectPlayer(IsoPlayer var0, UdpConnection var1){
    // Start of modification 
    EventManager.invokeEvent("onPlayerDisconnect",var0,var1);
    // End of modification
        
    /*
    Other code
    */
}
```

## zombie/ui/UIManager

Added to the `update` method to prevent mouse events from being processed on standard UI elements if the mouse is captured

```java
public static void update() {
    /*
    Other code
    */
        
    // Start of modification 
    if(WidgetManager.isMouseCapture()) return;
    // End of modification

    if (Mouse.isLeftPressed()) {
        /*
        Other code
        */
    }
    /*
    Other code
    */
}
```

## zombie/GameWindow

A call to initialize the FluxLoader client core has been added to the `init` method at the beginning
At the end, a call to initialize the user state was added to show the project logo and a call to the `onClientWindowInit` event

```java
private static void init() throws Exception {
        // Start of modification 
        io.xlorey.FluxLoader.client.core.Core.init();
        // End of modification
        
        /*
        Other code
        */
        
        LuaEventManager.triggerEvent("OnLoadSoundBanks");
        
        // Start of modification 
        StateManager.init();
        EventManager.invokeEvent("onClientWindowInit");
        // End of modification
}
```

The name of the window title has been changed in the `InitDisplay` method

```java
public static void InitDisplay() throws IOException, LWJGLException{
    // Start of modification 
    String newTitle=String.format("Project Zomboid by %s (v%s)",Constants.FLUX_NAME,Constants.FLUX_VERSION);
    // End of modification
    
    /*
    Other code
    */
}
```