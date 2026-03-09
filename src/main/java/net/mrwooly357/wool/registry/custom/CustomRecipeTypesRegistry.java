package net.mrwooly357.wool.registry.custom;

import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.RecipeType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class CustomRecipeTypesRegistry {

    private static final Map<RecipeType<?>, RecipeBookGroup> TO_BOOK_GROUP = new HashMap<>();

    private CustomRecipeTypesRegistry() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate CustomRecipeTypesRegistry!");
    }


    public static void register(RecipeType<?> type, RecipeBookGroup bookGroup) {
        TO_BOOK_GROUP.put(type, bookGroup);
    }

    public static Optional<RecipeBookGroup> getBookCategory(RecipeType<?> type) {
        return Optional.ofNullable(TO_BOOK_GROUP.get(type));
    }
}
