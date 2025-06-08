package net.mrwooly357.wool.config.restriction.custom.range;

public class ShortRangeRestriction extends RangeRestriction<Short> {

    public ShortRangeRestriction(short bottom, short top) {
        super(bottom, top);
    }


    @Override
    public Short normalize(Short value) {
        short normalized;

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
