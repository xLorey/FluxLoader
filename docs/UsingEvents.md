# Using events

Flux Loader has its own event subscription system. All [standard](https://pzwiki.net/wiki/Lua_Events) and [custom](./Events.md) events are available to you, and you can subscribe to each of them. However, for this you must fulfill several conditions.

## Creating a class

By default, each entry point is automatically subscribed to the `listener` role, so even in it you can implement any subscriptions and event handling. But, to maintain the appearance and cleanliness of the code, we recommend that you move the handlers into separate classes. To do this, create a file `EventHandler.java`:
```java
/**
 * User and game event handler
 */
public class EventHandler {
    /**
     * Event handler that is called every tick
     */
    @SubscribeEvent(eventName = "OnTick")
    public void onTickHandler(){

    }

    /**
     * Event handler that is called when a key is pressed
     * @param keyID key ID LWJGL3
     */
    @SubscribeEvent(eventName = "OnCustomUIKey")
    public void onCustomUIKeyHandler(int keyID){

    }
}
```
Each event handler is created by writing a `SubscribeEvent` annotation and specifying its name. **Important**, the handler method must take exactly the same number of parameters with the same types and in the same order as specified in the documentation, otherwise an exception will be thrown when executed.

After creating the `EventHandler` class, it needs to be registered as a listener at the entry point:

```java
/**
 * Implementing a client plugin
 */
public class ClientPlugin extends Plugin {
    /*
        Rest of the code
    */

    /**
     * Plugin entry point. Called when a plugin is loaded via FluxLoader.
     */
    @Override
    public void onInitialize() {
        EventManager.subscribe(new EventHandler());
    }

    /*
        Rest of the code
    */
}
```
## Communication between plugins

Since each plugin is isolated, situations may arise when you need to get some value or call a method from another plugin. Logic through events has been developed for this purpose. This is probably not the best implementation, it may be changed in the future.

To do this, in the plugin that is the receiving party, you need to create a method with `SubscribeSingleEvent` annotations:
```java
/**
 * Custom event for communication between plugins
 * @param text transmitted text
 */
@SubscribeSingleEvent(eventName = "pt-log")
public void customLogHandler(String text) {
    /**
     * implementation
     */
}

/**
 * Custom event for communication between plugins
 */
@SubscribeSingleEvent(eventName = "pt-getText")
public String customGetObjectHandler() {
    return "Example text";
}

```
> [!WARNING]
> It is important to use a unique event name, for example with partial or full mention of the plugin ID.

Then, on the receiving end, you can raise these events and receive (or not receive) the corresponding objects:
```java
EventManager.invokeSingleEventAndReturn("pt-log"); // Execute your implementation
String exampleText = EventManager.invokeSingleEventAndReturn("pt-getText"); // Returns `Example text` or null otherwise
```