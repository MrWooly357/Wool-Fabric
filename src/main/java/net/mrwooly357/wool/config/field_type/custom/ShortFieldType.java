package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class ShortFieldType implements FieldType<Short> {

    public ShortFieldType() {}


    @Override
    public Short parse(String string) {
        return Short.parseShort(string.trim());
    }

    @Override
    public String format(Short value) {
        return value.toString();
    }
}
