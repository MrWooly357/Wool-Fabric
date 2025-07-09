package net.mrwooly357.wool.util.condition;

import java.util.function.Predicate;

@FunctionalInterface
public interface Condition<T> extends Predicate<T> {


    @Override
    boolean test(T object);
}
