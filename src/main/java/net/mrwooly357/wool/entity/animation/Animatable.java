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
                Animation.Variant variant = Animation.PlayerStorage.get(entity).getCurrentVariant();

                if (variant != null) {

                    for (Map.Entry<String, Animation.Transform> bone : variant.getInterpolatedKeyframe(((Server) entity).getElapsedAnimationTicks()).bones().entrySet()) {

                        for (Map.Entry<String, ModelPart> partEntry : getModelParts().entrySet()) {

                            if (Objects.equals(bone.getKey(), partEntry.getKey())) {
                                ModelPart part = partEntry.getValue();
                                Animation.Transform transform = bone.getValue();
                                part.pivotX = -transform.x();
                                part.pivotY = -transform.y();
                                part.pivotZ = -transform.z();
                                part.pitch = -transform.pitch();
                                part.yaw = -transform.yaw();
                                part.roll = -transform.roll();
                                part.xScale = -transform.xScale();
                                part.yScale = -transform.yScale();
                                part.zScale = -transform.zScale();

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
