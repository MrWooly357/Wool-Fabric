package net.mrwooly357.wool.config.restriction.custom.range;

public class LongRangeRestriction extends RangeRestriction<Long> {

    public LongRangeRestriction(long min, long max) {
        super(min, max);
    }


    @Override
    public Long normalize(Long value) {
        long normalized;

        if (value < min) {
            normalized = min;
        } else if (value > max) {
            normalized = max;
        } else
            normalized = value;

        return normalized;
    }
}
