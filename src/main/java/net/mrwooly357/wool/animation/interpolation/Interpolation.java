package net.mrwooly357.wool.animation.interpolation;

import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

/**
 * Provides interpolation functions for animations.
 * @see Interpolations
 */
@FunctionalInterface
public interface Interpolation {

    /**
     * Applies interpolation to a normalized progress value (0 - 1).
     * @param delta normalized progress (0 - 1).
     * @return interpolated value.
     */
    float apply(float delta);

    /**
     * Helper method to apply interpolations to value ranges.
     * @param start start value.
     * @param end end value.
     * @param delta normalized progress (0 - 1).
     * @param interpolation interpolation function.
     * @return interpolated value between {@code start} and {@code end}.
     */
    static float interpolate(float start, float end, float delta, Interpolation interpolation) {
        return MathHelper.lerp(interpolation.apply(delta), start, end);
    }

    /**
     * Helper method for 3D vector interpolation.
     * @param start start vector.
     * @param end end vector.
     * @param delta normalized progress (0 - 1).
     * @param interpolation interpolation function.
     * @return interpolated vector.
     */
    static Vector3f interpolate(Vector3f start, Vector3f end, float delta, Interpolation interpolation) {
        float a = interpolation.apply(delta);

        return new Vector3f(MathHelper.lerp(a, start.x, end.x), MathHelper.lerp(a, start.y, end.y), MathHelper.lerp(a, start.z, end.z));
    }
}
