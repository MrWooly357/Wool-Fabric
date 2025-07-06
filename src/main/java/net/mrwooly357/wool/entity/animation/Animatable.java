package net.mrwooly357.wool.entity.animation;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.entity.Entity;
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

        static TrackedData<Integer> createlapsedAnimationTicksTrackedData(Class<? extends DataTracked> entityClass) {
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


            default void applyAnimation(Entity entity, Animation.Player player) {
                player.play(((Server) entity).getCurrentAction());

                for (Map.Entry<String, Animation.Transform> bone : player.getCurrentVariant().getInterpolatedKeyframe(player.getElapsedTicks()).bones().entrySet()) {

                    for (Map.Entry<String, ModelPart> partEntry : getModelParts().entrySet()) {

                        if (Objects.equals(bone.getKey(), partEntry.getKey())) {
                            Animation.Transform transform = bone.getValue();
                            ModelPart part = partEntry.getValue();

                            part.setTransform(ModelTransform.of(transform.x(), transform.y(), transform.z(), transform.pitch(), transform.yaw(), transform.roll()));
                            part.xScale = transform.xScale();
                            part.yScale = transform.yScale();
                            part.zScale = transform.zScale();

                            break;
                        }
                    }
                }
            }

            Map<String, ModelPart> getModelParts();

            Map<Identifier, Animation> getAnimations();

            static Map<Identifier, Animation> createAnimations(Entity entity) {
                Map<Identifier, Animation> map = new HashMap<>();

                for (Map.Entry<Identifier, Animation> entry : WoolClient.ANIMATION_LOADER.getTemplates().get(entity.getType()).entrySet()) {
                    Animation template = entry.getValue();

                    map.put(Identifier.of(entry.getKey().toString()), new Animation(template.entityType(), template.actionId(), template.loop(), template.variants()));
                }

                return map;
            }
        }


        interface Renderer {


            Animation.Player getAnimationPlayer();
        }
    }
}
