package net.mrwooly357.wool.animation.condition.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.mrwooly357.wool.animation.condition.Condition;

public class IsBabyCondition implements Condition {

    public IsBabyCondition() {}


    @Override
    public boolean test(Entity entity) {
        if (entity instanceof MobEntity mob) {
            return mob.isBaby();
        } else {
            return false;
        }
    }
}
