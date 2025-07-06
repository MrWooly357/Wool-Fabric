package net.mrwooly357.wool.registry.helper;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * A helper used for registering custom {@link RecipeSerializer}s.
 */
public interface RecipeSerializerRegistryHelper {


    /**
     * Registers a custom {@link RecipeSerializer}.
     * @param id the {@link Identifier}.
     * @param serializer the {@link RecipeSerializer}.
     * @return a registered {@link RecipeSerializer}.
     * @param <R> the {@link Recipe}.
     */
    static <R extends Recipe<?>> RecipeSerializer<R> register(Identifier id, RecipeSerializer<R> serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, id, serializer);
    }
}
