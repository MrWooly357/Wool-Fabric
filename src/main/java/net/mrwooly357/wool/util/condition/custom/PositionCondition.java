package net.mrwooly357.wool.util.condition.custom;

import net.mrwooly357.wool.util.condition.Condition;
import net.mrwooly357.wool.util.position.Position;

/**
 * A {@link Condition condition} for {@link Position position}.
 * @since 1.0.0
 */
@FunctionalInterface
public interface PositionCondition extends Condition<Position<?>> {


    /**
     * Tests the {@link Position position}.
     * @param position the {@link Position}.
     * @return {@code true} if requirement is met, otherwise {@code false}.
     */
    @Override
    boolean test(Position<?> position);
}
