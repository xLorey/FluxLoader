package io.xlorey.FluxLoader.annotations;

import java.lang.annotation.*;

/**
 * An annotation that includes the name of the command (without slashes or other prefixes)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CommandName {
    /**
     * The name of the command.
     * @return The name of the command without slashes or prefixes.
     */
    String command();
}
