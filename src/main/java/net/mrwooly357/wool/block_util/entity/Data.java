package net.mrwooly357.wool.block_util.entity;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;

import java.util.function.BiConsumer;

public record Data(BiConsumer<NbtCompound, RegistryWrapper.WrapperLookup> serializer, BiConsumer<NbtCompound, RegistryWrapper.WrapperLookup> deserializer) {}
