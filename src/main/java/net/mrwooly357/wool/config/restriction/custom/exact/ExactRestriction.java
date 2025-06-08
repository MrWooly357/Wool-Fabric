package net.mrwooly357.wool.config.restriction.custom.exact;

import net.mrwooly357.wool.config.restriction.Restriction;

public abstract class ExactRestriction<T> implements Restriction<T> {

    protected final T required;

    protected ExactRestriction(T defaultValue) {
        this.required = defaultValue;
    }


    @Override
    public T normalize(T value) {
        return required;
    }

    @Override
    public String getComment() {
        return "Must be " + required + ".";
    }
}
