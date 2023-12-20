# Writing a plugin (mod)

## Contents

-   [Writing a plugin (mod)](#writing-a-plugin-mod)
    -   [Contents](#contents)
    -   [Introduction](#introduction)
-   [Beginning of work](#beginning-of-work)
    -   [Setting up Gradle](#setting-up-gradle)
    -   [Creating plugin metadata](#creating-plugin-metadata)
    -   [Creating the main plugin class](#creating-the-main-plugin-class)
    -   [Subscribe to events](#subscribe-to-events)
    -   [Organization of interaction between plugins](#organization-of-interaction-between-plugins)
    -   [Custom chat and console command](#custom-chat-and-console-command)
    -   [Compilation](#compilation)

## Introduction

The FluxLoader plugin loading system includes the ability to load both server and client plugins.
To work, you need to move your plugin in Jar format to the `plugins` folder in the game/server directory.

Loading of plugins starts automatically when the server/client starts with metadata output to the logs.

Plugin Template: [GitHub](https://github.com/xLorey/FluxLoader-PluginTemplate)

# Beginning of work

We recommend using plugins for writing [JetBrains Intellij Idea](https://www.jetbrains.com/idea/), for the simple reason that this IDE is very convenient and allows you to make all the necessary project settings in just two clicks.

**Project Zomboid** developed on [Java version 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html), so for compiling plugins and [FluxLoader](https://github.com/xLorey/FluxLoader-PZ/releases) you must use this particular version of the JDK, as well as the current version [Gradle](https://gradle.org/) (version used 8.4 in example).

The basic plugin template for Flux Loader is hosted on GitHub, where you can simply start writing your own plugin based on it.

## Setting up Gradle

First of all, you need to include FluxLoader as a library. To do this, download the latest version [FluxLoader](https://github.com/xLorey/FluxLoader-PZ/releases) and move it to the `libs` folder in your project. The structure should look something like this:

```
PluginProject
└── libs
    └── FluxLoader-x.x.x.jar
    └── ...
└── src
    └── main
        └── java
            └── ...
        └── resources
            └── plugin.json
            └── icon.png
            └── ...
    └── ...
└── build.gradle
└── settings.gradle
└── LICENSE
└── ...
```

Next, we connect FluxLoader in the `build.gradle` file only for compilation:

```groovy
/**
 * Plugin Dependencies
 */
dependencies {
    /**
     * Flux Loader - connects only in compileOnly format
     */
    compileOnly files('libs/FluxLoader-x.x.x.jar')
}
```

Great, in the same file we will create a packaging script in a Jar archive:

```groovy
/**
 * Build the project in a jar archive. The source file will be created in the 'build' folder
 */
jar {
    archiveFileName = "${rootProject.name}-${version}.jar"
    destinationDirectory = file('build')
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    from {
        configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) }
    }

    from ('.') {
        include('LICENSE')
    }
}
```

Now, when you run the Jar script, an archive will be created in the `build` folder.

## Creating plugin metadata

All metadata about the plugin is generated based on its metadata. They are located in `plugin.json` in the folder with the resources of your project. Its contents look something like this (required fields and values):

```json
{
    "name": "Plugin Template",
    "description": "Basic plugin implementation",
    "id": "PluginTemplate",
    "version": "${version}",
    "authors": ["xLorey Team"],
    "contact": ["xlorey@mail.ru"],
    "license": "MIT",
    "icon": "icon.png",
    "entrypoints": {
        "client": ["io.xlorey.plugintemplate.client.ClientPlugin"],
        "server": ["io.xlorey.plugintemplate.server.ServerPlugin"]
    },
    "dependencies": {
        "project-zomboid": "=41.78.16",
        "flux-loader": "=0.2.0"
    }
}
```

`name` - plugin name.

`description` - plugin description.

`id` - unique plugin ID.

`version` - plugin version, dynamically installed by Gradle.

`authors` - list of authors of the project.

`license` - project license.

`icon` - the path to the project icon, size 512x512.

`entrypoints` - a list of entry points, there may be several. Each class must inherit from the FluxLoader of the `Plugin` class.

`dependencies` - list of project dependencies. Specifying `project-zomboid` and `flux-loader` is mandatory. Possible conditions `>=`, `<=`, `<`, `>`.

> [!NOTE]
> To get metadata from the plugin, you can call the `getPluginInfo()` method

## Creating the main plugin class

> [!WARNING]
> Each class - the entry point - must inherit from the `Plugin` class in FluxLoader

Each plugin requires an entry point for both the server side and the client side (there may be several for each of the parties). They are specified as the path to the class in `plugin.json` in the string `entrypoints`. After loading the plugin, the `OnInitialize` method will be called at each entry point. Accordingly, when loading the game (client), the entry points specified in the `client` line will be called, in case of server startup - `server`.

Plugins can be client-only or server-only. To do this, just leave the desired line empty. For example:

```json
{
  <...>
  "entrypoints": {
    "client": [
      "io.xlorey.plugintemplate.client.ClientPlugin"
    ],
    "server": [
      ""
    ]
  },
  <...>
}
```

As an example, let's create classes for the client:

```java
package io.xlorey.plugintemplate.client;

import io.xlorey.FluxLoader.annotations.SubscribeEvent;
import io.xlorey.FluxLoader.plugin.Plugin;
import io.xlorey.FluxLoader.utils.Logger;

/**
 * Implementing a client plugin
 */
public class ClientPlugin extends Plugin {
    /**
     * Plugin entry point. Called when a plugin is loaded via FluxLoader.
     */
    public void onInitialize() {
        Logger.print("Hello world in client!");
        Logger.print("Test info: " + getPluginInfo().getName());
    }

    /**
     * Example implementation of event subscription. Calls every frame and prints a message to the console
     */
    @SubscribeEvent(eventName = "OnRenderTick")
    public void renderHandler(){
        Logger.print("Render Tick");
    }
}

```

The name of the class - the entry point - can be arbitrary, **important** only inherited from `Plugin`. In this case, after loading the plugin, a message will be displayed in the log:

```
Hello world in client!
```

and

```
Test info: Plugin Template
```

## Subscribe to events

By default, each point is automatically registered as an event listener, so you can immediately subscribe to events (game and user). However, if there is a need to register a third-party class, then you need to use:

```java
EventManager.subscribe(classInstance)
```

Instead of `classInstance`, you must specify an instance of the class that needs to be registered as a listener.

> [!WARNING]
> One method can be subscribed to **only one** [event](Wiki.md)!

Subscription to events occurs exclusively through the annotation `@SubscribeEvent` with the event name as an argument. It must be located at the event handler method. If the event implies arguments, they must also be specified in the handler method.

There is also an annotation `@SubscribeSingleEvent(eventName="<...>")`. It is attached to methods that can return something (some object). For example so:

```java
@SubscribeSingleEvent(eventName = "getTestText")
public String getTestTextHandler(){
    return "Text"
}
```

**Important!** This annotation is used exclusively in plugins, the event name must be **strictly** unique, preferably using the plugin ID. Such a measure is necessary for the organization of interaction between plugins. In the future, when he finds a better and easier way, everything can change.

## Organization of interaction between plugins

At the moment, in FluxLoader, interaction between plugins is organized through mutual subscription and calling events. To do this, the annotation `@SubscribeSingleEvent` or `@SubscribeEvent` must be attached to the **non-static** method. One of them! Implementation example:

Plugin #1:

```java
/**
 * Processing of sending a message, it is necessary to use a unique event name
 * @param text message text
 */
@SubscribeEvent(eventName = "sendMessage")
public void sendMessageHandler(String text){
    API.sendMessage(text);
}

/**
 * Processing the output of a value from the API.md
 * @param args arguments
 * @return returns a constant value from the API.md
 */
@SubscriSinglebeEvent(eventName = "getConstantInfo")
public String getConstantInfoHandler(String arg){
    return API.getConstantInfo(arg);
}
```

Plugin #2:

```java
/**
 * Plugin initialization method
 */
@Override
public void onInitialize() {
    EventManager.invokeEvent("sendMessage", "Hello World!");
    String infoByExtern = EventManager.invokeSingleEventAndReturn("getConstantInfo", "VERSION"); // If nothing is found returns NULL
}
```

## Custom chat and console command

FluxLoader supports creating custom commands for chat and/or console. To create a new command, you need to create a new class and inherit from the `ICommand` interface in the `io.xlorey.FluxLoader.interfaces` package. After implementing the methods, it will look something like this:
```java
/**
 * An annotation that includes the name of the command (without slashes or other prefixes)
 */
@CommandName(command = "example")
/**
 * An annotation to specify the scope where a command can be executed.
 */
@CommandExecutionScope(scope = CommandScope.BOTH)
/**
 * An annotation that provides the text to be output to chat when the command is used
 */
@CommandChatReturn(text = "Example command has been invoked!")
/**
 * An annotation that indicates the required access level for a user.
 * Users with a lower access level than specified will not have access to the functionality.
 */
@CommandAccessLevel(accessLevel = AccessLevel.ADMIN)
/**
 * Implementing a new server command
 */
public class ExampleCommand implements ICommand {
    /**
     * Performing a chat command action
     * @param playerConnection player connection, if called from the console, the connection will return as null
     * @param args arguments of the received command
     */
    void onInvoke(UdpConnection playerConnection, String[] args) {
        <...>
    };
}

```

After that, it needs to be registered, for this in the main class of the plugin (entry point) in the plugin initialization method you need to add the following:
```java
/**
 * Implementing a server plugin
 */
public class ServerPlugin extends Plugin {
    /**
     * Plugin entry point. Called when a plugin is loaded via FluxLoader.
     */
    public void onInitialize() {
        CommandsManager.addCommand(new ExampleCommand());
    }
}

```

Now, depending on what scope you have set, this command can be used in chat and/or the server console with the ability to pass arguments. For example:
```bash
# Without implementing methods, invoke will simply return in chat/console: 'Example command has been invoked!'
/example args1 args2 ...
```

## Compilation

After you have written your plugin, you need to build it using `jar` in `build.gradle`:

```groovy
/**
 * Build the project in a jar archive. The source file will be created in the 'build' folder
 */
jar {
    archiveFileName = "${rootProject.name}-${version}.jar"
    destinationDirectory = file('build')
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    from {
        configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) }
    }

    from ('.') {
        include('LICENSE')
    }
}
```

Just run this script and the assembled Jar archive will appear in the `build` folder. Then you can use it (move it to the 'plugins` folder in the game directory)
