package net.mrwooly357.wool.recipe_util;

import net.minecraft.recipe.RecipeType;

import java.util.HashSet;
import java.util.Set;

public final class NoUnknownRecipeCategoryLogWarningRecipeTypes {

    private static final Set<RecipeType<?>> RECIPE_TYPES = new HashSet<>();


    public static void addRecipeType(RecipeType<?> recipeType) {
        RECIPE_TYPES.add(recipeType);
    }

    public static boolean contains(RecipeType<?> recipeType) {
        return RECIPE_TYPES.contains(recipeType);
    }
}
