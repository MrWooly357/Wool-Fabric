package net.mrwooly357.wool.util.unsafe;

import java.lang.annotation.*;

/**
 * Used to mark classes that use {@link UnsafeLookup}. It's primarily used for safety.
 * @apiNote methods that provide access to instances of {@link UnsafeLookup} should check for the presence of this annotation on users.
 * @implNote methods that instantiate {@link UnsafeLookup} should check if the class is annotated as an {@link UnsafeUser}.
 * @since 0.0.1-alpha-build-1.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UnsafeUser {}
