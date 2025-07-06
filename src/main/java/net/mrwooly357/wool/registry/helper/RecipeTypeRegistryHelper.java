package net.mrwooly357.wool.registry.helper;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * A helper used for registering custom {@link RecipeType}s.
 */
public interface RecipeTypeRegistryHelper {


    /**
     * Registers a custom {@link RecipeType}.
     * @param id the {@link Identifier}.
     * @param type the {@link RecipeType}.
     * @return a registered {@link RecipeType}.
     * @param <R> the {@link Recipe}
     */
    static <R extends Recipe<?>> RecipeType<R> register(Identifier id, RecipeType<R> type) {
        return Registry.register(Registries.RECIPE_TYPE, id, type);
    }
}
