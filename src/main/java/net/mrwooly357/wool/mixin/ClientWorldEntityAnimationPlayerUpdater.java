package net.mrwooly357.wool.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.mrwooly357.wool.entity.animation.Animatable;
import net.mrwooly357.wool.entity.animation.Animation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldEntityAnimationPlayerUpdater {


    @Inject(method = "tickEntity", at = @At("HEAD"))
    private void injectTickEntity(Entity entity, CallbackInfo info) {
        Animation.Player player = Animation.PlayerStorage.get(entity);

        if (entity instanceof Animatable.Server serverAnimatable) {
            player.play(serverAnimatable.getCurrentAction());
            player.tick();
        }
    }
}
