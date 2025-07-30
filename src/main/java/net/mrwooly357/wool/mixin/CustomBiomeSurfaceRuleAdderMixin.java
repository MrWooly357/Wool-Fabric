package net.mrwooly357.wool.mixin;

import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.mrwooly357.wool.custom_biome.region.RegionType;
import net.mrwooly357.wool.custom_biome.SurfaceRuleManager;
import net.mrwooly357.wool.util.holder.custom.RegionTypeHolder;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkGeneratorSettings.class)
public abstract class CustomBiomeSurfaceRuleAdderMixin implements RegionTypeHolder {

    @Unique
    @Nullable
    private RegionType regionType;


    @Inject(method = "surfaceRule", at = @At("HEAD"), cancellable = true)
    private void injectSurfaceRule(CallbackInfoReturnable<MaterialRules.MaterialRule> cir) {
        if (regionType != null) {
            MaterialRules.MaterialRule materialRule = SurfaceRuleManager.getCombinedSurfaceMaterialRules(regionType);

            if (materialRule != null)
                cir.setReturnValue(materialRule);
        }

    }

    @Override
    public RegionType get() {
        return regionType;
    }

    @Override
    public void set(RegionType regionType) {
        this.regionType = regionType;
    }
}
