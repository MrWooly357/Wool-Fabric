package net.mrwooly357.wool.config.restriction.custom.range;

public class IntRangeRestriction extends RangeRestriction<Integer> {

    public IntRangeRestriction(int min, int max) {
        super(min, max);
    }


    @Override
    public Integer normalize(Integer value) {
        int normalized;

        if (value < min) {
            normalized = min;
        } else if (value > max) {
            normalized = max;
        } else
            normalized = value;

        return normalized;
    }
}
