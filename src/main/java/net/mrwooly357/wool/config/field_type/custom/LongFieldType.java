package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class LongFieldType implements FieldType<Long> {

    public LongFieldType() {}


    @Override
    public Long parse(String string) {
        return Long.parseLong(string.trim());
    }

    @Override
    public String format(Long value) {
        return value.toString();
    }
}
