package io.xlorey.fluxloader.enums;

/**
 * Author: Deknil
 * Date: 13.02.2024
 * GitHub: <a href="https://github.com/Deknil">https://github.com/Deknil</a>
 * Description: The enumeration defines five event priority levels used to control the order in which events are processed in an application.
 * <p> FluxLoader © 2024. All rights reserved. </p>
 */
public enum EventPriority {
    /**
     * Highest priority events.
     */
    HIGHEST,

    /**
     * High priority events.
     */
    HIGH,

    /**
     * Events with normal priority.
     */
    NORMAL,

    /**
     * Low priority events.
     */
    LOW,

    /**
     * Lowest priority events.
     */
    LOWEST;
}
