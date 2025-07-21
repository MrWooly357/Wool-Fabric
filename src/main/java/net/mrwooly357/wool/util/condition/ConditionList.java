package net.mrwooly357.wool.util.condition;

import com.google.common.collect.ImmutableList;

/**
 * An {@link ImmutableList immutable list} of {@link Condition conditions}.
 * @param <T> the type of value that {@link Condition conditions} test.
 * @since 1.0.0
 */
public final class ConditionList<T> {

    /**
     * All the {@link Condition conditions}.
     */
    private final ImmutableList<Condition<T>> conditions;

    /**
     * Creates a new {@link ConditionList}.
     * @param conditions the {@link Condition conditions}.
     */
    @SafeVarargs
    public ConditionList(Condition<T>... conditions) {
        this.conditions = ImmutableList.copyOf(conditions);
    }


    /**
     * Tests all {@link Condition conditions}.
     * @param object the {@link Object} to test.
     * @return {@code true} if all {@link Condition conditions} were tested successfully, otherwise {@code false}.
     */
    public boolean test(T object) {
        boolean valid = true;

        for (Condition<T> condition : conditions)
            valid = condition.test(object);

        return valid;
    }
}
