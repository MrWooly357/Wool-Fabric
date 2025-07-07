package net.mrwooly357.wool.mixin;

import net.minecraft.client.model.ModelPart;
import net.mrwooly357.wool.entity.animation.OriginalDataTrackedModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(ModelPart.class)
public abstract class ModelPartOriginalDataTrackerMixin implements OriginalDataTrackedModelPart {

    @Shadow
    public float pivotX;
    @Shadow
    public float pivotY;
    @Shadow
    public float pivotZ;
    @Shadow
    public float roll;
    @Shadow
    public float yaw;
    @Shadow
    public float pitch;
    @Shadow
    public float xScale;
    @Shadow
    public float yScale;
    @Shadow
    public float zScale;
    @Unique
    private float originalPivotX;
    @Unique
    private float originalPivotY;
    @Unique
    private float originalPivotZ;
    @Unique
    private float originalPitch;
    @Unique
    private float originalYaw;
    @Unique
    private float originalRoll;
    @Unique
    private float originalXScale;
    @Unique
    private float originalYScale;
    @Unique
    private float originalZScale;


    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectConstructor(List<ModelPart.Cuboid> cuboids, Map<String, ModelPart> children, CallbackInfo info) {
        this.originalPivotX = pivotX;
        this.originalPivotY = pivotY;
        this.originalPivotZ = pivotZ;
        this.originalPitch = pitch;
        this.originalYaw = yaw;
        this.originalRoll = roll;
        this.originalXScale = xScale;
        this.originalYScale = yScale;
        this.originalZScale = zScale;
    }

    @Override
    public float getOriginalPivotX() {
        return originalPivotX;
    }

    @Override
    public float getOriginalPivotY() {
        return originalPivotY;
    }

    @Override
    public float getOriginalPivotZ() {
        return originalPivotZ;
    }

    @Override
    public float getOriginalPitch() {
        return originalPitch;
    }

    @Override
    public float getOriginalYaw() {
        return originalYaw;
    }

    @Override
    public float getOriginalRoll() {
        return originalRoll;
    }

    @Override
    public float getOriginalXScale() {
        return originalXScale;
    }

    @Override
    public float getOriginalYScale() {
        return originalYScale;
    }

    @Override
    public float getOriginalZScale() {
        return originalZScale;
    }
}
