package net.mrwooly357.wool.animation.interpolation;

import net.minecraft.util.math.MathHelper;

public class EaseInInterpolation implements Interpolation {

    public EaseInInterpolation() {}


    @Override
    public float apply(float delta) {
        float a = MathHelper.clamp(delta, 0, 1);

        return a * a;
    }
}
