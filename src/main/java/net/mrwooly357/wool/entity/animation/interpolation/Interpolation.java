package net.mrwooly357.wool.entity.animation.interpolation;

@FunctionalInterface
public interface Interpolation {


    float apply(float... arguments);
}
