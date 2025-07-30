package net.mrwooly357.wool.animation.interpolation.custom;

import net.mrwooly357.wool.animation.interpolation.Interpolation;

@FunctionalInterface
public interface SimpleInterpolation extends Interpolation {


    float apply(float t, float a, float b);

    @Override
    default float apply(float... arguments) {
        return apply(arguments[0], arguments[1], arguments[2]);
    }
}
