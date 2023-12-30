package io.xlorey.FluxLoader.shared;

import io.xlorey.FluxLoader.annotations.SubscribeEvent;
import io.xlorey.FluxLoader.annotations.SubscribeSingleEvent;
import io.xlorey.FluxLoader.utils.Logger;
import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Event calling and subscription system
 */
@UtilityClass
public class EventManager {
    /**
     * Event Listeners
     */
    private static final ArrayList<Object> listeners = new ArrayList<>();

    /**
     * Subscribing a listener class to events
     * @param listener - Object listener
     */
    public static void subscribe(Object listener) {
        if (!isUniqueSingleEvents(listener)) {
            return;
        }

        listeners.add(listener);
    }

    /**
     * Returns a set of unique event names marked with the SubscribeSingleEvent annotation.
     * found in the methods of the specified listener object.
     *
     * @param listener A listener object whose methods search for event names.
     * @return Set of unique event names.
     */
    private static HashSet<String> getSingleEventNames(Object listener) {
        HashSet<String> eventNames = new HashSet<>();
        for (Method method : listener.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(SubscribeSingleEvent.class)) {
                SubscribeSingleEvent annotation = method.getAnnotation(SubscribeSingleEvent.class);
                eventNames.add(annotation.eventName());
            }
        }
        return eventNames;
    }

    /**
     * Checks whether the listener object contains methods with the same event names,
     * marked with the SubscribeSingleEvent annotation, which are already registered in the system.
     *
     * @param newListener The listener object to test.
     * @return true if there are no conflicting event names in the new listener, false otherwise.
     */
    private static boolean isUniqueSingleEvents(Object newListener) {
        HashSet<String> newListenerEvents = getSingleEventNames(newListener);

        for (Object registeredListener : listeners) {
            HashSet<String> registeredListenerEvents = getSingleEventNames(registeredListener);
            for (String eventName : newListenerEvents) {
                if (registeredListenerEvents.contains(eventName)) {
                    Logger.printLog(String.format("Error subscribing class to events! Duplicate single event '%s' detected in '%s'! Skipping...",
                            eventName,
                            newListener.getClass().getName()));
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Calls a method marked with the SubscribeSingleEvent annotation that matches the given event name.
     * The method must be compatible with the arguments provided. Returns the result of a method call.
     *
     * @param eventName The name of the event to raise.
     * @return The result of calling the event method. Returns null if no matching method is found.
     */
    public static Object invokeSingleEventAndReturn(String eventName) {
        return invokeSingleEventAndReturn(eventName, (Object[]) null);
    }

    /**
     * Calls a method marked with the SubscribeSingleEvent annotation that matches the given event name.
     * The method must be compatible with the arguments provided. Returns the result of a method call.
     *
     * @param eventName The name of the event to raise.
     * @param args Arguments passed to the event method.
     * @return The result of calling the event method. Returns null if no matching method is found.
     */
    public static Object invokeSingleEventAndReturn(String eventName, Object... args) {
        for (Object listener : listeners) {
            for (Method method : listener.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(SubscribeSingleEvent.class)) {
                    SubscribeSingleEvent annotation = method.getAnnotation(SubscribeSingleEvent.class);
                    if (annotation.eventName().equals(eventName) && isMethodCompatible(method, eventName, args)) {
                        try {
                            method.setAccessible(true);
                            return method.invoke(listener, args);
                        } catch (InvocationTargetException e) {
                            Throwable cause = e.getCause();
                            String className = listener.getClass().getName();
                            String methodName = method.getName();
                            Logger.printLog(String.format("Error invoking single event '%s' method '%s' in class '%s': %s", eventName, methodName, className, Arrays.toString(cause.getStackTrace())));
                        } catch (IllegalAccessException e) {
                            Logger.printLog(String.format("Illegal access when invoking single event '%s' in class '%s': %s", eventName, listener.getClass().getName(), Arrays.toString(e.getStackTrace())));
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Send an event to all listeners
     * @param eventName event name
     */
    public static void invokeEvent(String eventName) {
        invokeEvent(eventName, (Object[])null);
    }

    /**
     * Send an event to all listeners
     * @param eventName event name
     * @param args event arguments
     */
    public static void invokeEvent(String eventName, Object... args) {
        for (Object listener : listeners) {
            for (Method method : listener.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(SubscribeEvent.class)) {
                    SubscribeEvent annotation = method.getAnnotation(SubscribeEvent.class);
                    if (annotation.eventName().equals(eventName) && isMethodCompatible(method, eventName, args)) {
                        try {
                            method.setAccessible(true);
                            method.invoke(listener, args);
                        } catch (InvocationTargetException e) {
                            Throwable cause = e.getCause();
                            String className = listener.getClass().getName();
                            String methodName = method.getName();
                            Logger.printLog(String.format("Error invoking event '%s' method '%s' in class '%s': %s", eventName, methodName, className, Arrays.toString(cause.getStackTrace())));
                        } catch (IllegalAccessException e) {
                            Logger.printLog(String.format("Illegal access when invoking event '%s' in class '%s': %s", eventName, listener.getClass().getName(), Arrays.toString(e.getStackTrace())));
                        }
                    }
                }
            }
        }
    }

    /**
     * Checking the called method to ensure that the arguments and their types match correctly
     * @param method called method
     * @param eventName event name
     * @param args event arguments
     * @return true - check passed, false - check failed
     */
    private static boolean isMethodCompatible(Method method, String eventName, Object[] args) {
        if (args == null || args.length == 0) {
            return method.getParameterCount() == 0;
        }

        if (method.getParameterCount() != args.length) {
            Logger.printLog(String.format("Number of arguments mismatch when calling event '%s' in method '%s' (class '%s')",
                    eventName, method.getName(), method.getDeclaringClass().getSimpleName()));
            return false;
        }

        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!parameterTypes[i].isInstance(args[i])) {
                String expectedTypeName = parameterTypes[i].getName();
                String receivedTypeName = args[i] == null ? "null" : args[i].getClass().getName();

                Logger.printLog(String.format("Mismatch of passed and received argument types when calling event '%s' in method '%s' (class '%s'). Expected '%s', but got '%s'",
                        eventName, method.getName(), method.getDeclaringClass().getSimpleName(), expectedTypeName, receivedTypeName));
                return false;
            }
        }

        return true;
    }
}
