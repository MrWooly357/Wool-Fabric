package net.mrwooly357.wool.util.condition.custom;

import net.minecraft.entity.Entity;
import net.mrwooly357.wool.util.condition.Condition;

@FunctionalInterface
public interface EntityCondition extends Condition<Entity> {


    @Override
    boolean test(Entity entity);
}
