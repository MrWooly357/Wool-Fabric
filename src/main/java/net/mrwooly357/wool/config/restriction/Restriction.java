package net.mrwooly357.wool.config.restriction;

public interface Restriction<T> {


    T normalize(T value);

    String getComment();
}
