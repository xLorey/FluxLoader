# Compilation

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
