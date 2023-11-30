package io.xlorey.FluxLoader.annotations;

import java.lang.annotation.*;

/**
 * Annotation - a flag indicating that injection occurred in this file
 */
@Target({ElementType.METHOD})
public @interface Injected {
}
