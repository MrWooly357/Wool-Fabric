package net.mrwooly357.wool.animation.interpolation;

import net.minecraft.util.math.MathHelper;

public class LinearInterpolation implements Interpolation {

    public LinearInterpolation() {}


    @Override
    public float apply(float delta) {
        return MathHelper.clamp(delta, 0, 1);
    }
}
