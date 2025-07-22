package net.mrwooly357.wool.config.restriction.custom.range;

import net.mrwooly357.wool.config.restriction.Restriction;

public abstract class RangeRestriction<T> implements Restriction<T> {

    protected final T min;
    protected final T max;

    protected RangeRestriction(T min, T max) {
        this.min = min;
        this.max = max;
    }


    @Override
    public String getComment() {
        return "Must be greater than or equal to " + min + " but less than or equal to " + max + "." ;
    }
}
