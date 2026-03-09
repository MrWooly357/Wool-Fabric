package net.mrwooly357.wool.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.mrwooly357.wool.registry.custom.CustomRaritiesRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {


    @Inject(method = "getRarity", at = @At("RETURN"), cancellable = true)
    private void injectGetRarity(CallbackInfoReturnable<Rarity> cir) {
        if (hasEnchantments())
            CustomRaritiesRegistry.getWhenEnchanted(cir.getReturnValue()).ifPresent(cir::setReturnValue);
    }

    @Shadow
    public abstract boolean hasEnchantments();
}
