package net.mrwooly357.wool.particle_util;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public final class ParticleUtil {


    public static <PE extends ParticleEffect> void register(ParticleType<PE> type, ParticleFactory<PE> factory) {
        ParticleFactoryRegistry.getInstance().register(type, factory);
    }
}
