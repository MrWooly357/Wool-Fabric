package net.mrwooly357.wool.mixin;

import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.*;
import net.mrwooly357.wool.registry.custom.CustomRecipeTypesRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public abstract class ClientRecipeBookMixin {


    @Inject(method = "getGroupForRecipe", at = @At("RETURN"), cancellable = true)
    private static void injectGetGroupForRecipe(RecipeEntry<?> recipe, CallbackInfoReturnable<RecipeBookGroup> cir) {
        CustomRecipeTypesRegistry.getBookCategory(recipe.value().getType()).ifPresent(cir::setReturnValue);
    }
}
