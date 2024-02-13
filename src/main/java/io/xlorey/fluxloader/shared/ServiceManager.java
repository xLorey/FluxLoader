package io.xlorey.fluxloader.shared;

import java.util.HashMap;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 13.02.2024
 * Description: The service manager allows you to register services by their interfaces and access them
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
public class ServiceManager {
    /**
     * Stores registered services by associating interfaces with their implementations.
     */
    private static final HashMap<Class<?>, Object> services = new HashMap<>();

    /**
     * Registers a service by its interface.
     * @param <T> service type
     * @param serviceInterface service interface
     * @param serviceImplementation service implementation
     */
    public static <T> void register(Class<T> serviceInterface, T serviceImplementation) {
        services.put(serviceInterface, serviceImplementation);
    }

    /**
     * Returns the registered service by its interface.
     * @param <T> service type
     * @param serviceInterface service interface
     * @return an instance of the service corresponding to the specified interface, or {@code null} if the service is not found
     */
    public static <T> T getService(Class<T> serviceInterface) {
        return serviceInterface.cast(services.get(serviceInterface));
    }
}
