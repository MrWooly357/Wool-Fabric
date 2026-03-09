package net.mrwooly357.wool.registry.custom;

import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class CustomRecipeCategoriesRegistry {

    private static final Map<RecipeCategory, CraftingRecipeCategory> TO_CRAFTING_CATEGORY = new HashMap<>();

    private CustomRecipeCategoriesRegistry() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate CustomRecipeCategoriesRegistry!");
    }


    public static void register(RecipeCategory category, CraftingRecipeCategory craftingCategory) {
        TO_CRAFTING_CATEGORY.put(category, craftingCategory);
    }

    public static Optional<CraftingRecipeCategory> getCraftingCategory(RecipeCategory category) {
        return Optional.ofNullable(TO_CRAFTING_CATEGORY.get(category));
    }
}
