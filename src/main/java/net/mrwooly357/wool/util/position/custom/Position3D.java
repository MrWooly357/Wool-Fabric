package net.mrwooly357.wool.util.position.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.util.WoolUtil;
import net.mrwooly357.wool.util.position.Position;

import java.util.Objects;

/**
 * Represents a position in 3D space with three double-precision components.
 *
 * @version 1.0.0
 * @since 1.0.0
 *
 * @see Position3
 */
public final class Position3D extends Position3<Double> {

    /**
     * The zero {@link Position3D}.
     */
    public static final Position3D ZERO = new Position3D(0.0, 0.0, 0.0);
    /**
     * The {@link Codec}.
     */
    public static final Codec<Position3D> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("x").forGetter(Position3D::getX),
            Codec.DOUBLE.fieldOf("y").forGetter(Position3D::getY),
            Codec.DOUBLE.fieldOf("z").forGetter(Position3D::getZ)
            ).apply(instance, Position3D::new)
    );
    /**
     * The {@link PacketCodec}.
     */
    public static final PacketCodec<PacketByteBuf, Position3D> PACKET_CODEC = new PacketCodec<>() {


        @Override
        public Position3D decode(PacketByteBuf buf) {
            return new Position3D(buf.readDouble(), buf.readDouble(), buf.readDouble());
        }

        @Override
        public void encode(PacketByteBuf buf, Position3D position3D) {
            buf.writeDouble(position3D.x);
            buf.writeDouble(position3D.y);
            buf.writeDouble(position3D.z);
        }
    };

    /**
     * Creates a new {@link Position3D} using x, y and z components.
     * @param x the {@link Position3#x} component.
     * @param y the {@link Position3#y} component.
     * @param z the {@link Position3#z} component.
     * @see Position3#Position3(Number, Number, Number)
     */
    private Position3D(double x, double y, double z) {
        super(x, y, z);
    }


    public static Position3D of(double x, double y, double z) {
        return new Position3D(x, y, z);
    }

    public static Position3D of(BlockPos pos) {
        return of(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Position3D of(BlockEntity blockEntity) {
        return of(blockEntity.getPos());
    }

    public static Position3D of(Entity entity) {
        return of(entity.getX(), entity.getY(), entity.getZ());
    }

    /**
     * An implementation of {@link Position#getZero()} which gets the zero {@link Position3D}.
     * @return the zero {@link Position3D}.
     */
    @Override
    public Position<Double> getZero() {
        return ZERO;
    }

    /**
     * An implementation of {@link Position#getCodec()} which gets the {@link Position3D#CODEC}.
     * @return the {@link Position3D#CODEC}.
     */
    @Override
    public Codec<Position3D> getCodec() {
        return CODEC;
    }

    /**
     * An implementation of {@link Position#getPacketCodec()} which gets the {@link Position3D#PACKET_CODEC}.
     * @return the {@link Position3D#PACKET_CODEC}.
     */
    @Override
    public PacketCodec<PacketByteBuf, Position3D> getPacketCodec() {
        return PACKET_CODEC;
    }

    /**
     * An implementation of {@link Position#copy()} which makes a copy of this {@link Position3D position}.
     * @return a copy of this position.
     */
    @Override
    public Position<Double> copy() {
        return new Position3D(x, y, z);
    }

    /**
     * An implementation of {@link Position#add(Position)} which adds a {@link Position position} to current one.
     * @param position the {@link Position} to be added.
     * @return the sum of this and another position.
     */
    @Override
    public Position<Double> add(Position<?> position) {
        if (position instanceof Position3<?> position3) {
            return new Position3D(x + (double) position3.x, y + (double) position3.y, z + (double) position3.z);
        } else if (WoolConfig.enableDeveloperMode) {
            throw createAdditionException(this, position);
        } else
            return getZero();
    }

    /**
     * An implementation of {@link Position#add(Number)} which adds a value to this {@link Position3D position}.
     * @param value the {@link Number value} to be added.
     * @return the sum of this position and the value.
     * @param <N> the type of {@link Number}.
     */
    @Override
    public <N extends Number> Position<Double> add(N value) {
        return add(new Position3D((double) value, (double) value, (double) value));
    }

    /**
     * An implementation of {@link Position#subtract(Position)} which subtracts a {@link Position position} from current one.
     * @param position the {@link Position} to be subtracted.
     * @return the difference of this and another position.
     */
    @Override
    public Position<Double> subtract(Position<?> position) {
        if (position instanceof Position3<?> position3) {
            return new Position3D(x - (double) position3.x, y - (double) position3.y, z - (double) position3.z);
        } else if (WoolConfig.enableDeveloperMode) {
            throw createSubtractionException(this, position);
        } else
            return getZero();
    }

    /**
     * An implementation of {@link Position#subtract(Number)} which subtracts a value from this {@link Position3D position}.
     * @param value the {@link Number value} to be subtracted.
     * @return the difference between this position and the value.
     * @param <N> the type of {@link Number}.
     */
    @Override
    public <N extends Number> Position<Double> subtract(N value) {
        return subtract(new Position3D((double) value, (double) value, (double) value));
    }

    /**
     * An implementation of {@link Position#multiply(Position)} which multiplies this {@link Position3D position} by another one.
     * @param position the {@link Position} to multiply by.
     * @return the product of this and another position.
     */
    @Override
    public Position<Double> multiply(Position<?> position) {
        if (position instanceof Position3<?> position3) {
            return new Position3D(x * (double) position3.x, y * (double) position3.y, z * (double) position3.z);
        } else if (WoolConfig.enableDeveloperMode) {
            throw createMultiplicationException(this, position);
        } else
            return getZero();
    }

    /**
     * An implementation of {@link Position#multiply(Number)} which multiplies this {@link Position3D position} by a value.
     * @param value the {@link Number value} to multiply by.
     * @return the product of this position and the value.
     * @param <N> the type of {@link Number}.
     */
    @Override
    public <N extends Number> Position<Double> multiply(N value) {
        return multiply(new Position3D((double) value, (double) value, (double) value));
    }

    /**
     * An implementation of {@link Position#divide(Position)} which divides this {@link Position3D position} by another one.
     * @param position the {@link Position} to divide by.
     * @return the quotient of this and another position.
     */
    @Override
    public Position<Double> divide(Position<?> position) {
        if (position instanceof Position3<?> position3) {
            double x = (double) position3.x;
            double y = (double) position3.y;
            double z = (double) position3.z;

            if (x != 0.0 && y != 0.0 && z != 0.0) {
                return new Position3D(this.x / x, this.y / y, this.z / z);
            } else
                throw new IllegalArgumentException("One of components' values is 0.0. Can't divide by 0.0!" + this + ", " + position3);
        } else if (WoolConfig.enableDeveloperMode) {
            throw createDivisionException(this, position);
        } else
            return getZero();
    }

    /**
     * An implementation of {@link Position#divide(Number)} which divides this {@link Position3D position} by a value.
     * @param value the {@link Number value} to divide by.
     * @return the quotient of this position and the value.
     * @param <N> the type of {@link Number}.
     */
    @Override
    public <N extends Number> Position<Double> divide(N value) {
        return divide(new Position3D((double) value, (double) value, (double) value));
    }

    /**
     * An implementation of {@link Position#equal(Position)} which checks if {@link Position position}s are equal to each-other.
     * @param position the other {@link Position position}.
     * @return if {@link Position position}s are equal to each-other.
     */
    @Override
    public boolean equal(Position<?> position) {
        return position instanceof Position3D position3D && Objects.equals(position3D.x, x) && Objects.equals(position3D.y, y) && Objects.equals(position3D.z, z);
    }

    /**
     * An implementation of {@link Position#randomInRadius(Number)} which finds a random {@link Position3D position} in certain radius around the current one.
     * @param radius the radius.
     * @return a random {@link Position position} in radius around the current one.
     */
    @Override
    public Position<Double> randomInRadius(Double radius) {
        return randomInRadius(Random.create(), radius);
    }

    /**
     * An implementation of {@link Position#randomInRadius(Random, Number)} which finds a random {@link Position3D position} in certain radius around the current one.
     * @param random the {@link Random}.
     * @param radius the radius.
     * @return a random {@link Position position} in certain radius around the current one.
     */
    @Override
    public Position<Double> randomInRadius(Random random, Double radius) {
        return new Position3D(x + MathHelper.nextDouble(random, -radius, radius), y + MathHelper.nextDouble(random, -radius, radius), z + MathHelper.nextDouble(random, -radius, radius));
    }

    /**
     * An implementation of {@link Position#distanceTo(Position)} which calculates the distance between this {@link Position3D position} and another one.
     * @param position the {@link Position}.
     * @return the distance between this {@link Position3D position} and another one.
     */
    @Override
    public Double distanceTo(Position<Double> position) {
        if (position instanceof Position3<?> position3) {
            return WoolUtil.getDistanceBetween(x, y, z, (double) position3.x, (double) position3.y, (double) position3.z);
        } else if (WoolConfig.enableDeveloperMode) {
            throw createDistanceToException(this, position);
        } else
            return 0.0;
    }

    /**
     * An implementation of {@link Position#toNbt()}.
     */
    @Override
    public NbtCompound toNbt() {
        NbtCompound nbt = super.toNbt();

        nbt.putDouble(X_KEY, x);
        nbt.putDouble(Y_KEY, y);
        nbt.putDouble(Z_KEY, z);

        return nbt;
    }

    public static Position3D fromNbt(NbtCompound nbt) {
        double x = nbt.getDouble(X_KEY);
        double y = nbt.getDouble(Y_KEY);
        double z = nbt.getDouble(Z_KEY);

        return of(x, y, z);
    }

    /**
     * Centres the current {@link Position3D position}.
     * @return a centred {@link Position3D}.
     */
    public Position3D centre() {
        return new Position3D((double) ((int) ((double) x)) + 0.5, (double) ((int) ((double) y)) + 0.5, (double) ((int) ((double) z)) + 0.5);
    }
}
