package net.mrwooly357.wool.animation.condition;

import net.minecraft.entity.Entity;

public class EmptyCondition implements Condition {

    public EmptyCondition() {}


    @Override
    public boolean test(Entity entity) {
        return false;
    }
}
