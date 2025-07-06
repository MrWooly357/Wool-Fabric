package net.mrwooly357.wool.entity.animation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.profiler.Profiler;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.entity.action.Action;
import net.mrwooly357.wool.entity.animation.interpolation.Interpolation;
import net.mrwooly357.wool.registry.WoolRegistries;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public record Animation(Identifier entityType, Identifier actionId, boolean loop, List<Variant> variants) {


    @Nullable
    public Variant chooseVariant(Random random) {
        if (!variants.isEmpty()) {
            int totalWeight = variants.stream().mapToInt(Variant::weight).sum();
            int roll = random.nextInt(totalWeight);

            for (Variant variant : variants) {
                roll -= variant.weight();

                if (roll < 0)
                    return variant;
            }

            return variants.getFirst();
        } else
            return null;
    }


    public record Transform(float x, float y, float z, float pitch, float yaw, float roll, float xScale, float yScale, float zScale) {


        public static Transform lerp(Transform a, Transform b, float t) {
            return new Transform(MathHelper.lerp(t, a.x, b.x), MathHelper.lerp(t, a.y, b.y), MathHelper.lerp(t, a.z, b.z), MathHelper.lerp(t, a.pitch, b.pitch), MathHelper.lerp(t, a.yaw, b.yaw), MathHelper.lerp(t, a.roll, b.roll), MathHelper.lerp(t, a.xScale, b.xScale), MathHelper.lerp(t, a.yScale, b.yScale), MathHelper.lerp(t, a.zScale, b.zScale));
        }
    }


    public record Keyframe(float progress, Map<String, Transform> bones) {


        public static Keyframe interpolate(Keyframe previous, Keyframe next, float t) {
            Map<String, Transform> result = new LinkedHashMap<>();

            for (String bone : previous.bones.keySet()) {
                Transform tPrevious = previous.bones.get(bone);
                Transform tNext = next.bones.getOrDefault(bone, tPrevious);

                result.put(bone, Transform.lerp(tPrevious, tNext, t));
            }

            return new Keyframe(MathHelper.lerp(t, previous.progress, next.progress), result);
        }
    }


    public record Variant(int weight, int duration, List<Keyframe> keyframes, Interpolation interpolation) {


        public Keyframe getInterpolatedKeyframe(float progress) {
            if (keyframes.isEmpty()) {
                return new Keyframe(progress, Map.of());
            } else if (keyframes.size() == 1) {
                return keyframes.getFirst();
            } else {
                Keyframe previous = keyframes.getFirst();

                for (int i = 1; i < keyframes.size(); i++) {
                    Keyframe next = keyframes.get(i);

                    if (progress < next.progress()) {
                        float progress1 = (progress - previous.progress()) / (next.progress() - previous.progress());
                        float t = interpolation.apply(progress1);

                        return Keyframe.interpolate(previous, next, t);
                    }

                    previous = next;
                }

                return keyframes.getLast();
            }
        }
    }


    public static class Player {

        private Animatable.Server serverAnimatable;
        private Animatable.Client.Model clientAnimatableModel;
        @Nullable
        private Action currentAction;
        private Variant currentVariant;
        private int elapsedTicks;

        public Player(Entity entity, EntityModel<? extends Animatable.Server> model) {
            if (entity instanceof Animatable.Server server)
                serverAnimatable = server;

            if (model instanceof Animatable.Client.Model clientModel)
                clientAnimatableModel = clientModel;

            elapsedTicks = serverAnimatable.getElapsedAnimationTicks();
        }


        public Variant getCurrentVariant() {
            return currentVariant;
        }

        public int getElapsedTicks() {
            return elapsedTicks;
        }

        public void play(Action action) {
            play(clientAnimatableModel.getAnimations().get(action.getId()));
        }

        public void play(@Nullable Animation animation) {
            if (animation != null) {
                Action action = serverAnimatable.getIdsToActions().get(animation.actionId());
                Random random = Random.create();

                if (!action.equals(currentAction)) {
                    currentAction = action;
                    currentVariant = animation.chooseVariant(random);
                    elapsedTicks = 0;
                } else {
                    elapsedTicks++;

                    if (currentVariant != null && elapsedTicks >= currentVariant.duration()) {

                        if (animation.loop()) {
                            currentVariant = animation.chooseVariant(random);
                            elapsedTicks = 0;
                        } else
                            elapsedTicks = currentVariant.duration();
                    }
                }

                serverAnimatable.setElapsedAnimationTicks(elapsedTicks);
            }
        }
    }


    public static class Loader implements IdentifiableResourceReloadListener {

        private final Map<EntityType<?>, Map<Identifier, Animation>> templates = new HashMap<>();


        private static final Identifier ID = Identifier.of(Wool.MOD_ID, "animation_loader");

        public Map<EntityType<?>, Map<Identifier, Animation>> getTemplates() {
            return templates;
        }


        @Override
        public Identifier getFabricId() {
            return ID;
        }

        @Override
        public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
            return CompletableFuture.supplyAsync(() -> {
                loadResources(manager);

                return null;
            }, prepareExecutor).thenCompose(synchronizer::whenPrepared).thenAcceptAsync(object -> {}, applyExecutor);
        }

        private void loadResources(ResourceManager manager) {
            templates.clear();

            Map<Identifier, Resource> resources = manager.findResources("animation/entity", path -> path.getPath().endsWith(".entityanimation.json"));

            for (Map.Entry<Identifier, Resource> entry : resources.entrySet()) {

                try (Reader reader = entry.getValue().getReader()) {
                    Animation animation = parseAnimation(JsonParser.parseReader(reader).getAsJsonObject());

                    templates.computeIfAbsent(Registries.ENTITY_TYPE.get(animation.entityType()), entityType1 -> new HashMap<>()).put(animation.actionId(), animation);
                } catch (Exception exception) {

                    if (WoolConfig.developerMode) {
                        Wool.LOGGER.error("Failed to load animation: {}, {}", entry.getKey(), exception.getMessage());
                        exception.printStackTrace();
                    }
                }
            }

            if (WoolConfig.developerMode)
                Wool.LOGGER.info("Loaded " + Wool.MOD_ID + " animations");
        }

        private Animation parseAnimation(JsonObject json) {
            List<Variant> variants = new ArrayList<>();

            for (JsonElement variantElement : json.getAsJsonArray("variants")) {
                JsonObject variantObject = variantElement.getAsJsonObject();
                int weight = variantObject.get("weight").getAsInt();
                List<Keyframe> keyframes = new ArrayList<>();

                for (JsonElement keyframeElement : variantObject.getAsJsonArray("keyframes")) {
                    JsonObject keyframeObject = keyframeElement.getAsJsonObject();
                    float progress = keyframeObject.get("progress").getAsFloat();
                    Map<String, Transform> bones = new LinkedHashMap<>();

                    for (JsonElement boneElement : keyframeObject.getAsJsonArray("bones")) {
                        JsonObject boneObject = boneElement.getAsJsonObject();
                        float[] translation = parseFloatArray3(boneObject, "translation", false);
                        float[] rotation = parseFloatArray3(boneObject, "rotation", true);
                        float[] scale = parseFloatArray3(boneObject, "scale", false);

                        bones.put(boneObject.get("name").getAsString(), new Transform(translation[0], translation[1], translation[2], rotation[0], rotation[1], rotation[2], scale[0], scale[1], scale[2]));
                    }

                    keyframes.add(new Keyframe(progress, bones));
                }

                variants.add(new Variant(weight, variantObject.get("duration").getAsInt(), keyframes, WoolRegistries.INTERPOLATION.get(Identifier.of(variantObject.get("interpolation").getAsString()))));
            }

            return new Animation(Identifier.of(json.get("entity_type").getAsString()), Identifier.of(json.get("action").getAsString()), json.get("loop").getAsBoolean(), variants);
        }

        private float[] parseFloatArray3(JsonObject object, String key, boolean toRadians) {
            if (!object.has(key))
                return new float[] {0, 0, 0};

            JsonArray array = object.getAsJsonArray(key);
            float[] result = new float[] {!array.isEmpty() ? array.get(0).getAsFloat() : 0.0F, array.size() > 1 ? array.get(1).getAsFloat() : 0.0F, array.size() > 2 ? array.get(2).getAsFloat() : 0.0F};

            return toRadians ? new float[] {(float) Math.toRadians(result[0]), (float) Math.toRadians(result[1]), (float) Math.toRadians(result[2])} : result;
        }
    }
}
