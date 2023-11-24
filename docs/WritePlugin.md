# Writing a plugin (mod)

## Introduction

The FluxLoader plugin loading system includes the ability to load both server and client plugins.
To work, you need to move your plugin in Jar format to the `plugins` folder in the game/server directory.

Loading of plugins starts automatically when the server/client starts with information output to the logs.
Plugins may have dependencies on other plugins based on their ID.
The download order is sorted automatically, if something goes wrong, you can find out about it in
logs (handling of cases of recursive dependency, re-download of duplicates, etc. is included).

# Beginning of work

We recommend using plugins for writing [JetBrains Intellij Idea](https://www.jetbrains.com/idea/), 
for the simple reason that this IDE is very convenient and allows you to make all the necessary project settings in just two clicks.

**Project Zomboid** developed on Java version 17, so for compiling plugins and FluxLoader
you must use this particular version of the JDK, as well as the current version [Gradle](https://gradle.org/) (version used 8.4).

The basic plugin template for Flux Loader is hosted on GitHub, where you can simply start writing your own plugin based on it.

## Настройка Gradle

First of all, you need to include FluxLoader as a library. To do this, download the latest version [FluxLoader](https://github.com/xLorey/FluxLoader-PZ/releases) and move it to the `libs` folder in your project. The structure should look something like this:

```
PluginProject
└── libs
    └── FluxLoader-x.x.x.jar
└── src
    └── main
        └── ...
└── build.gradle
└── settings.gradle
```

Next, we connect FluxLoader in the `build.gradle` file only for compilation:

```groovy
dependencies {
    compileOnly files('libs/FluxLoader-x.x.x.jar')
}
```

Great, in the same file we will create a packaging script in a Jar archive:

```groovy
ext {
    /**
     * Plugin entry point - specified independently based on the project name
     */
    MainClass = 'io.xlorey.templateplugin.Main'
}
jar {
    archiveFileName = "${rootProject.name}-${version}.jar"
    destinationDirectory = file('build')
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    manifest {
        attributes(
                'Main-Class': project.ext.MainClass
        )
    }
    from {
        configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) }
    }
}
```

Now, when you run the Jar script, an archive will be created in the `build` folder.

## Creating the main plugin class

Each plugin requires an entry point - the main class Main. In the project directory in `src/main/...` we will create a file `Main.java` and fill in the basic structure of the plugin.

```java
package io.xlorey.templateplugin;

import io.xlorey.FluxLoader.plugin.IPlugin;
import io.xlorey.FluxLoader.utils.Logger;

/**
 * Main plugin class
 */
public class Main extends FluxPlugin {
    /**
     * Plugin initialization event
     */
    @Override
    public void onInitialize() {
        Logger.print("Hello world from FluxPlugin Template!", getPluginId());
    }

    /**
     * Getting the plugin ID
     * @return Plugin ID as a string
     */
    @Override
    public String getPluginId() {
        return "FluxTemplate";
    }

    /**
     * Getting the plugin name
     * @return plugin name
     */
    @Override
    public String getPluginName() {
        return "FluxTemplate Plugin";
    }

    /**
     * Getting the plugin version
     * @return string version of the plugin
     */
    @Override
    public String getPluginVersion() {
        return "1.0.0";
    }

    /**
     * Getting an array of dependencies for a given plugin
     * @return array of strings - ID - dependent plugins. Returns an empty array if there are no dependencies
     */
    @Override
    public String[] getPluginDependencies() {
        return new String[0];
    }
}
```

All plugins inherit from the abstract class `FluxPlugin`; by implementing its methods you create a basic structure. It's important to note that if your plugin has no dependencies, you must send an empty array.

## Subscribe to events

The loader implements a subscription system for in-game (Lua) and user events. When loading the plugin, the main class Main is automatically signed as a listener and it is enough to simply specify the appropriate annotation for the desired method; for all others, you need to subscribe separately:

```java
public class Main extends FluxPlugin {
    @Override
    public void onInitialize() {
        EventManager.subscribe(Class);
    }
}
```

Instead of `Class` in the `subscribe` method, you must specify your class, which should be registered as a listener.
To subscribe a method to an event, you must specify the `SubcribeEvent` annotation:

```java
@SubscribeEvent(eventName = "RenderTick")
public static void renderTick(){
    ...
}
```

> [!WARNING]
> One method can be subscribed to **only one** [event](Events.md)!

Also, if the event involves passing an argument, your method must also accept it:

```java
@SubscribeEvent(eventName = "Example")
public static void exampleHandler(String arg1, int arg2, ...){
    ...
}
```

## Compilation

Now that you have written a basic plugin, you need to compile and archive it. To do this, just run the `jar` script, then move the compiled plugin to the `plugins` folder and launch the game!
