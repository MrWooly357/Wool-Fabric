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
import java.util.Objects;

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

            default void applyAnimation(Entity entity) {
                Animation.Player player = Animation.PlayerStorage.get(entity);
                Animation.Variant variant = player.getCurrentVariant();

                if (variant != null) {

                    for (Map.Entry<String, Animation.Transform> bone : variant.getInterpolatedKeyframe(player.getElapsedTicks()).bones().entrySet()) {

                        for (Map.Entry<String, ModelPart> partEntry : getModelParts().entrySet()) {

                            if (Objects.equals(bone.getKey(), partEntry.getKey())) {
                                ModelPart part = partEntry.getValue();
                                OriginalDataTrackedModelPart originalPart = ((OriginalDataTrackedModelPart) part);
                                Animation.Transform transform = bone.getValue();
                                part.pivotX = originalPart.getOriginalPivotX() - transform.x();
                                part.pivotY = originalPart.getOriginalPivotY() - transform.y();
                                part.pivotZ = originalPart.getOriginalPivotZ() - transform.z();
                                part.pitch = originalPart.getOriginalPitch() - transform.pitch();
                                part.yaw = originalPart.getOriginalYaw() - transform.yaw();
                                part.roll = originalPart.getOriginalRoll() - transform.roll();
                                part.xScale = originalPart.getOriginalXScale() - transform.xScale();
                                part.yScale = originalPart.getOriginalYScale() - transform.yScale();
                                part.zScale = originalPart.getOriginalZScale() - transform.zScale();

                                break;
                            }
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
