package net.mrwooly357.wool.util.condition;

import java.util.function.Predicate;

/**
 * A wrapper like extension of {@link Predicate} which is used in {@link ConditionList}.
 * @param <T> the type of input.
 * @since 1.0.0
 */
@FunctionalInterface
public interface Condition<T> extends Predicate<T> {


    /**
     * Tests the {@link T object}.
     * @param object the input argument.
     * @return {@code true} if requirement is met, otherwise {@code false}.
     */
    @Override
    boolean test(T object);
}
