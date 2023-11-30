package io.xlorey.FluxLoader.annotations;

import java.lang.annotation.*;
/**
 * Subscription to in-game and user events
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SubscribeEvent {
    /**
     * Event name
     * @return The default event name for the subscription. If not specified, an empty string is returned.
     */
    String eventName() default "";
}