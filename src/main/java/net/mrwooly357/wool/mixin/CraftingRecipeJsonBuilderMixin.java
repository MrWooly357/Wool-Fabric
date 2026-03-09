package net.mrwooly357.wool.mixin;

import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;
import net.mrwooly357.wool.registry.custom.CustomRecipeCategoriesRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingRecipeJsonBuilder.class)
public interface CraftingRecipeJsonBuilderMixin {


    @Inject(method = "toCraftingCategory", at = @At("RETURN"), cancellable = true)
    private static void injectToCraftingCategory(RecipeCategory category, CallbackInfoReturnable<CraftingRecipeCategory> cir) {
       CustomRecipeCategoriesRegistry.getCraftingCategory(category).ifPresent(cir::setReturnValue);
    }
}
