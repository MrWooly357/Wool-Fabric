package net.mrwooly357.wool.mixin;

import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.RecipeEntry;
import net.mrwooly357.wool.recipe_util.NoUnknownRecipeCategoryLogWarningRecipeTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public class NoUnknownRecipeGroupLogWarningRecipeTypesMixin {


    @Inject(method = "getGroupForRecipe", at = @At("HEAD"), cancellable = true)
    private static void injectGetGroupForRecipe(RecipeEntry<?> recipe, CallbackInfoReturnable<RecipeBookGroup> cir) {
       if (NoUnknownRecipeCategoryLogWarningRecipeTypes.contains(recipe.value().getType()))
           cir.setReturnValue(RecipeBookGroup.UNKNOWN);
    }
}
