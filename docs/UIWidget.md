# Create the user interface

Flux Loader is built on top of [ImGui](https://github.com/SpaiR/imgui-java), but the creation of its elements is implemented using widgets.
There is one class `Widget` that implements `IWidget` and from which you can and should inherit to create your own UI elements.
Each separate class inheriting from `Widget` is expected to be a separate UI window.

## Creating the main widget

As an example, let's create a widget component `MainWidget.java`, create a window in it:

```java
/**
 * Implementation of the main widget
 */
public class MainWidget extends Widget {
    /**
     * Update window state
     */
    @Override
    public void update() {
        super.update();
    }

    /**
     * Widget rendering
     */
    @Override
    public void render() {
        ImGui.setNextWindowSize(650, 400);

        ImGui.begin("Example Widget", ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoCollapse);

        captureMouseFocus();
        
        ImGui.end();
    }
}

```

All core widgets will render whenever and wherever you want if you add them to the rendering process in the initialization method, in which case you need to limit their visibility to game states.
Rendering occurs when the render method is called. All UI implementation is handled by ImGui, so we recommend reading the documentation for this library in its repository.
By default, widgets do not intercept mouse click events, so you can click 'through' them on standard UI elements. To prevent this, you need to call `captureMouseFocus` after the `ImGui.begin` method, it will capture the mouse and not allow interaction with standard elements.

To add it to the drawing process, you need to call the appropriate `addToScreen` and `removeFromScreen` methods in the right places:
```java
/**
 * Implementing a client plugin
 */
public class ClientPlugin extends Plugin {
    /**
     * Main widget
     */
    private MainWidget mainWidget;

    /**
     * Plugin entry point. Called when a plugin is loaded via FluxLoader.
     */
    @Override
    public void onInitialize() {
        mainWidget = new MainWidget();
    }

    /**
     * Executing the plugin
     */
    @Override
    public void onExecute() {
        mainWidget.addToScreen();
    }

    /**
     * Terminating the plugin
     */
    @Override
    public void onTerminate() {
        mainWidget.removeFromScreen();
    }
}
```
In our case, the widget will be drawn when connecting/leaving a game session.