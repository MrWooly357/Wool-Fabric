package net.mrwooly357.wool.util.enum_extensions;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.book.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import net.mrwooly357.wool.registry.custom.CustomRecipeBookCategoriesRegistry;
import net.mrwooly357.wool.registry.custom.CustomRecipeBookGroupsRegistry;

import java.util.Arrays;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public final class EnumExtenders {

    public static final EnumExtender<Formatting> FORMATTING = EnumExtender.create(
            Formatting.class,
            extender -> {
                Formatting.CODEC = StringIdentifiable.createCodec(Formatting::values);
                Formatting.BY_NAME = Arrays.stream(Formatting.values())
                        .collect(Collectors.toMap(formatting -> Formatting.sanitize(formatting.name), formatting -> formatting));
            },
            Pair.of(String.class, 0),
            Pair.of(char.class, 0),
            Pair.of(boolean.class, 0),
            Pair.of(String.class, 1),
            Pair.of(int.class, 0)
    );
    public static final EnumExtender<Rarity> RARITY = EnumExtender.create(
            Rarity.class,
            extender -> {
                Rarity.CODEC = StringIdentifiable.createBasicCodec(Rarity::values);
                Rarity.ID_TO_VALUE = ValueLists.createIdToValueFunction((ToIntFunction<Rarity>) rarity -> rarity.index, Rarity.values(), ValueLists.OutOfBoundsHandling.ZERO);
                Rarity.PACKET_CODEC = PacketCodecs.indexed(Rarity.ID_TO_VALUE, value -> value.index);
                extender.apply(ItemStack.class);
            },
            Pair.of(int.class, 0),
            Pair.of(String.class, 0),
            Pair.of(Formatting.class, 0)
    );
    public static final EnumExtender<SpawnReason> SPAWN_REASON = EnumExtender.create(SpawnReason.class);
    public static final EnumExtender<CraftingRecipeCategory> CRAFTING_RECIPE_CATEGORY = EnumExtender.create(
            CraftingRecipeCategory.class,
            extender -> {
                CraftingRecipeCategory.CODEC = StringIdentifiable.createCodec(CraftingRecipeCategory::values);
                CraftingRecipeCategory.INDEX_TO_VALUE = ValueLists.createIdToValueFunction(CraftingRecipeCategory::getIndex, CraftingRecipeCategory.values(), ValueLists.OutOfBoundsHandling.ZERO);
                CraftingRecipeCategory.PACKET_CODEC = PacketCodecs.indexed(CraftingRecipeCategory.INDEX_TO_VALUE, CraftingRecipeCategory::getIndex);
                extender.apply(ClientRecipeBook.class);
            },
            Pair.of(String.class, 0),
            Pair.of(int.class, 0)
    );
    public static final EnumExtender<CookingRecipeCategory> COOKING_RECIPE_CATEGORY = EnumExtender.create(
            CookingRecipeCategory.class,
            extender -> {
                CookingRecipeCategory.CODEC = StringIdentifiable.createCodec(CookingRecipeCategory::values);
                extender.apply(ClientRecipeBook.class);
            },
            Pair.of(String.class, 0)
    );
    public static final EnumExtender<RecipeCategory> RECIPE_CATEGORY = EnumExtender.create(
            RecipeCategory.class,
            extender -> extender.apply(CraftingRecipeJsonBuilder.class),
            Pair.of(String.class, 0)
    );
    public static final EnumExtender<RecipeBookCategory> RECIPE_BOOK_CATEGORY = EnumExtender.create(
            RecipeBookCategory.class,
            extender -> {
                RecipeBookOptions.CATEGORY_OPTION_NAMES = CustomRecipeBookCategoriesRegistry.getCombinedOptionNames();
                extender.apply(RecipeBookGroup.class);
            }
    );
    public static final EnumExtender<RecipeBookGroup> RECIPE_BOOK_GROUP = EnumExtender.create(
            RecipeBookGroup.class,
            extender -> RecipeBookGroup.SEARCH_MAP = CustomRecipeBookGroupsRegistry.getCombinedSearchMap(),
            Pair.of(ItemStack[].class, 0)
    );

    private EnumExtenders() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate EnumExtenders!");
    }
}
