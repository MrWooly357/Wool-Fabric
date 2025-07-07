package net.mrwooly357.wool.entity.action;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;

public interface Action {

    Action DYING = () -> Identifier.of(Wool.MOD_ID, "dying");


    Identifier getId();

    default boolean equals(Action action) {
        return getId() == action.getId();
    }
}
