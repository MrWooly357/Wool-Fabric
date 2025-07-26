package net.mrwooly357.wool.util.position;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.random.Random;
import net.mrwooly357.wool.util.condition.ConditionList;
import net.mrwooly357.wool.util.condition.custom.PositionCondition;
import net.mrwooly357.wool.util.position.custom.Position3;

/**
 * Represents a position in space.
 * @param <T> the type of components.
 * @implNote components must be numbers!
 * @see Position3
 * @since 1.0.0
 */
public interface Position<T extends Number> {


    /**
     * Gets all components.
     * @return all components.
     */
    ImmutableList<T> getComponents();

    /**
     * Gets the {@link Position} whose components' values are zero.
     * @return the zero {@link Position}.
     */
    Position<T> getZero();

    /**
     * Gets the {@link Codec}.
     * @return the {@link Codec}.
     */
    Codec<? extends Position<T>> getCodec();

    /**
     * Gets the {@link PacketCodec}.
     * @return the {@link PacketCodec}.
     */
    PacketCodec<PacketByteBuf, ? extends Position<T>> getPacketCodec();

    /**
     * Makes a copy of this {@link Position position}.
     * @return a copy of this {@link Position position}.
     */
    Position<T> copy();

    /**
     * Adds a {@link Position}.
     * @param position the {@link Position} to be added.
     * @return the sum of this {@link Position position} and the one from parentheses.
     * @apiNote  by "adding a {@link Position position}" I mean adding all of its components' values to the corresponding ones in this position.
     * @implNote throw {@link Position#createAdditionException(Position, Position)} when the position can't be added due to its type.
     */
    Position<T> add(Position<?> position);

    /**
     * Creates an {@link IllegalArgumentException} to throw whenever a wrong type of {@link Position} is used in {@link Position#add(Position)}.
     * @param position the {@link Position}.
     * @param other the {@link Position} that was tried to be added.
     * @return an {@link IllegalArgumentException} to use in {@link Position#add(Position)}.
     */
    IllegalArgumentException createAdditionException(Position<T> position, Position<?> other);

    /**
     * Adds a {@link T value}.
     * @param value the {@link T value} to be added.
     * @return a {@link Position} whose components' values are sums of this position's components' values and the value from parentheses.
     * @param <N> the type of {@link Number} of the value.
     */
    <N extends Number> Position<T> add(N value);

    /**
     * Subtracts a {@link Position}.
     * @param position the {@link Position} to be subtracted.
     * @return the difference of this {@link Position position} and the one from parentheses.
     * @implNote by "subtracting a {@link Position position}" I mean subtracting all of its components' values to the corresponding ones in this position.
     */
    Position<T> subtract(Position<?> position);

    /**
     * Creates an {@link IllegalArgumentException} to throw whenever a wrong type of {@link Position} is used in {@link Position#subtract(Position)}.
     * @param position the {@link Position}.
     * @param other the {@link Position} that was tried to be subtracted.
     * @return an {@link IllegalArgumentException} to use in {@link Position#subtract(Position)}.
     */
    IllegalArgumentException createSubtractionException(Position<T> position, Position<?> other);

    /**
     * Subtracts a {@link T number}.
     * @param value the {@link T number} to be subtracted.
     * @return a {@link Position} whose components' values are differences of this position's components' values and the value from parentheses.
     * @param <N> the type of {@link Number} of the value.
     */
    <N extends Number> Position<T> subtract(N value);

    /**
     * Multiplies by a {@link Position}.
     * @param position the {@link Position} to multiply by.
     * @return the product of this position and the one from parentheses.
     * @implNote by "multiplying a {@link Position position}" I mean multiplying all of its components' values to the corresponding ones in this {@link Position position}.
     */
    Position<T> multiply(Position<?> position);

    /**
     * Creates an {@link IllegalArgumentException} to throw whenever a wrong type of {@link Position} is used in {@link Position#multiply(Position)}.
     * @param position the {@link Position}.
     * @param other the {@link Position} that was tried to be multiplied by.
     * @return an {@link IllegalArgumentException} to use in {@link Position#multiply(Position)}.
     */
    IllegalArgumentException createMultiplicationException(Position<T> position, Position<?> other);

    /**
     * Multiplies by a {@link T number}.
     * @param value the {@link T number} to be multiplied by.
     * @return a {@link Position} whose components' values are products of this position's components' values and the value from parentheses.
     * @param <N> the type of {@link Number} of the value.
     */
    <N extends Number> Position<T> multiply(N value);

    /**
     * Divides by a {@link Position}.
     * @param position the {@link Position} to divide by.
     * @return the quotient of this position and the one from parentheses.
     * @implNote by "dividing a {@link Position position}" I mean dividing all of its components' values to the corresponding ones in this position.
     */
    Position<T> divide(Position<?> position);

    /**
     * Creates an {@link IllegalArgumentException} to throw whenever a wrong type of {@link Position} is used in {@link Position#divide(Position)}.
     * @param position the {@link Position}.
     * @param other the {@link Position} that was tried to be divided by.
     * @return an {@link IllegalArgumentException} to use in {@link Position#divide(Position)}.
     */
    IllegalArgumentException createDivisionException(Position<T> position, Position<?> other);

    /**
     * Divides by a {@link T number}.
     * @param value the {@link T number} to be divided by.
     * @return a {@link Position} whose components' values are quotients of this position's components' values and the value from parentheses.
     * @param <N> the type of {@link Number} of the value.
     */
    <N extends Number> Position<T> divide(N value);

    /**
     * Tests if this {@link Position position} is equal to another one.
     * @param position the other {@link Position position}.
     * @return if {@link Position position} is equal to this {@link Position position}.
     */
    boolean equal(Position<?> position);

    /**
     * Tests a {@link PositionCondition condition}.
     * @param condition the {@link PositionCondition}.
     * @return true if all requirements are met.
     * @apiNote use {@link Position#testList(ConditionList)} to test a few {@link PositionCondition condition}s.
     */
    default boolean test(PositionCondition condition) {
        return condition.test(this);
    }

    /**
     * Tests a {@link ConditionList} with {@link PositionCondition condition}s.
     * @param conditions a {@link ConditionList} which stores {@link PositionCondition condition}s.
     * @return if all {@link PositionCondition}s were tested successfully.
     * @apiNote use {@link Position#test(PositionCondition)} to test a single {@link PositionCondition condition}.
     */
    default boolean testList(ConditionList<Position<T>> conditions) {
        return conditions.test(this);
    }

    /**
     * Finds a random {@link Position} in radius. The current {@link Position position} is used as a centre.
     * @param radius the radius.
     * @return a random {@link Position} in radius.
     * @apiNote to use custom {@link Random} instead of {@link Random#create()} use {@link Position#randomInRadius(Random, Number)}.
     */
    Position<T> randomInRadius(T radius);

    /**
     * Finds a random {@link Position} in radius. The current {@link Position position} is used as a centre.
     * @param random the {@link Random}.
     * @param radius the radius.
     * @return a random {@link Position} in radius.
     * @apiNote you can use {@link Position#randomInRadius(Number)} in case you want to avoid using custom {@link Random}.
     */
    Position<T> randomInRadius(Random random, T radius);

    /**
     * Calculates the distance between this {@link Position position} and another one.
     * @param position the {@link Position}.
     * @return the distance between this {@link Position position} and the other one.
     */
    T distanceTo(Position<T> position);

    /**
     * Creates an {@link IllegalArgumentException} to throw whenever a wrong type of {@link Position} is used in {@link Position#distanceTo(Position)}.
     * @param position the {@link Position}.
     * @param other the other {@link Position}.
     * @return an {@link IllegalArgumentException} to use in {@link Position#distanceTo(Position)}.
     */
    IllegalArgumentException createDistanceToException(Position<T> position, Position<?> other);

    /**
     * Creates a {@link Mutable mutable} copy of this {@link Position position}.
     * @return a {@link Mutable mutable} copy of this {@link Position position}.
     * @apiNote use {@link Mutable#toImmutable()} to get an {@link Position immutable} copy.
     */
    Mutable<T> toMutable();


    /**
     * An extended {@link Position} whose components' values can be set to certain values.
     * @param <T> the type of components.
     * @implNote components must be numbers!
     * @see Position3.Mutable
     */
    interface Mutable<T extends Number> extends Position<T> {


        /**
         * Sets all components' values.
         * @param value the new value for all components.
         */
        void setComponents(T value);

        /**
         * Creates an {@link Position immutable} copy of this {@link Position position}.
         * @return an {@link Position immutable} copy of this {@link Position position}.
         */
        Position<T> toImmutable();
    }
}
