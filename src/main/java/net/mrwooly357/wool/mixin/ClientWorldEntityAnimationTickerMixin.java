package net.mrwooly357.wool.mixin;

import net.minecraft.client.MinecraftClient;
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
public abstract class ClientWorldEntityAnimationTickerMixin {


    @Inject(method = "tickEntity", at = @At("HEAD"))
    private void injectTickEntities(Entity entity, CallbackInfo info) {
        if (entity instanceof Animatable.Server serverAnimatable) {
            Action action = serverAnimatable.getCurrentAction();

            if (action != null && MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity) instanceof Animatable.Client.Renderer clientAnimatable) {
                Animation animation = clientAnimatable.getAnimations().get(action.getId());

                if (animation != null) {
                    Animation.Player player = Animation.PlayerStorage.get(entity);

                    player.play(action, animation);
                    player.tick();
                }
            }
        }
    }
}
