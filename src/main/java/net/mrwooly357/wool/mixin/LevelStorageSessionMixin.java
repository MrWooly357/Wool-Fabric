package net.mrwooly357.wool.mixin;

import net.minecraft.world.level.storage.LevelStorage;
import net.mrwooly357.wool.config.ConfigManagersRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelStorage.Session.class)
public abstract class LevelStorageSessionMixin {

    @Shadow
    @Final
    private String directoryName;


    @Inject(
            method = "deleteSessionLock",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/nio/file/Files;walkFileTree(Ljava/nio/file/Path;Ljava/nio/file/FileVisitor;)Ljava/nio/file/Path;",
                    shift = At.Shift.AFTER
            )
    )
    private void injectDeleteSession(CallbackInfo ci) {
        ConfigManagersRegistry.forEachWorld(manager -> manager.delete(directoryName));
    }
}
