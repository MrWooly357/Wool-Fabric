package net.mrwooly357.wool.registry;

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
     * @param screenHandlerType the {@link ScreenHandlerType}.
     * @param <Type> the type of {@link ScreenHandler}.
     * @return a registered {@link ScreenHandlerType}.
     */
    static <Type extends ScreenHandler> ScreenHandlerType<Type> register(Identifier id, ScreenHandlerType<Type> screenHandlerType) {
        return Registry.register(Registries.SCREEN_HANDLER, id, screenHandlerType);
    }
}
