package net.mrwooly357.wool.animation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.entity.EntityType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.animation.condition.Condition;
import net.mrwooly357.wool.animation.interpolation.Interpolation;
import net.mrwooly357.wool.registry.ModRegistries;
import net.mrwooly357.wool.util.ModResourceReloadListenerKeys;
import org.joml.Vector3f;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class AnimationLoader implements SimpleSynchronousResourceReloadListener {

    public static final Map<EntityType<?>, List<AnimationData>> LOADED_ANIMATIONS = new HashMap<>();
    private AnimationData temporaryAnimationData;
    private AnimationVariantData temporaryAnimationVariantData;
    private ActualAnimationData temporaryActualAnimationData;
    private KeyframeData temporaryKeyframeData;
    private ActualKeyframeData temporaryActualKeyframeData;

    public AnimationLoader() {}


    @Override
    public Identifier getFabricId() {
        return ModResourceReloadListenerKeys.ANIMATIONS;
    }


    public record AnimationData(Identifier action, Identifier id, List<AnimationVariantData> variantsData) {}

    private record AnimationVariantData(Identifier id, int weight, List<Condition> conditions, List<ActualAnimationData> actualAnimationsData) {}

    private record ActualAnimationData(int duration, boolean loop, List<Animation.Keyframe> keyframes, List<KeyframeData> keyframesData) {}

    private record KeyframeData(String modelPartName, List<ActualKeyframeData> actualKeyframesData) {}

    private record ActualKeyframeData(int time, Interpolation interpolation, Vector3f translation, Vector3f rotation, Vector3f scale) {}


    @Override
    public void reload(ResourceManager manager) {
        LOADED_ANIMATIONS.clear();

        manager.findResources("animation", path -> path.getPath().contains("/animations.json")).forEach(
                (entityPath, resource) -> {
                    try (InputStream inputStream = resource.getInputStream()) {
                        JsonObject json = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonObject();
                        String[] splitId = json.get("entity").getAsString().split(":");
                        String namespace = splitId[0];
                        String name = splitId[1];
                        Identifier entityId = Identifier.of(namespace, name);
                        List<AnimationData> animationsData = new ArrayList<>();
                        JsonArray animations = json.getAsJsonArray("animations");
                        AnimationData animationData;

                        for (JsonElement animationElement : animations) {
                            JsonObject animationObject = animationElement.getAsJsonObject();

                            if (animationObject != null) {
                                animationData = loadAnimationData(manager, name, entityId);

                                animationsData.add(animationData);
                            }
                        }

                        resetTemporaryData();

                        Optional<EntityType<?>> optionalEntityType = EntityType.get(entityId.toString());

                        optionalEntityType.ifPresent(entityType -> LOADED_ANIMATIONS.put(entityType, animationsData));
                    } catch (Exception exception) {
                        Wool.LOGGER.error("Couldn't load animations for {} {}", entityPath, exception);
                    }
                }
        );
    }

    private AnimationData loadAnimationData(ResourceManager manager, String entityName, Identifier entityId) {
        manager.findResources("animation/" + entityName, path -> path.getPath().endsWith("/animations.json")).forEach(
                (entityPath, resource) -> {
                    try (InputStream inputStream = resource.getInputStream()) {
                        JsonObject json = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonObject();
                        Identifier action = Identifier.of("");
                        Identifier id = Identifier.of("");
                        List<AnimationVariantData> animationVariantsData = new ArrayList<>();
                        AnimationVariantData animationVariantData;

                        if (json.has("animations")) {
                            JsonArray animations = json.getAsJsonArray("animations");

                            for (JsonElement jsonElement : animations) {
                                JsonObject animationObject = jsonElement.getAsJsonObject();
                                String[] actionSplitId = animationObject.get("action").getAsString().split(":");
                                String actionNamespace = actionSplitId[0];
                                String actionName = actionSplitId[1];
                                action = Identifier.of(actionNamespace, actionName);
                                String[] idSplitId = animationObject.get("id").getAsString().split(":");
                                String idNamespace = idSplitId[0];
                                String idName = idSplitId[1];
                                id = Identifier.of(idNamespace, idName);
                                animationVariantData = loadAnimationVariantData(manager, entityId, entityName, id);

                                animationVariantsData.add(animationVariantData);
                            }
                        }

                        setTemporaryAnimationData(new AnimationData(action, id, animationVariantsData));
                    } catch (Exception exception) {
                        Wool.LOGGER.error("Couldn't load animation data for {} {} {}", entityId, entityPath, exception);
                    }
                }
        );

        return temporaryAnimationData;
    }

    private AnimationVariantData loadAnimationVariantData(ResourceManager manager, Identifier entityId, String entityName, Identifier animationId) {
        manager.findResources("animation/" + entityName + "/animations", path -> path.getPath().endsWith(".json")).forEach(
                (entityPath, resource) -> {
                    try (InputStream inputStream = resource.getInputStream()) {
                        JsonObject json = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonObject();
                        Identifier id = Identifier.of("");
                        int weight = 0;
                        List<Condition> conditions = new ArrayList<>();
                        List<ActualAnimationData> actualAnimationsData = new ArrayList<>();
                        JsonArray variants = json.getAsJsonArray("variants");
                        ActualAnimationData actualAnimationData;

                        for (JsonElement variantElement : variants) {
                            JsonObject variantObject = variantElement.getAsJsonObject();
                            id = Identifier.of(variantObject.get("id").getAsString());

                            if (variantObject.has("weight")) {
                                weight = variantObject.get("weight").getAsInt();
                            }

                            if (variantObject.has("conditions")) {
                                JsonArray conditionsArray = variantObject.getAsJsonArray("conditions");

                                for (int a = 0; a < conditionsArray.size(); a++) {
                                    conditions.add(ModRegistries.CONDITION.get(Identifier.of(conditionsArray.get(a).getAsString())));
                                }
                            }

                            actualAnimationData = loadActualAnimationData(manager, entityId, entityName, animationId);

                            actualAnimationsData.add(actualAnimationData);
                        }

                        setTemporaryAnimationVariantData(new AnimationVariantData(id, weight, conditions, actualAnimationsData));
                    } catch (Exception exception) {
                        Wool.LOGGER.error("Couldn't load animation variant data for {} {} {}", animationId, entityPath, exception);
                    }
                }
        );

        return temporaryAnimationVariantData;
    }

    private ActualAnimationData loadActualAnimationData(ResourceManager manager, Identifier entityId, String entityName, Identifier animationVariantId) {
        manager.findResources("animation/" + entityName + "/variants", path -> path.getPath().endsWith(".json")).forEach(
                (entityPath, resource) -> {
                    try (InputStream inputStream = resource.getInputStream()) {
                        JsonObject json = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonObject();
                        int duration = 0;
                        boolean loop = false;
                        List<Animation.Keyframe> keyframes = new ArrayList<>();
                        List<KeyframeData> keyframesData = new ArrayList<>();
                        KeyframeData keyframeData = loadKeyframeData(manager, entityId, entityName, animationVariantId);
                        duration = json.get("duration").getAsInt();

                        if (json.has("loop")) {
                            loop = json.get("loop").getAsBoolean();
                        }

                        JsonArray keyframesArray = json.getAsJsonArray("keyframes");

                        for (JsonElement jsonElement : keyframesArray) {

                            if (jsonElement.isJsonObject()) {

                                for (ActualKeyframeData actualKeyframeData : keyframeData.actualKeyframesData()) {
                                    keyframes.add(new Animation.Keyframe(actualKeyframeData.time(), actualKeyframeData.translation, actualKeyframeData.rotation, actualKeyframeData.scale, actualKeyframeData.interpolation));
                                }
                            }
                        }

                        keyframesData.add(keyframeData);
                        setTemporaryActualAnimationData(new ActualAnimationData(duration, loop, keyframes, keyframesData));
                    } catch (Exception exception) {
                        Wool.LOGGER.error("Couldn't load actual animation data for {} {} {}", animationVariantId, entityPath, exception);
                    }
                }
        );

        return temporaryActualAnimationData;
    }

    private KeyframeData loadKeyframeData(ResourceManager manager, Identifier entityId, String entityName, Identifier animationVariantId) {
        manager.findResources("animation/" + entityName + "/variants", path -> path.getPath().endsWith(".json")).forEach(
                (entityPath, resource) -> {
                    try (InputStream inputStream = resource.getInputStream()) {
                        JsonObject json = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonObject();
                        String modelPart = "";
                        List<ActualKeyframeData> actualKeyframesData = new ArrayList<>();
                        ActualKeyframeData actualKeyframeData;
                        JsonArray keyframes = json.getAsJsonArray("keyframes");

                        for (int a = 0; a < keyframes.size(); a++) {
                            actualKeyframeData = loadActualKeyframeData(manager, entityId, entityName, animationVariantId);

                            actualKeyframesData.add(actualKeyframeData);
                        }

                        setTemporaryKeyframeData(new KeyframeData(modelPart, actualKeyframesData));
                    } catch (Exception exception) {
                        Wool.LOGGER.error("Couldn't load keyframe data for {} {} {}", entityId, entityPath, exception);
                    }
                }
        );

        return temporaryKeyframeData;
    }

    private ActualKeyframeData loadActualKeyframeData(ResourceManager manager, Identifier entityId, String entityName, Identifier animationVariantId) {
        return temporaryActualKeyframeData;
    }

    private void setTemporaryAnimationData(AnimationData animationData) {
        temporaryAnimationData = animationData;
    }

    private void setTemporaryAnimationVariantData(AnimationVariantData animationVariantData) {
        temporaryAnimationVariantData = animationVariantData;
    }

    private void setTemporaryActualAnimationData(ActualAnimationData actualAnimationData) {
        temporaryActualAnimationData = actualAnimationData;
    }

    private void setTemporaryKeyframeData(KeyframeData keyframeData) {
        temporaryKeyframeData = keyframeData;
    }

    private void setTemporaryActualKeyframeData(ActualKeyframeData actualKeyframeData) {
        temporaryActualKeyframeData = actualKeyframeData;
    }

    private void resetTemporaryData() {
        setTemporaryAnimationData(null);
        setTemporaryAnimationVariantData(null);
        setTemporaryActualAnimationData(null);
        setTemporaryKeyframeData(null);
        setTemporaryActualKeyframeData(null);
    }

    public static void initialize() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new AnimationLoader());
        Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " animation loader.");
    }
}
