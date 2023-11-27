# Custom event
Here are all the custom events that are implemented in FluxLoader. Content:

[Client Events](#client-events)

- [onClientWindowInit](#onClientWindowInit)

[Server Events](#server-events)

- [onChatServerMessage](#onChatServerMessage)
- [onServerInitialize](#onServerInitialize)
- [onServerShutdown](#onServerShutdown)
- [onPlayerConnect](#onPlayerConnect)
- [onPlayerDisconnect](#onPlayerDisconnect)
- [onPlayerKick](#onplayerkick)
- [onPlayerBan](#onplayerban)

# Client Events

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
