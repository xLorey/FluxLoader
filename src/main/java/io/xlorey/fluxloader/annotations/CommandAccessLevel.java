package io.xlorey.fluxloader.annotations;

import io.xlorey.fluxloader.enums.AccessLevel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author: Deknil
 * GitHub: <a href=https://github.com/Deknil>https://github.com/Deknil</a>
 * Date: 08.02.2024
 * Description: An annotation that indicates the required access level for a user.
 *              Users with a lower access level than specified will not have access to the functionality.
 * <p>FluxLoader © 2024. All rights reserved.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandAccessLevel {
    /**
     * Specifies the required access level.
     * @return The access level required for the functionality.
     *         The default is AccessLevel.NONE, meaning no specific access level is required.
     */
    AccessLevel accessLevel() default AccessLevel.NONE;
}