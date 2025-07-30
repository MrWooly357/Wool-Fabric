package net.mrwooly357.wool.util.context.custom;

import net.mrwooly357.wool.util.context.Context;

/**
 * A {@link Context context} with a result value.
 * @param <V> the type of result value.
 * @since 1.0.0
 */
public class ResultContext<V> implements Context {

    /**
     * The {@link V value}.
     */
    protected V value;

    /**
     * Creates a new {@link ResultContext} with an initial value.
     * @param initialValue the initial value.
     */
    public ResultContext(V initialValue) {
        value = initialValue;
    }


    /**
     * Gets the result value of this {@link ResultContext context}.
     * @return the result value.
     */
    public V get() {
        return value;
    }
}
