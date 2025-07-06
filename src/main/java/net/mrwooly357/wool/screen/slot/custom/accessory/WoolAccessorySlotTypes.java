package net.mrwooly357.wool.screen.slot.custom.accessory;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.AccessorySlotTypeRegistryHelper;
import net.mrwooly357.wool.util.WoolItemTags;

public class WoolAccessorySlotTypes {

    public static final AccessorySlotType EMPTY = register(
            "empty", AccessorySlotType.builder()
                    .build()
    );
    public static final AccessorySlotType NECKLACE = register(
            "necklace", AccessorySlotType.builder()
                    .icon(
                            Identifier.of(
                                    Wool.MOD_ID, "necklace"
                            )
                    )
                    .tag(
                            WoolItemTags.NECKLACE_ACCESSORIES
                    )
                    .build()
    );
    public static final AccessorySlotType RING = register(
            "ring", AccessorySlotType.builder()
                    .icon(
                            Identifier.of(
                                    Wool.MOD_ID, "ring"
                            )
                    )
                    .tag(
                            WoolItemTags.RING_ACCESSORIES
                    )
                    .build()
    );
    public static final AccessorySlotType BELT = register(
            "belt", AccessorySlotType.builder()
                    .icon(
                            Identifier.of(
                                    Wool.MOD_ID, "belt"
                            )
                    )
                    .tag(
                            WoolItemTags.BELT_ACCESSORIES
                    )
                    .build()
    );


    private static AccessorySlotType register(String name, AccessorySlotType slot) {
        return AccessorySlotTypeRegistryHelper.register(Identifier.of(Wool.MOD_ID, name), slot);
    }

    public static void initialize() {
        if (WoolConfig.developerMode)
            Wool.LOGGER.info("Initializing " + Wool.MOD_ID + " accessory slots");
    }
}
