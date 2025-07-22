package net.mrwooly357.wool.config.restriction.custom.range;

public class FloatRangeRestriction extends RangeRestriction<Float> {

    public FloatRangeRestriction(float min, float max) {
        super(min, max);
    }


    @Override
    public Float normalize(Float value) {
        float normalized;

        if (value < min) {
            normalized = min;
        } else if (value > max) {
            normalized = max;
        } else
            normalized = value;

        return normalized;
    }
}
