package net.mrwooly357.wool.animation.interpolation;

import net.minecraft.util.math.MathHelper;

public class EaseOutInterpolation implements Interpolation {

    public EaseOutInterpolation() {}


    @Override
    public float apply(float delta) {
        float a = MathHelper.clamp(delta, 0, 1);

        return 1 - (1 - a) * (1 - a);
    }
}
