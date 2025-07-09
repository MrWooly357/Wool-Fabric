package net.mrwooly357.wool.util.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ConditionList<T> {

    private final List<Condition<T>> conditions;

    @SafeVarargs
    public ConditionList(Condition<T>... conditions) {
        this.conditions = new ArrayList<>();

        this.conditions.addAll(Arrays.asList(conditions));
    }


    public List<Condition<T>> getConditions() {
        return conditions;
    }

    public boolean test(T object) {
        boolean bl = true;

        for (Condition<T> condition : conditions)
            bl = condition.test(object);

        return bl;
    }
}
