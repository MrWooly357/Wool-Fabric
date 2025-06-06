package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class FloatFieldType implements FieldType<Float> {

    public FloatFieldType() {}


    @Override
    public Float parse(String string) {
        return Float.parseFloat(string.trim());
    }

    @Override
    public String format(Float value) {
        return value.toString();
    }
}
