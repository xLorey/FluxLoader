# Custom event
Here are all the custom events that are implemented in FluxLoader. Content:

[Client Events](#client-events)

- [onClientWindowInit](#onClientWindowInit)

[Server Events](#server-events)

- [onSendConsoleCommand](#onSendConsoleCommand)
- [onSendChatCommand](#onSendChatCommand)
- [onAddIncoming](#onaddincoming)
- [onChatServerMessage](#onChatServerMessage)
- [onServerInitialize](#onServerInitialize)
- [onServerShutdown](#onServerShutdown)
- [onPlayerFullyConnected](#onPlayerFullyConnected)
- [onPlayerConnect](#onPlayerConnect)
- [onPlayerDisconnect](#onPlayerDisconnect)
- [onPlayerKick](#onplayerkick)
- [onPlayerBan](#onplayerban)

# Client Events

## onSendConsoleCommand
Called when a player writes a command in console
```java
@SubscribeEvent(eventName="onSendConsoleCommand")
public void onSendConsoleCommandHandler(String command){

}
```

## onSendChatCommand
Called when a player writes a command in chat
```java
@SubscribeEvent(eventName="onSendChatCommand")
public void onSendChatCommandHandler(UdpConnection playerConnection, String command){

}
```

## onAddIncoming
Called whenever new data is received from the client
```java
@SubscribeEvent(eventName="onAddIncoming")
public void onAddIncomingHandler(Short opcode, ByteBuffer data, UdpConnection connection){

}
```

## onClientWindowInit
Called when the game window is running and initialized
```java
@SubscribeEvent(eventName="onClientWindowInit")
public void onClientWindowInitHandler(){

}
```

# Server Events

## onChatServerMessage
Called when a player sends a chat message
```java
@SubscribeEvent(eventName="onChatServerMessage")
public void onChatServerMessageHandler(ChatBase chatBase, ChatMessage chatMessage){

}
```

## onServerInitialize
Called when the server has successfully initialized, before the main loop
```java
@SubscribeEvent(eventName="onServerInitialize")
public void onServerInitializeHandler(String[] serverStartupArgs){
    
}
```

## onServerShutdown
Called when the server shuts down
```java
@SubscribeEvent(eventName="onServerShutdown")
public void onServerShutdownHandler(){
    
}
```

## onPlayerFullyConnected
Called when the player is fully connected to the server
```java
@SubscribeEvent(eventName="onPlayerFullyConnected")
public void onPlayerFullyConnectedHandler(ByteBuffer playerData,
        UdpConnection playerConnection,
        String username){
    
}
```

## onPlayerConnect
Called when a player connects to the server
```java
@SubscribeEvent(eventName="onPlayerConnect")
public void onPlayerConnectHandler(ByteBuffer playerData,
        UdpConnection playerConnection,
        String username){
    
}
```

## onPlayerDisconnect
Called when a player leaves the server
```java
@SubscribeEvent(eventName="onPlayerDisconnect")
public void onPlayerDisconnectHandler(IsoPlayer playerInstance, UdpConnection playerConnection){
    
}
```

## onPlayerKick
Called when kicked from the server (when using the API)
```java
@SubscribeEvent(eventName="onPlayerKick")
public void onPlayerKickHandler(IsoPlayer player, String reason){
    
}
```

## onPlayerBan
Called when banned from the server (when using the API)
```java
@SubscribeEvent(eventName="onPlayerBan")
public void onPlayerBanHandler(IsoPlayer player, String reason){
    
}
```
