package net.mrwooly357.wool.config.restriction.custom.range;

public class ShortRangeRestriction extends RangeRestriction<Short> {

    public ShortRangeRestriction(short min, short max) {
        super(min, max);
    }


    @Override
    public Short normalize(Short value) {
        short normalized;

        if (value < min) {
            normalized = min;
        } else if (value > max) {
            normalized = max;
        } else
            normalized = value;

        return normalized;
    }
}
