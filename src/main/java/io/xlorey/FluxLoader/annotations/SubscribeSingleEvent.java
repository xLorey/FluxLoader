package io.xlorey.FluxLoader.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Subscription to in-game and user events
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SubscribeSingleEvent {
    /**
     * Event name
     * @return The default event name for the subscription. If not specified, an empty string is returned.
     */
    String eventName() default "";
}