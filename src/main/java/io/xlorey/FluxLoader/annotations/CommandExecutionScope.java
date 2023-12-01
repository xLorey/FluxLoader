package io.xlorey.FluxLoader.annotations;

import io.xlorey.FluxLoader.enums.CommandScope;

import java.lang.annotation.*;

/**
 * An annotation to specify the scope where a command can be executed.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CommandExecutionScope {
    /**
     * The scope of the command.
     * @return The specified command scope.
     */
    CommandScope scope();
}