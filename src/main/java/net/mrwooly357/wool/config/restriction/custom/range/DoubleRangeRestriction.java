package net.mrwooly357.wool.config.restriction.custom.range;

public class DoubleRangeRestriction extends RangeRestriction<Double> {

    public DoubleRangeRestriction(double bottom, double top) {
        super(bottom, top);
    }


    @Override
    public Double normalize(Double value) {
        double normalized;

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
