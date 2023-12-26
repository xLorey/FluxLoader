# Create the user interface

Flux Loader is built on top of [ImGui](https://github.com/SpaiR/imgui-java), but the creation of its elements is implemented using widgets.
There are two main classes that can be inherited from, all of which implement the `IWidget` interface:

`ScreenWidget` - the main widgets that are the parents for others. These are the ones that can be added to display on the screen.

`ComponentWidget` - child widgets, which are a collection of basic ImGui elements.

## Creating the main widget

As an example, let's create a widget component. To do this, create the file `ChildWidget.java`:
```java
/**
 * Implementation of the child widget
 */
public class ChildWidget extends ComponentWidget {
    /**
     * Widget rendering
     */
    @Override
    public void render() {
        ImGui.button("Example Button", -1, 40);
    }
}
```
Now let's create the main widget `MainWidget.java`, create a window in it and draw our child widget:

```java
/**
 * Implementation of the main widget
 */
public class MainWidget extends ScreenWidget {
    /**
     * Child widget component
     */
    private final ChildWidget childWidget;

    /**
     * Initializing the main widget
     */
    public MainWidget() {
        childWidget = new ChildWidget();
        addChild(childWidget);
    }

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

        childWidget.render();
        
        ImGui.end();
    }
}

```

All main widgets will be drawn always and everywhere if you add it to the rendering process in the initialization method, in which case you need to limit its visibility to game states. After instantiating the child widget, we added it using `addChild`, this is necessary so that the `update` method is called on the child widget, which is important for updating the state of the widget. Rendering occurs when the `render` method is called. The entire UI implementation is carried out by ImGui, so we recommend reading the documentation for this library in its repository.

By default, widgets do not intercept mouse click events, so you can click 'through' them on standard UI elements. To prevent this, you need to call `captureMouseFocus` after the `ImGui.begin` method, it will capture the mouse and not allow interaction with standard elements.

To add it to the drawing process, you need to call the appropriate `addToScreenDraw` and `removeFromScreenDraw` methods in the right places:
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
        mainWidget.addToScreenDraw();
    }

    /**
     * Terminating the plugin
     */
    @Override
    public void onTerminate() {
        mainWidget.removeFromScreenDraw();
    }
}
```
In our case, the widget will be drawn when connecting/leaving a game session.