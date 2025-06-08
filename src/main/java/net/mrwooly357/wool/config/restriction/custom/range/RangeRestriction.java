package net.mrwooly357.wool.config.restriction.custom.range;

import net.mrwooly357.wool.config.restriction.Restriction;

public abstract class RangeRestriction<T> implements Restriction<T> {

    protected final T bottom;
    protected final T top;

    protected RangeRestriction(T bottom, T top) {
        this.bottom = bottom;
        this.top = top;
    }


    @Override
    public String getComment() {
        return "Must be greater than or equal to " + bottom + " but less than or equal to " + top + "." ;
    }
}
