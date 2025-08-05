package net.mrwooly357.wool.recipe_util;

import net.minecraft.recipe.RecipeType;

import java.util.HashSet;
import java.util.Set;

public interface NoUnknownRecipeCategoryLogWarningRecipeTypes {

    Set<RecipeType<?>> RECIPE_TYPES = new HashSet<>();


    static void addRecipeType(RecipeType<?> recipeType) {
        RECIPE_TYPES.add(recipeType);
    }

    static boolean contains(RecipeType<?> recipeType) {
        return RECIPE_TYPES.contains(recipeType);
    }
}
