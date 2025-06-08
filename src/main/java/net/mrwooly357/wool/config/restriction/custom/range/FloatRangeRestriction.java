package net.mrwooly357.wool.config.restriction.custom.range;

public class FloatRangeRestriction extends RangeRestriction<Float> {

    public FloatRangeRestriction(float bottom, float top) {
        super(bottom, top);
    }


    @Override
    public Float normalize(Float value) {
        float normalized;

        if (value < bottom) {
            normalized = bottom;
        } else if (value > top) {
            normalized = top;
        } else {
            normalized = value;
        }

        return normalized;
    }
}
