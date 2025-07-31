package net.mrwooly357.wool.util.misc;

import java.util.List;
import java.util.function.Predicate;

public final class PredicateList<T, P extends Predicate<T>> {

    private final List<P> predicates;

    @SafeVarargs
    public PredicateList(P... predicates) {
        this.predicates = List.of(predicates);
    }


    boolean test(T object) {
        for (P predicate : predicates) {

            if (!predicate.test(object))
                return false;
        }

        return true;
    }
}
