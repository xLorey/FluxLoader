package io.xlorey.FluxLoader.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Subscription to in-game and user events
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SubscribeEvent {
    /**
     * Event name
     */
    String eventName() default "";
}