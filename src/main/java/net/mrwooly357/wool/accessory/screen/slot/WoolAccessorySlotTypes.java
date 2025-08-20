package net.mrwooly357.wool.accessory.screen.slot;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.accessory.screen.slot.custom.AccessorySlotType;
import net.mrwooly357.wool.registry.WoolRegistries;
import net.mrwooly357.wool.util.misc.WoolItemTags;

public class WoolAccessorySlotTypes {

    public static final AccessorySlotType BELT = register("belt", AccessorySlotType.builder()
                    .icon(Identifier.of(Wool.MOD_ID, "belt"))
                    .tag(WoolItemTags.BELT_ACCESSORIES)
                    .build()
    );
    public static final AccessorySlotType RING = register("ring", AccessorySlotType.builder()
                    .icon(Identifier.of(Wool.MOD_ID, "ring"))
                    .tag(WoolItemTags.RING_ACCESSORIES)
                    .build()
    );
    public static final AccessorySlotType NECKLACE = register("necklace", AccessorySlotType.builder()
                    .icon(Identifier.of(Wool.MOD_ID, "necklace"))
                    .tag(WoolItemTags.NECKLACE_ACCESSORIES)
                    .build()
    );


    private static AccessorySlotType register(String name, AccessorySlotType type) {
        return Registry.register(WoolRegistries.ACCESSORY_SLOT_TYPE, Identifier.of(Wool.MOD_ID, name), type);
    }

    public static void initialize() {
        Wool.logInitializing("accessory slot types");
    }
}
