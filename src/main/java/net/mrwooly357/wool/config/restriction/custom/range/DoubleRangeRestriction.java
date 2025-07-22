package net.mrwooly357.wool.config.restriction.custom.range;

public class DoubleRangeRestriction extends RangeRestriction<Double> {

    public DoubleRangeRestriction(double min, double max) {
        super(min, max);
    }


    @Override
    public Double normalize(Double value) {
        double normalized;

        if (value < min) {
            normalized = min;
        } else if (value > max) {
            normalized = max;
        } else
            normalized = value;

        return normalized;
    }
}
