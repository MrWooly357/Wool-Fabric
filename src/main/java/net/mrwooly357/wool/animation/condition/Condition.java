package net.mrwooly357.wool.animation.condition;

import net.minecraft.entity.Entity;

/**
 * A true-false helper which evaluates whether something is present when trying to select an animation variant.
 * @see net.mrwooly357.wool.animation.condition.Conditions
 */
public interface Condition {

    /**
     * Evaluates the condition for a specific entity or its environment.
     * @param entity target entity.
     * @return the result of the test ({@code true} if condition is met, {@code false} otherwise).
     */
    boolean test(Entity entity);
}
