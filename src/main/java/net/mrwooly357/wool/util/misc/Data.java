package net.mrwooly357.wool.util.misc;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class Data<T> {

    @Nullable
    private T value;

    public Data(@Nullable T initialValue) {
        value = initialValue;
    }


    @Nullable
    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Data<?> data && Objects.equals(value, data.value);
    }

    @Override
    public String toString() {
        return "Data[Value: " + value + "]";
    }
}
