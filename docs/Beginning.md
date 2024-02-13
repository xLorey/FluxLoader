# Getting started with modifications

## General concepts

Flux Loader is a loader of user files (modifications) packaged in a `jar` archive. 
Hereinafter, the concept `plugin` will be used to define these modifications. 
The loader architecture is designed in such a way that one plugin can contain multiple entry points for the client and server 
sides, in other words, you can write one plugin for both the client and the server at once. 
This principle is especially useful and effective when writing synchronization between the client and the server. 
In addition, you should take into account that each plugin is loaded `in isolation`, although it contains the same parent 
`ClassLoader`. This decision is due to the specifics of the `JVM` and the desire to avoid problems with name conflicts between the names of the plugin packages.

## Creating a Project

You can start from scratch or based on [template](https://github.com/xLorey/FluxLoader-PluginTemplate). Here we will only describe creating a project from scratch. To do this, you need to create a new Java project in your IDE. **Important**, it must be based on `JDK 17`, you can download it [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html), since Project Zomboid is written specifically on this version and otherwise your plugin simply will not run. We recommend using `Gradle` as a builder, for example [version 8.5](https://gradle.org/releases/).
Create a `build.gradle` file and fill it with the following:
```groovy
/**
 * Standard plugins for the Gradle builder
 */
plugins {
    id 'java'
}

/**
 * Project group and version
 */
group = 'io.xlorey.plugintemplate'
version = '1.0.0'

/**
 * Repositories used
 */
repositories {
    mavenCentral()
}

/**
 * Plugin Dependencies
 */
dependencies {
    /**
     * Flux Loader - connects only in compileOnly format
     */
    compileOnly files("libs/FluxLoader-0.8.0.jar")
}

/**
 * Updating project metadata
 */
processResources {
    inputs.property "version", project.version

    filesMatching("metadata.json") {
        expand "version": project.version
    }
}

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

In the project dependencies, you must indicate the current version of Flux Loader, always in the `compileOnly` format, since it is only needed for compilation, everything else will be found during execution. You can find out how to connect the bootloader on the main page of the repository in `readme.md`

In the resource root of the project, for example, `src/main/java/resources/`, create a file `metadata.json`:
```json
{
  "revision": "1",
  "name": "Plugin Template",
  "description": "Basic plugin implementation",
  "id": "PluginTemplate",
  "version": "${version}",
  "authors": [
    "xLorey Team"
  ],
  "contact": [
    "xlorey@mail.ru"
  ],
  "license": "MIT",
  "icon": "icon.png",
  "entrypoints": {
    "client": [
      "io.xlorey.plugintemplate.client.ClientPlugin"
    ],
    "server": [
      "io.xlorey.plugintemplate.server.ServerPlugin"
    ]
  },
  "controlsEntrypoint": "io.xlorey.plugintemplate.client.PluginControls",
  "dependencies": {
    "project-zomboid": "=41.78.16",
    "flux-loader": "=0.8.0"
  }
}
```
where:

`revision` - metadata revision.

`name` - plugin name.

`description` - plugin description.

`id` - unique plugin ID.

`version` - plugin version, dynamically installed by Gradle.

`authors` - list of authors of the project.

`license` - project license.

`icon` - the path to the project icon, size 512x512, required for every project.

`entrypoints` - a list of entry points, there may be several. Each class must inherit from the FluxLoader of the `Plugin` class.

`controlsEntrypoint` - entry point for plugin controls displayed on the client in the plugin menu. You can leave it empty, then there will be no controls.

`dependencies` - list of project dependencies. Specifying `project-zomboid` and `flux-loader` is mandatory. Possible conditions `>=`, `<=`, `<`, `>`, `=`.

> [!NOTE]
> To get metadata from the plugin, you can call the `getMetadata()` method

And the file `icon.png` is your plugin icon, it will be displayed exclusively on the client in the plugin settings menu, however, in order to maintain the plugin structure, it is also necessary for server plugins.

The project structure should look something like this:

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
            └── metadata.json
            └── icon.png
            └── ...
    └── ...
└── build.gradle
└── settings.gradle
└── LICENSE
└── ...
```

After completing all the steps described above, you can start writing a plugin.

## Entry points

Client and server entry points should **always** inherit from `Plugin` in the Flux Loader package. Approximate view of the entry point:
```java
/**
 * Implementing a client plugin
 */
public class ClientPlugin extends Plugin {
    /**
     * Plugin entry point. Called when a plugin is loaded via FluxLoader.
     */
    @Override
    public void onInitialize() {
    }

    /**
     * Executing the plugin
     */
    @Override
    public void onExecute() {

    }

    /**
     * Terminating the plugin
     */
    @Override
    public void onTerminate() {

    }
}
```

Each entry point contains 3 methods by default: `onInitialize`, `onExecute` and `onTerminate`.
`onInitialize` - always executed when the plugin is loaded by the game/server, executed once.

`onExecute` - executed when joining a game session on the client and when starting the server.

`onTerminate` - executed when exiting the main menu (exiting the game session) and shutting down the server via `quit`, will not be called in case of an unscheduled shutdown.

You can override each of the methods; by default, they do not provide any implementation.

To create plugin controls (exclusively on the client side), you need to create a class and implement the `IControlsWidget` interface:
```java
/**
 * Class for creating plugin controls
 */
public class Controls implements IControlsWidget {
    /**
     * Rendering controls using ImGui
     */
    @Override
    public void render(float panelWidth) {

    }
}
```

The entire user interface in Flux Loader is based on [ImGui](https://github.com/SpaiR/imgui-java), in the `render` method you can place any ImGui elements, they will be drawn in the plugin management menu on the client side .