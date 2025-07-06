package net.mrwooly357.wool.entity.action;

import net.minecraft.util.Identifier;

public interface Action {


    Identifier getId();

    default boolean equals(Action action) {
        return getId() == action.getId();
    }
}
