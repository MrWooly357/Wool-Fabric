package net.mrwooly357.wool.util;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;

public final class ModResourceReloadListenerKeys {

    public static final Identifier ANIMATIONS = Identifier.of(Wool.MOD_ID, "animations");


    public static void init() {
        Wool.LOGGER.info("initializing " + Wool.MOD_ID + " resource reload listener keys");
    }
}
