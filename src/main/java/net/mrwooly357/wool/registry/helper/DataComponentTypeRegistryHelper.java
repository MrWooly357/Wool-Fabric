package net.mrwooly357.wool.registry.helper;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

/**
 * A helper used for registering custom {@link ComponentType}s.
 */
public interface DataComponentTypeRegistryHelper {


    /**
     * Registers a custom {@link ComponentType}.
     * @param id the {@link Identifier}.
     * @param builderOperator the {@link UnaryOperator builder operator}.
     * @return a registered {@link ComponentType}.
     * @param <T> the type of data.
     */
    static <T> ComponentType<T> register(Identifier id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply(ComponentType.builder()).build());
    }
}
