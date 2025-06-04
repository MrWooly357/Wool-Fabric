package net.mrwooly357.wool.animation.condition.custom;

import net.minecraft.entity.Entity;
import net.mrwooly357.wool.animation.condition.Condition;

public class EmptyCondition implements Condition {

    public EmptyCondition() {}


    @Override
    public boolean test(Entity entity) {
        return false;
    }
}
