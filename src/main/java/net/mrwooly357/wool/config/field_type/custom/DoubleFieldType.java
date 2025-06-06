package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class DoubleFieldType implements FieldType<Double> {

    public DoubleFieldType() {}


    @Override
    public Double parse(String string) {
        return Double.parseDouble(string.trim());
    }

    @Override
    public String format(Double value) {
        return value.toString();
    }
}
