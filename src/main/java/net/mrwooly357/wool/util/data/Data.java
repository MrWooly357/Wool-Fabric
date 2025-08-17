package net.mrwooly357.wool.util.data;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.*;

public final class Data<T> {

    @Nullable
    private T value;

    private Data(@Nullable T initialValue) {
        value = initialValue;
    }


    public static <T> Data<T> empty() {
        return of(null);
    }

    public static <T> Data<T> of(@Nullable T initialValue) {
        return new Data<>(initialValue);
    }

    @Nullable
    public T get() {
        return value;
    }

    public T getIfNonNullOrElse(T other) {
        return isNonNull() ? value : other;
    }

    public <E extends Throwable> T getOrThrow(E throwable) throws E {
        if (isNonNull())
            return value;
        else
            throw throwable;
    }

    public void set(@Nullable T value) {
        this.value = value;
    }

    public void setIfNull(T value) {
        ifNull(() -> set(value));
    }

    public boolean isNull() {
        return value == null;
    }

    public boolean isNonNull() {
        return !isNull();
    }

    public void ifNull(Runnable function) {
        if (isNull())
            function.run();
    }

    public void ifNonNull(Consumer<? super T> action) {
        if (isNonNull())
            action.accept(value);
    }

    public void ifNullOrElse(Runnable function, Consumer<? super T> action) {
        if (isNull())
            function.run();
        else
            action.accept(value);
    }

    public void update(UnaryOperator<T> updater) {
        set(updater.apply(value));
    }

    public void updateIfNonNull(UnaryOperator<T> updater) {
        if (isNonNull())
            set(updater.apply(value));
    }

    public void clear() {
        value = null;
    }

    public Data<T> filter(Predicate<T> predicate) {
        return isNonNull() ? (predicate.test(value) ? this : empty()) : empty();
    }

    public Data<T> tap(Consumer<? super T> action) {
        ifNonNull(action);

        return this;
    }

    public <R> Data<R> transform(Function<T, Data<R>> transformer) {
        return isNonNull() ? transformer.apply(value) : empty();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Data<?> data && Objects.equals(value, data.value);
    }

    @Override
    public String toString() {
        return isNonNull() ? "Data[value: " + value + "]" : "Data[]";
    }
}
