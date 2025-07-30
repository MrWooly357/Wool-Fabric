package net.mrwooly357.wool.animation.interpolation.custom;

import net.mrwooly357.wool.animation.interpolation.Interpolation;

@FunctionalInterface
public interface AdvancedInterpolation extends Interpolation {


    float apply(float t, float a, float b, float previous, float next);

    @Override
    default float apply(float... arguments) {
        return apply(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4]);
    }
}
