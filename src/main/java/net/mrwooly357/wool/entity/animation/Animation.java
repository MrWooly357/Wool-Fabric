package net.mrwooly357.wool.entity.animation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.client.MinecraftClient;
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
import net.mrwooly357.wool.network.packet.c2s.CanTickAnimationUpdateC2SPacket;
import net.mrwooly357.wool.network.packet.c2s.ElapsedAnimationTicksSyncC2SPacket;
import net.mrwooly357.wool.registry.WoolRegistries;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public record Animation(Identifier entityType, Identifier actionId, boolean loop, Randomizer randomizer, List<Variant> variants) {


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


    public record Randomizer(float min, float max, float threshold) {


        public boolean playRandom() {
            return MathHelper.nextFloat(Random.create(), min, max) <= threshold;
        }
    }


    public record Transformation(float x, float y, float z, float pitch, float yaw, float roll, float xScale, float yScale, float zScale) {}


    public record Keyframe(float progress, Map<String, Transformation> bones) {}


    public record Variant(int weight, int duration, List<Keyframe> keyframes, Interpolation interpolation) {


        public Keyframe getInterpolatedKeyframe(float progress) {
            int size = keyframes.size();

            if (keyframes.isEmpty()) {
                return new Keyframe(progress, Map.of());
            } else if (size == 1) {
                return keyframes.getFirst();
            } else {
                Keyframe previous = keyframes.getFirst();

                for (int i = 1; i < size; i++) {
                    Keyframe next = keyframes.get(i);

                    if (progress < next.progress) {
                        float t = (progress - previous.progress) / (next.progress - previous.progress);
                        int iBefore = i - 2;
                        Keyframe before = iBefore >= 0 ? keyframes.get(iBefore) : previous;
                        int iAfter = i + 1;
                        Keyframe after = iAfter < keyframes.size() ? keyframes.get(iAfter) : next;
                        Map<String, Transformation> previousBones = previous.bones;
                        Map<String, Transformation> result = new LinkedHashMap<>();

                        for (String bone : previousBones.keySet()) {
                            Transformation a = previousBones.get(bone);
                            Transformation b = next.bones.getOrDefault(bone, a);
                            Transformation p0 = before.bones.getOrDefault(bone, a);
                            Transformation p3 = after.bones.getOrDefault(bone, b);

                            result.put(bone, new Transformation(interpolation.apply(t, a.x, b.x, p0.x, p3.x), interpolation.apply(t, a.y, b.y, p0.y, p3.y), interpolation.apply(t, a.z, b.z, p0.z, p3.z),
                                    interpolation.apply(t, a.pitch, b.pitch, p0.pitch, p3.pitch), interpolation.apply(t, a.yaw, b.yaw, p0.yaw, p3.yaw), interpolation.apply(t, a.roll, b.roll, p0.roll, p3.roll),
                                    interpolation.apply(t, a.xScale, b.xScale, p0.xScale, p3.xScale), interpolation.apply(t, a.xScale, b.xScale, p0.xScale, p3.xScale), interpolation.apply(t, a.xScale, b.xScale, p0.xScale, p3.xScale)));
                        }

                        return new Keyframe(progress, result);
                    }

                    previous = next;
                }

                return keyframes.getLast();
            }
        }
    }


    public static class Player {

        private final Entity entity;
        private final Animatable.Server serverAnimatable;
        @Nullable
        Animation currentAnimation;
        @Nullable
        private Variant currentVariant;
        private int elapsedTicks;

        public Player(Entity entity) {
            this.entity = entity;
            serverAnimatable = ((Animatable.Server) entity);
            elapsedTicks = serverAnimatable.getElapsedAnimationTicks();
        }


        @Nullable
        public Variant getCurrentVariant() {
            return currentVariant;
        }

        public int getElapsedTicks() {
            return elapsedTicks;
        }

        public void play(@Nullable Action action) {
            if (action != null) {
                Animation animation = ((Animatable.Client.Renderer) MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity)).getAnimations().get(action.getId());

                if (animation != null) {

                    if (currentAnimation == null && elapsedTicks == 0) {

                        if (!animation.randomizer.playRandom())
                            return;

                        currentAnimation = animation;
                        currentVariant = animation.chooseVariant(Random.create());

                        sendCanTickAnimationUpdatePacket(true);
                        sendElapsedAnimationTicksSyncC2SPacket(0);
                    } else if (currentVariant != null && elapsedTicks > currentVariant.duration) {

                        if (currentAnimation.loop) {
                            currentVariant = animation.chooseVariant(Random.create());
                            elapsedTicks = 0;

                            sendElapsedAnimationTicksSyncC2SPacket(0);
                        } else {
                            currentAnimation = null;
                            currentVariant = null;
                            elapsedTicks = 0;

                            sendCanTickAnimationUpdatePacket(false);
                            sendElapsedAnimationTicksSyncC2SPacket(0);
                        }
                    }
                }
            }
        }

        public void tick() {
            elapsedTicks = serverAnimatable.getElapsedAnimationTicks();
        }

        private void sendCanTickAnimationUpdatePacket(boolean canTickAnimation) {
            ClientPlayNetworking.send(new CanTickAnimationUpdateC2SPacket(entity.getId(), canTickAnimation));
        }

        private void sendElapsedAnimationTicksSyncC2SPacket(int elapsedTicks) {
            ClientPlayNetworking.send(new ElapsedAnimationTicksSyncC2SPacket(entity.getId(), elapsedTicks));
        }
    }


    public static class PlayerStorage {

        private static final Map<Integer, Player> PLAYERS = new HashMap<>();


        public static Player get(Entity entity) {
            return PLAYERS.computeIfAbsent(entity.getId(), integer -> new Player(entity));
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

            Map<Identifier, Resource> resources = manager.findResources("animations/entity", path -> path.getPath().endsWith(".json"));

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
            JsonObject randomizer = json.getAsJsonObject("randomizer");

            for (JsonElement variantElement : json.getAsJsonArray("variants")) {
                JsonObject variantObject = variantElement.getAsJsonObject();
                int weight = variantObject.get("weight").getAsInt();
                List<Keyframe> keyframes = new ArrayList<>();

                for (JsonElement keyframeElement : variantObject.getAsJsonArray("keyframes")) {
                    JsonObject keyframeObject = keyframeElement.getAsJsonObject();
                    float progress = keyframeObject.get("progress").getAsFloat();
                    Map<String, Transformation> bones = new LinkedHashMap<>();

                    for (JsonElement boneElement : keyframeObject.getAsJsonArray("bones")) {
                        JsonObject boneObject = boneElement.getAsJsonObject();
                        float[] translation = parseFloatArray3(boneObject, "translation", false);
                        float[] rotation = parseFloatArray3(boneObject, "rotation", true);
                        float[] scale = parseFloatArray3(boneObject, "scale", false);

                        bones.put(boneObject.get("name").getAsString(), new Transformation(translation[0], translation[1], translation[2], rotation[0], rotation[1], rotation[2], scale[0], scale[1], scale[2]));
                    }

                    keyframes.add(new Keyframe(progress, bones));
                }

                variants.add(new Variant(weight, variantObject.get("duration").getAsInt(), keyframes, WoolRegistries.INTERPOLATION.get(Identifier.of(variantObject.get("interpolation").getAsString()))));
            }

            return new Animation(Identifier.of(json.get("entity_type").getAsString()), Identifier.of(json.get("action").getAsString()), json.get("loop").getAsBoolean(), new Randomizer(randomizer.get("min").getAsFloat(), randomizer.get("max").getAsFloat(), randomizer.get("threshold").getAsFloat()), variants);
        }

        private float[] parseFloatArray3(JsonObject object, String key, boolean toRadians) {
            if (!object.has(key))
                return new float[] {0, 0, 0};

            JsonArray array = object.getAsJsonArray(key);

            return toRadians ? new float[] {!array.isEmpty() ? (float) Math.toRadians(array.get(0).getAsFloat()) : 0.0F,
                    array.size() > 1 ? (float) Math.toRadians(array.get(1).getAsFloat()) : 0.0F,
                    array.size() > 2 ? (float) Math.toRadians(array.get(2).getAsFloat()) : 0.0F}
                    : new float[] {!array.isEmpty() ? array.get(0).getAsFloat() : 0.0F,
                    array.size() > 1 ? array.get(1).getAsFloat() : 0.0F,
                    array.size() > 2 ? array.get(2).getAsFloat() : 0.0F};
        }
    }
}
