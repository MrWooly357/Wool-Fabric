package net.mrwooly357.wool.entity.action;

import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.util.Identifier;

import java.util.Map;

public interface ActionHolder {

    TrackedData<String> ACTION = DataTracker.registerData(Entity.class, TrackedDataHandlerRegistry.STRING);


    Action getCurrentAction();

    default Action getCurrentAction(DataTracker tracker) {
        return getIdsToActions().get(Identifier.of(tracker.get(ACTION)));
    }

    void setCurrentAction(Action action);

    default void setCurrentAction(DataTracker tracker, Action action) {
        tracker.set(ACTION, action.getId().toString());
    }

    Map<Identifier, Action> getIdsToActions();
}
