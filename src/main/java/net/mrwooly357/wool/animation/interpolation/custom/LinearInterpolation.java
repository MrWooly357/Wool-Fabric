package net.mrwooly357.wool.animation.interpolation.custom;

import net.minecraft.util.math.MathHelper;
import net.mrwooly357.wool.animation.interpolation.Interpolation;

public class LinearInterpolation implements Interpolation {

    public LinearInterpolation() {}


    @Override
    public float apply(float delta) {
        return MathHelper.clamp(delta, 0, 1);
    }
}
