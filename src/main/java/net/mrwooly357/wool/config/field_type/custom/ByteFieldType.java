package net.mrwooly357.wool.config.field_type.custom;

import net.mrwooly357.wool.config.field_type.FieldType;

public class ByteFieldType implements FieldType<Byte> {

    public ByteFieldType() {}


    @Override
    public Byte parse(String string) {
        return Byte.parseByte(string.trim());
    }

    @Override
    public String format(Byte value) {
        return value.toString();
    }
}
