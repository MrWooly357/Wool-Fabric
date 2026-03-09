package net.mrwooly357.wool.util.predicate;

@FunctionalInterface
public interface TetraPredicate<A, B, C, D> {


    boolean test(A a, B b, C c, D d);

    default TetraPredicate<A, B, C, D> and(TetraPredicate<? super A, ? super B, ? super C, ? super D> other) {
        return (a, b, c, d) -> test(a, b, c, d) && other.test(a, b, c, d);
    }

    default TetraPredicate<A, B, C, D> negate() {
        return (a, b, c, d) -> !test(a, b, c, d);
    }

    default TetraPredicate<A, B, C, D> or(TetraPredicate<? super A, ? super B, ? super C, ? super D> other) {
        return (a, b, c, d) -> test(a, b, c, d) || other.test(a, b, c, d);
    }
}
