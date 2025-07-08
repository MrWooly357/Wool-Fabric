package net.mrwooly357.wool.registry.helper;

import net.minecraft.screen.ScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

/**
 * A helper used for registering custom {@link ScreenHandlerType}s.
 */
public interface ScreenHandlerTypeRegistryHelper {


    /**
     * Registers a custom {@link ScreenHandlerType}.
     * @param id the {@link Identifier}.
     * @param type the {@link ScreenHandlerType}.
     * @return a registered {@link ScreenHandlerType}.
     * @param <H> the {@link ScreenHandler}.
     */
    static <H extends ScreenHandler> ScreenHandlerType<H> register(Identifier id, ScreenHandlerType<H> type) {
        return Registry.register(Registries.SCREEN_HANDLER, id, type);
    }
}
