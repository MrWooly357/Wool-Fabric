package net.mrwooly357.wool.registry.helper;

import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

/**
 * A helper used for registering custom {@link SensorType}s.
 */
public interface SensorTypeRegistryHelper {


    /**
     * Registers a custom {@link SensorType}.
     * @param id the {@link Identifier}.
     * @param factory the {@link Supplier sensor factory}
     * @return a registered {@link SensorType}.
     * @param <S> the {@link Sensor}.
     */
    static <S extends Sensor<?>> SensorType<S> register(Identifier id, Supplier<S> factory) {
        return Registry.register(Registries.SENSOR_TYPE, id, new SensorType<>(factory));
    }
}
