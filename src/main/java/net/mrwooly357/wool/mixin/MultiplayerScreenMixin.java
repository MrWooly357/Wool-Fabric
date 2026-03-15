package net.mrwooly357.wool.mixin;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.mrwooly357.wool.config.ConfigManagersRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public abstract class MultiplayerScreenMixin {

    @Shadow
    protected MultiplayerServerListWidget serverListWidget;


    @Inject(method = "removeEntry", at = @At("HEAD"))
    private void inject(boolean confirmedAction, CallbackInfo ci) {
        if (confirmedAction && serverListWidget.getSelectedOrNull() instanceof MultiplayerServerListWidget.ServerEntry entry) {
            String name = entry.getServer().name;
            ConfigManagersRegistry.forEachWorld(manager -> {
                if (manager.isClient())
                    manager.delete(name);
            });
        }
    }
}
