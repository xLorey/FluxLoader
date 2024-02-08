package io.xlorey.fluxloader.annotations;

import io.xlorey.fluxloader.enums.CommandScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 08.02.2024
 * Description: An annotation to specify the scope where a command can be executed.
 * <p>FluxLoader © 2024. All rights reserved.</p>
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