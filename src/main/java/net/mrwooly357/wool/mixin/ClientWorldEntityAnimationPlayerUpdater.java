package net.mrwooly357.wool.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.mrwooly357.wool.entity.action.Action;
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
        if (entity instanceof Animatable.Server serverAnimatable) {
            Animation.Player player = Animation.PlayerStorage.get(entity);
            Action action = serverAnimatable.getCurrentAction();

            player.play(action);
            player.tick();
        }
    }
}
