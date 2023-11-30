package io.xlorey.FluxLoader.annotations;

import java.lang.annotation.*;

/**
 * An annotation that provides the text to be output to chat when the command is used
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CommandChatReturn {
    /**
     * Text that will be displayed when using the command
     * @return Message text
     */
    String text() default "";
}
