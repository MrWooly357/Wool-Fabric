package net.mrwooly357.wool.util.context.custom;

import net.mrwooly357.wool.util.context.Context;

/**
 * Represents a result context of something. Usually it's a group of {@link Object objects}.
 * @param <V> the type of final value.
 * @since 1.0.0
 */
public interface ResultContext<V> extends Context {


    /**
     * Gets the final value of this {@link ResultContext context}.
     * @return the final value.
     */
    V get();
}
