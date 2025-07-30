package net.mrwooly357.wool.config.field_type;

public interface FieldType<T> {


    T parse(String string);

    String format(T value);
}
