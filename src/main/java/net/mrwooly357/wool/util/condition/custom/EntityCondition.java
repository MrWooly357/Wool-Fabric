package net.mrwooly357.wool.util.condition.custom;

import net.minecraft.entity.Entity;
import net.mrwooly357.wool.util.condition.Condition;

/**
 * A {@link Condition condition} for {@link Entity entity}.
 * @since 1.0.0
 */
@FunctionalInterface
public interface EntityCondition extends Condition<Entity> {


    /**
     * Tests the {@link Entity entity}.
     * @param entity the {@link Entity}.
     * @return {@code true} if requirement is met, otherwise {@code false}.
     */
    @Override
    boolean test(Entity entity);
}
