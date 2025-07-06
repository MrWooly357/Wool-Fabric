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
     * @param <T> the {@link ScreenHandler}.
     */
    static <T extends ScreenHandler> ScreenHandlerType<T> register(Identifier id, ScreenHandlerType<T> type) {
        return Registry.register(Registries.SCREEN_HANDLER, id, type);
    }
}
