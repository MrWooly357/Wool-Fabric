package net.mrwooly357.wool.mixin;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.mrwooly357.wool.registry.custom.CustomBoatsRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {


    @Inject(method = "asItem", at = @At("RETURN"), cancellable = true)
    private void injectAsItem(CallbackInfoReturnable<Item> cir) {
        CustomBoatsRegistry.getAsItem(getVariant()).ifPresent(cir::setReturnValue);
    }

    @Shadow
    public abstract BoatEntity.Type getVariant();
}
