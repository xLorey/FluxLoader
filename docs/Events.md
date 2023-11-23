# Flux Loader Events

## onServerInitialize
Called when the server has successfully initialized, before the main loop
```java
@SubscribeEvent(eventName="onServerInitialize")
public void onServerInitializeHandler(){
    
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
