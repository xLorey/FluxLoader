package io.xlorey.fluxloader.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 08.02.2024
 * Description: An annotation that includes the name of the command (without slashes or other prefixes)
 * <p>FluxLoader © 2024. All rights reserved.</p>
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