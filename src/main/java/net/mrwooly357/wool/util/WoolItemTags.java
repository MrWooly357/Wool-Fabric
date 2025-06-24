package net.mrwooly357.wool.util;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;

public class WoolItemTags {

    public static final TagKey<Item> GLASSES_ACCESSORIES = create("glasses_accessories");


    public static TagKey<Item> create(String name) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(Wool.MOD_ID, name));
    }

    public static void initialize() {
        if (WoolConfig.developerMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " item tags");
    }
}
