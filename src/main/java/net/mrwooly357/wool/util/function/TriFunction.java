package net.mrwooly357.wool.util.function;

@FunctionalInterface
public interface TriFunction<A, B, C, D> {


    D apply(A a, B b, C c);
}
