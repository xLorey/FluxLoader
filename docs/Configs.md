# Configuration files

Flux Loader provides a configuration file system built in YAML that is very similar to the implementation in [MC Fabric](https://bukkit.org/).

You have the opportunity to use the standard `config.yml` and create your own, either using a template or dynamically. `config.yml` must be created from a template. To do this, create a `config.yml` file in the resource root of your project and fill it in, observing the language syntax. The structure should look something like this:
```
PluginProject
└── src
    └── main
        └── resources
            └── config.yml
            └── example.yml
            └── ...
```
Now, in the plugin initialization method, you can copy it and load it immediately, or just load it if it is already in the plugin config folder (it is located in the same directory as the plugin). For example, let's create a custom config template `example.yml` and fill it in. Now the templates look something like this, standard:

```yml
message: Welcome!
```

and `example.yml`:

```yml
example: Test example!
```

Their implementation at the entry point would look like this:

```java
public class ClientPlugin extends Plugin {
    /**
     * Example of a custom configuration file
     */
    private Configuration exampleCopy;
    /**
     * Example of a custom configuration file
     */
    private Configuration exampleCreate;

    /**
     * Plugin entry point. Called when a plugin is loaded via FluxLoader.
     */
    @Override
    public void onInitialize() {
        exampleCopy = new Configuration(getConfigPath("example.yml"), this);
        exampleCreate = new Configuration(getConfigPath("exampleOther.yml"), this);

        exampleCopy.load(); // Loading, with preliminary copying from JAR, or creating an empty file
        exampleCreate.load(); // Creating an empty config file, since it is not in the JAR archive of the template (Or loadings if it is already in the config folder)

        saveDefaultConfig(); // Copying and loading the standard config (an empty config is created if it is not in the Jar archive)

        exampleCreate.setString("newStringKey", "Hello World!");
        exampleCreate.setString("newStringKey.nested", "Hello World nested!");

        exampleCreate.save();

        String defaultConfigText = getDefaultConfig().getString("message");
    }
}
```
To copy and load the standard config, we used the `saveDefaultConfig` method, this is the only config that can be accessed via `getDefaultConfig` at the entry point. All configs are instances of the `Configuration` class. It contains methods for creating, loading, saving, and manipulating primitive types, text, lists, and dictionaries. After each change to the config, it must be saved by calling the `save` method.

To copy (create if not in the JAR) and load the template configuration, you must use the `load` method, it is similar to `saveDefaultConfig`, but is used for custom configurations. When creating a config object, you must specify the path where it will be saved. To avoid errors, we recommend getting it via `getConfigPath(configName)`, and the second argument must be passed an instance of the entry point, this is necessary for loading and copying the template from the `jar` archive.

`Configuration` supports nested keys, they are specified with a dot, that is, `server.port` will be equivalent to:

```yml
server:
    port: 27015
```
If the key does not exist, then `null` will be returned upon retrieval. Also, if you access the default config via `getDefaultConfig`, but do not save it via `saveDefaultConfig`, an exception will be thrown, since in this case it will return `null`.

If you don't want to create templates and do everything dynamically, then the declaration of the config object will remain the same, but the rest will be a little different. Creation occurs through `create`, you can check the presence of the file through `isExists` and then load it through `load`.
