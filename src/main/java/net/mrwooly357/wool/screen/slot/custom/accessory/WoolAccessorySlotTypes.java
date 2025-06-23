package net.mrwooly357.wool.screen.slot.custom.accessory;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.AccessorySlotTypeRegistryHelper;
import net.mrwooly357.wool.util.WoolItemTags;

public class WoolAccessorySlotTypes {

    public static final AccessorySlotType EMPTY = register(
            "empty", new AccessorySlotType(
                    Text.empty(), null, null
            )
    );
    public static final AccessorySlotType HEAD = register(
            "head", new AccessorySlotType(
                    Text.translatable("accessory_slot_type." + Wool.MOD_ID + ".head"), Identifier.of(Wool.MOD_ID, "textures/gui/accessory_slot/head"), WoolItemTags.HEAD_ACCESSORIES
            )
    );


    private static AccessorySlotType register(String name, AccessorySlotType slot) {
        return AccessorySlotTypeRegistryHelper.register(Identifier.of(Wool.MOD_ID, name), slot);
    }

    public static void initialize() {
        if (WoolConfig.developerMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " accessory slots");
    }
}
