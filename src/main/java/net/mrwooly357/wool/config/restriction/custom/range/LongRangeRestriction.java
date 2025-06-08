package net.mrwooly357.wool.config.restriction.custom.range;

public class LongRangeRestriction extends RangeRestriction<Long> {

    public LongRangeRestriction(long bottom, long top) {
        super(bottom, top);
    }


    @Override
    public Long normalize(Long value) {
        long normalized;

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
