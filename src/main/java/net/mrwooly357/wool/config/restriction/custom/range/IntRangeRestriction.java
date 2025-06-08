package net.mrwooly357.wool.config.restriction.custom.range;

public class IntRangeRestriction extends RangeRestriction<Integer> {

    public IntRangeRestriction(int bottom, int top) {
        super(bottom, top);
    }


    @Override
    public Integer normalize(Integer value) {
        int normalized;

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
