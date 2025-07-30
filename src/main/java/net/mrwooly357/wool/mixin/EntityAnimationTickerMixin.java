package net.mrwooly357.wool.mixin;

import net.minecraft.entity.Entity;
import net.mrwooly357.wool.animation.entity.Animatable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityAnimationTickerMixin {


    @Inject(method = "tick", at = @At("HEAD"))
    private void injectTick(CallbackInfo info) {
        if (((Entity) ((Object) this)) instanceof Animatable.Server serverAnimatable)
            serverAnimatable.tickAnimation();
    }
}
