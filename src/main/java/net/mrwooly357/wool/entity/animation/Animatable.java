package net.mrwooly357.wool.entity.animation;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracked;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.WoolClient;
import net.mrwooly357.wool.entity.action.ActionHolder;

import java.util.HashMap;
import java.util.Map;

public interface Animatable {


    interface Server extends ActionHolder {


        default void tickAnimation() {
            if (canTickAnimation())
                setElapsedAnimationTicks(getElapsedAnimationTicks() + 1);
        }

        TrackedData<Boolean> getCanTickAnimationTrackedData();

        static TrackedData<Boolean> createCanTickAnimationTrackedData(Class<? extends DataTracked> entityClass) {
            return DataTracker.registerData(entityClass, TrackedDataHandlerRegistry.BOOLEAN);
        }

        boolean canTickAnimation();

        default boolean canTickAnimation(DataTracker tracker) {
            return tracker.get(getCanTickAnimationTrackedData());
        }

        void setCanTickAnimation(boolean canTickAnimation);

        default void setCanTickAnimation(DataTracker tracker, boolean canTickAnimation) {
            tracker.set(getCanTickAnimationTrackedData(), canTickAnimation);
        }

        TrackedData<Integer> getElapsedAnimationTicksTrackedData();

        static TrackedData<Integer> createElapsedAnimationTicksTrackedData(Class<? extends DataTracked> entityClass) {
            return DataTracker.registerData(entityClass, TrackedDataHandlerRegistry.INTEGER);
        }

        int getElapsedAnimationTicks();

        default int getElapsedAnimationTicks(DataTracker tracker) {
            return tracker.get(getElapsedAnimationTicksTrackedData());
        }

        void setElapsedAnimationTicks(int elapsedAnimationTicks);

        default void setElapsedAnimationTicks(DataTracker tracker, int elapsedAnimationTicks) {
            tracker.set(getElapsedAnimationTicksTrackedData(), elapsedAnimationTicks);
        }
    }


    interface Client {


        interface Model {


            Map<String, ModelPart> getModelParts();
        }


        interface Renderer {


            Map<String, ModelPart> getModelParts();

            Map<Identifier, Animation> getAnimations();

            default void applyAnimation(Entity entity, float tickDelta) {
                Animation.Player player = Animation.PlayerStorage.get(entity);
                Animation.Variant variant = player.getCurrentVariant();

                if (variant != null) {

                    for (Map.Entry<String, Animation.Transformation> bone : variant.getInterpolatedKeyframe(player.getElapsedTicks() + tickDelta).bones().entrySet()) {
                        ModelPart part = getModelParts().get(bone.getKey());

                        if (part != null) {
                            Animation.Transformation transformation = bone.getValue();
                            part.pivotX = transformation.x();
                            part.pivotY = transformation.y();
                            part.pivotZ = transformation.z();
                            part.pitch = transformation.pitch();
                            part.yaw = transformation.yaw();
                            part.roll = transformation.roll();
                            part.xScale = transformation.xScale();
                            part.yScale = transformation.yScale();
                            part.zScale = transformation.zScale();
                        }
                    }
                }
            }

            static Map<Identifier, Animation> createAnimations(EntityType<? extends Server> type) {
                return new HashMap<>(WoolClient.ANIMATION_LOADER.getTemplates().get(type));
            }
        }
    }
}
