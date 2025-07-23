package net.mrwooly357.wool.util.position;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.math.BlockPos;

/**
 * Represents a position in 3D space with three components.
 * @param <T> the type of components.
 * @see Position
 * @see Position3D
 * @since 1.0.0
 */
public abstract class Position3<T extends Number> implements Position<T> {

    /**
     * The x component.
     */
    protected T x;
    /**
     * The y component.
     */
    protected T y;
    /**
     * The z component.
     */
    protected T z;

    /**
     * The message used in {@link Position3#add(Position)}.
     */
    String ADDITION_EXCEPTION_MESSAGE = "Can't add a non instance of Position3 to an instance of Position3! ";
    /**
     * The message used in {@link Position3#subtract(Position)}.
     */
    String SUBTRACTION_EXCEPTION_MESSAGE = "Can't subtract a non instance of Position3 to an instance of Position3! ";
    /**
     * The message used in {@link Position3#multiply(Position)}.
     */
    String MULTIPLICATION_EXCEPTION_MESSAGE = "Can't multiply an instance of Position3 by a non instance of Position3! ";
    /**
     * The message used in {@link Position3#divide(Position)}.
     */
    String DIVISION_EXCEPTION_MESSAGE = "Can't divide an instance of Position3 by a non instance of Position3! ";
    /**
     * The message used in {@link Position3#createDistanceToException(Position, Position)}.
     */
    String DISTANCE_TO_EXCEPTION_MESSAGE = "Can't use a non instance of Position3 in distanceTo in Position3! ";

    /**
     * Creates a new {@link Position3} using x, y and z components.
     * @param x the {@link Position3#x} component.
     * @param y the {@link Position3#y} component.
     * @param z the {@link Position3#z} component.
     */
    public Position3(T x, T y, T z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /**
     * An implementation of {@link Position#getComponents()} which gets an {@link ImmutableList} of {@link Position3#x}, {@link Position3#y} and {@link Position3#z}.
     * @return an {@link ImmutableList} of {@link Position3#x}, {@link Position3#y} and {@link Position3#z}.
     */
    @Override
    public ImmutableList<T> getComponents() {
        return ImmutableList.of(x, y, z);
    }

    /**
     * Gets the {@link Position3#x} component.
     * @return the {@link Position3#x}.
     * @see Position3#getY()
     * @see Position3#getZ()
     */
    public T getX() {
        return x;
    }

    /**
     * Gets the {@link Position3#y} component.
     * @return the {@link Position3#y}.
     * @see Position3#getX()
     * @see Position3#getZ()
     */
    public T getY() {
        return y;
    }

    /**
     * Gets the {@link Position3#z} component.
     * @return the {@link Position3#z}.
     * @see Position3#getX()
     * @see Position3#getY()
     */
    public T getZ() {
        return z;
    }

    /**
     * An implementation of {@link Position#createAdditionException(Position, Position)} which creates an {@link IllegalArgumentException} with the message being {@link Position3#ADDITION_EXCEPTION_MESSAGE}.
     * @param position the {@link Position}.
     * @param other the {@link Position} that was tried to be added.
     * @return an {@link IllegalArgumentException} with the message being {@link Position3#ADDITION_EXCEPTION_MESSAGE}.
     */
    @Override
    public IllegalArgumentException createAdditionException(Position<T> position, Position<?> other) {
        return new IllegalArgumentException(ADDITION_EXCEPTION_MESSAGE + position + ", " + other);
    }

    /**
     * An implementation of {@link Position#createSubtractionException(Position, Position)} which creates an {@link IllegalArgumentException} with the message being {@link Position3#SUBTRACTION_EXCEPTION_MESSAGE}.
     * @param position the {@link Position}.
     * @param other the {@link Position} that was tried to be subtracted.
     * @return an {@link IllegalArgumentException} with the message being {@link Position3#SUBTRACTION_EXCEPTION_MESSAGE}.
     */
    @Override
    public IllegalArgumentException createSubtractionException(Position<T> position, Position<?> other) {
        return new IllegalArgumentException(SUBTRACTION_EXCEPTION_MESSAGE + position + ", " + other);
    }

    /**
     * An implementation of {@link Position#createMultiplicationException(Position, Position)} which creates an {@link IllegalArgumentException} with the message being {@link Position3#MULTIPLICATION_EXCEPTION_MESSAGE}.
     * @param position the {@link Position}.
     * @param other the {@link Position} that was tried to be multiplied by.
     * @return an {@link IllegalArgumentException} with the message being {@link Position3#MULTIPLICATION_EXCEPTION_MESSAGE}.
     */
    @Override
    public IllegalArgumentException createMultiplicationException(Position<T> position, Position<?> other) {
        return new IllegalArgumentException(MULTIPLICATION_EXCEPTION_MESSAGE + position + ", " + other);
    }

    /**
     * An implementation of {@link Position#createDivisionException(Position, Position)} which creates an {@link IllegalArgumentException} with the message being {@link Position3#DIVISION_EXCEPTION_MESSAGE}.
     * @param position the {@link Position}.
     * @param other the {@link Position} that was tried to be divided by.
     * @return an {@link IllegalArgumentException} with the message being {@link Position3#DIVISION_EXCEPTION_MESSAGE}.
     */
    @Override
    public IllegalArgumentException createDivisionException(Position<T> position, Position<?> other) {
        return new IllegalArgumentException(DIVISION_EXCEPTION_MESSAGE + position + ", " + other);
    }

    /**
     * An implementation of {@link Position#createDistanceToException(Position, Position)} which creates an {@link IllegalArgumentException} with the message being {@link Position3#DISTANCE_TO_EXCEPTION_MESSAGE}.
     * @param position the {@link Position}.
     * @param other the other {@link Position}.
     * @return an {@link IllegalArgumentException} with the message being {@link Position3#DISTANCE_TO_EXCEPTION_MESSAGE}.
     */
    @Override
    public IllegalArgumentException createDistanceToException(Position<T> position, Position<?> other) {
        return new IllegalArgumentException(DISTANCE_TO_EXCEPTION_MESSAGE + position + ", " + other);
    }

    /**
     * Converts this {@link Position position} to a {@link BlockPos}.
     * @return a {@link BlockPos} whose x, y and z values are equal to {@link Position3#x}, {@link Position3#y} and {@link Position3#z} respectively.
     * @apiNote {@link Position3#x}, {@link Position3#y} and {@link Position3#z} are cast to {@link Integer}s when converting and lose their precision!
     * Use {@link Position3#toBlockPosRounded()} for {@link Position3#x}, {@link Position3#y} and {@link Position3#z} to be rounded.
     */
    public BlockPos toBlockPos() {
        return new BlockPos(x.intValue(), y.intValue(), z.intValue());
    }

    /**
     * Converts this {@link Position position} to a {@link BlockPos}.
     * @return a {@link BlockPos} whose x, y and z values are equal to {@link Position3#x}, {@link Position3#y} and {@link Position3#z} respectively.
     * @apiNote {@link Position3#x}, {@link Position3#y} and {@link Position3#z} are rounded when converting. If you want straight up casting to {@link Integer}s, use {@link Position3#toBlockPos()}.
     */
    public BlockPos toBlockPosRounded() {
        return new BlockPos(Math.round(x.floatValue()), Math.round(y.floatValue()), Math.round(z.floatValue()));
    }


    /**
     * An extended {@link Position.Mutable}.
     * @param <T> the type of components.
     */
    public interface Mutable<T extends Number> extends Position.Mutable<T> {


        /**
         * An implementation of {@link Position.Mutable#setComponents(Number)}. Sets all components' values to the one in parentheses.
         * @param value the new value for all components.
         */
        @Override
        default void setComponents(T value) {
            setX(value);
            setY(value);
            setZ(value);
        }

        /**
         * Sets the {@link Position3#x}.
         * @param value the new value.
         * @return this {@link Position3 position} with new {@link Position3#x}.
         */
        Position3.Mutable<T> setX(T value);

        /**
         * Sets the {@link Position3#y}.
         * @param value the new value.
         * @return this {@link Position3 position} with new {@link Position3#y}.
         */
        Position3.Mutable<T> setY(T value);

        /**
         * Sets the {@link Position3#z}.
         * @param value the new value.
         * @return this {@link Position3 position} with new {@link Position3#z}.
         */
        Position3.Mutable<T> setZ(T value);
    }
}
