package net.mrwooly357.wool.mixin;

import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.mrwooly357.wool.registry.custom.CustomRecipeBookCategoriesRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(RecipeBookGroup.class)
public abstract class RecipeBookGroupMixin {


    @Inject(method = "getGroups", at = @At("RETURN"), cancellable = true)
    private static void injectGetGroups(RecipeBookCategory category, CallbackInfoReturnable<List<RecipeBookGroup>> cir) {
        CustomRecipeBookCategoriesRegistry.getBookGroups(category).ifPresent(cir::setReturnValue);
    }
}
