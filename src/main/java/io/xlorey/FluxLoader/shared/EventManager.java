package io.xlorey.FluxLoader.shared;

import io.xlorey.FluxLoader.annotations.SubscribeEvent;
import io.xlorey.FluxLoader.utils.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Event calling and subscription system
 */
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
        listeners.add(listener);
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
                        } catch (Exception e) {
                            e.printStackTrace();
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
            Logger.print(String.format("Number of arguments mismatch when calling event '%s' in method '%s' (class '%s')",
                    eventName, method.getName(), method.getDeclaringClass().getSimpleName()));
            return false;
        }

        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!parameterTypes[i].isInstance(args[i])) {
                Logger.print(String.format("Mismatch of passed and received argument types when calling event '%s' in method '%s' (class '%s')",
                        eventName, method.getName(), method.getDeclaringClass().getSimpleName()));
                return false;
            }
        }

        return true;
    }
}
