package net.mrwooly357.wool.registry.custom;

import net.minecraft.client.recipebook.RecipeBookGroup;
import net.mrwooly357.wool.util.collection.map.MapUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CustomRecipeBookGroupsRegistry {

    private static final Map<RecipeBookGroup, List<RecipeBookGroup>> SEARCH_MAP = new HashMap<>();

    private CustomRecipeBookGroupsRegistry() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate CustomRecipeBookGroupsRegistry!");
    }


    public static void register(RecipeBookGroup group, RecipeBookGroup... search) {
        SEARCH_MAP.put(group, List.of(search));
    }

    public static Map<RecipeBookGroup, List<RecipeBookGroup>> getCombinedSearchMap() {
        return MapUtil.immutableCopyWith(RecipeBookGroup.SEARCH_MAP, SEARCH_MAP);
    }
}
