package net.mrwooly357.wool.entity.action;

import net.minecraft.entity.data.DataTracked;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.util.Identifier;

import java.util.Map;

public interface ActionHolder {


    TrackedData<String> getCurrentActionTrackedData();

    static TrackedData<String> createCurrentActionTrackedData(Class<? extends DataTracked> entityClass) {
        return DataTracker.registerData(entityClass, TrackedDataHandlerRegistry.STRING);
    }

    Action getCurrentAction();

    default Action getCurrentAction(DataTracker tracker) {
        return getIdsToActions().get(Identifier.of(tracker.get(getCurrentActionTrackedData())));
    }

    void setCurrentAction(Action action);

    default void setCurrentAction(DataTracker tracker, Action action) {
        tracker.set(getCurrentActionTrackedData(), action.getId().toString());
    }

    Map<Identifier, Action> getIdsToActions();
}
