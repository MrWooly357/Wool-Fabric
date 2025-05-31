package net.mrwooly357.wool.mixin;

import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.entity.Entity;
import net.mrwooly357.wool.animation.AnimationLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenEntityModel.class)
public abstract class ChickenEntityModelMixin<Type extends Entity> extends AnimalModel<Type> {


    @SuppressWarnings("unchecked")
    @Inject(method = "setAngles", at = @At("TAIL"), cancellable = true)
    private void setAngles(Type entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, CallbackInfo callbackInfo) {
        ChickenEntityModel<Type> model = (((ChickenEntityModel<Type>) (Object) this));
    }
}
