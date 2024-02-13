# Using events

Flux Loader has its own event subscription system. All default and custom events are available to you, and you can subscribe to each of them. However, for this you must fulfill several conditions.

## Creating a class

All event handlers must implement the corresponding abstract event classes from the `events` package in FluxLoader.

```java
/**
 * Event handler for server shutdown by command
 */
public class OnServerShutdownHandler extends OnServerShutdown {
    /**
     * Event Handling
     */
    @Override
    public void handleEvent() {
        /*
        Code TODO
         */
    }
}
```

In the documentation you can find a complete description of the event and the available arguments in `handleEvent`.
There are events that overload `handleEvent` with different arguments, so you need to be clear about what exactly you want to achieve.

After creating the `EventHandler` class, it needs to be registered as a listener at the entry point:

```java
/**
 * Implementing a client plugin
 */
public class ServerPlugin extends Plugin {
    /*
        Rest of the code
    */

    /**
     * Plugin entry point. Called when a plugin is loaded via FluxLoader.
     */
    @Override
    public void onInitialize() {
        EventManager.subscribe(new OnServerShutdownHandler(), EventPriority.HIGH);
    }

    /*
        Rest of the code
    */
}
```

In addition to the handler object, you can optionally specify the execution priority. The higher the priority, the earlier the event will be executed in relation to other handlers with lower priority. Without specifying a priority it will be set to `NORMAL`