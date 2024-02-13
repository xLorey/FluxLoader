package io.xlorey.fluxloader.shared;

import io.xlorey.fluxloader.enums.EventPriority;
import io.xlorey.fluxloader.events.Event;
import io.xlorey.fluxloader.utils.Logger;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 07.02.2024
 * Description: Manages event subscriptions and their raising.
 *              Allows objects to register themselves as listeners for specific events and raise those events dynamically.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@UtilityClass
public class EventManager {
    /**
     * List of all listeners for specific events, where the key is the event name,
     * and the value is a list of all event handlers (listeners)
     */
    private static final Map<String, List<EventListener>> listeners = new HashMap<>();

    /**
     * Registers a listener object for a specific event.
     * @param listener Event listener. Must have a handleEvent method with a signature corresponding to the event.
     * @param priority Event priority, events with lower priority are called last
     */
    public static synchronized void subscribe(Event listener, EventPriority priority) {
        String eventName = listener.getEventName();
        listeners.computeIfAbsent(eventName.toLowerCase(), k -> new ArrayList<>()).add(new EventListener(listener, priority));
    }

    /**
     * Registers a listener object for a specific event. Handler priority is set to NORMAL
     * @param listener Event listener. Must have a handleEvent method with a signature corresponding to the event.
     */
    public static synchronized void subscribe(Event listener) {
        String eventName = listener.getEventName();
        listeners.computeIfAbsent(eventName.toLowerCase(), k -> new ArrayList<>()).add(new EventListener(listener, EventPriority.NORMAL));
    }

    /**
     * Raises an event by its name, passing arguments to listeners registered for that event.
     * The method dynamically looks up and calls the handleEvent method on each event listener, passing the specified arguments.
     * If an error occurs during a call, it is logged and the process continues for the remaining listeners.
     * @param eventName The name of the event to raise. The event name is case insensitive.
     * @param args Arguments to be passed to the event listener's handleEvent method. The type and number of arguments must match the expected parameters of the handleEvent method.
     */
    public void invokeEvent(String eventName, Object... args) {
        List<EventListener> eventListeners = listeners.get(eventName.toLowerCase());

        if (eventListeners == null) return;

        eventListeners.sort(Comparator.comparingInt(l -> l.getPriority().ordinal()));

        for (EventListener listener : eventListeners) {
            Event eventHandler = listener.getHandler();

            try {
                invokeHandleEvent(eventHandler, args);
            } catch (Exception e) {
                Logger.print(String.format("An error occurred while trying to call method '%s' in listener '%s'!",
                        eventName,
                        listener.getClass()));
                e.printStackTrace();
            }
        }
    }

   /**
     * Calls the handleEvent method on all registered event listeners.
     * Each listener's handleEvent method must be compatible with the arguments passed.
     * @param listener The event listener to be called.
     * @param args Arguments passed to the listener's handleEvent method.
     * @throws NoSuchMethodException if a handleEvent method with matching arguments is not found.
     */
    private static void invokeHandleEvent(Event listener, Object[] args) throws NoSuchMethodException {
        Method[] methods = listener.getClass().getMethods();

        for (Method method : methods) {
            if (isCompatibleMethod(method, args)) {
                try {
                    method.invoke(listener, args);
                } catch (Exception e) {
                    StringBuilder argTypes = new StringBuilder();
                    for (Object arg : args) {
                        argTypes.append(arg.getClass().getSimpleName()).append(", ");
                    }
                    if (!argTypes.isEmpty()) {
                        argTypes = new StringBuilder(argTypes.substring(0, argTypes.length() - 2));
                    }

                    throw new RuntimeException(String.format("Failed to invoke 'handleEvent' on %s with arguments [%s]. Error: %s",
                            listener.getClass().getName(), argTypes, e.getCause().getMessage()), e);
                }
                return;
            }
        }

        throw new NoSuchMethodException("Compatible 'handleEvent' method not found for event  '" + listener.getEventName() + "'");
    }

    /**
     * Checks whether the method meets the requirements to be called.
     * The method must be named "handleEvent" and have parameter types compatible with the arguments passed.
     * @param method Method to check.
     * @param args Arguments to be passed to the method.
     * @return true if the method meets the requirements to be called.
     */
    private static boolean isCompatibleMethod(Method method, Object[] args) {
        if (!method.getName().equals("handleEvent") || method.getParameterTypes().length != args.length) {
            return false;
        }

        for (int i = 0; i < method.getParameterTypes().length; i++) {
            if (!method.getParameterTypes()[i].isAssignableFrom(args[i].getClass())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Standard event listener class
     */
    @Getter
    private static class EventListener{
        /**
         * Handler object for this event
         */
        private final Event handler;
        /**
         * Processing priority, according to the EventPriority enumeration
         */
        private final EventPriority priority;

        /**
         * Event Listener Data Constructor
         * @param listener Handler object for this event
         * @param eventPriority Processing priority, according to the EventPriority enumeration
         */
        public EventListener(Event listener, EventPriority eventPriority) {
            this.handler = listener;
            this.priority = eventPriority;
        }
    }
}