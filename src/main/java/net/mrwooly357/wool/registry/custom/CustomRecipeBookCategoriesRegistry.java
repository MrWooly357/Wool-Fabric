package net.mrwooly357.wool.registry.custom;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.book.RecipeBookOptions;
import net.mrwooly357.wool.util.collection.map.MapUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class CustomRecipeBookCategoriesRegistry {

    private static final Map<RecipeBookCategory, Pair<String, String>> TO_CATEGORY_OPTION_NAMES = new HashMap<>();
    private static final Map<RecipeBookCategory, List<RecipeBookGroup>> TO_BOOK_GROUPS = new HashMap<>();

    private CustomRecipeBookCategoriesRegistry() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate RecipeBookOptionsCategoryNamesRegistry!");
    }


    public static void register(RecipeBookCategory category, String isOpen, String isFilteringCraftable, List<RecipeBookGroup> bookGroups) {
        TO_CATEGORY_OPTION_NAMES.put(category, Pair.of(isOpen, isFilteringCraftable));
        TO_BOOK_GROUPS.put(category, bookGroups);
    }

    public static Map<RecipeBookCategory, Pair<String, String>> getCombinedOptionNames() {
        return MapUtil.immutableCopyWith(RecipeBookOptions.CATEGORY_OPTION_NAMES, TO_CATEGORY_OPTION_NAMES);
    }

    public static Optional<List<RecipeBookGroup>> getBookGroups(RecipeBookCategory category) {
        return Optional.ofNullable(TO_BOOK_GROUPS.get(category));
    }
}
