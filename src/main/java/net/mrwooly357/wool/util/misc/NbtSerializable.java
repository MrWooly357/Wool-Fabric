package net.mrwooly357.wool.util.misc;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;

public interface NbtSerializable {


    NbtCompound toNbt(RegistryWrapper.WrapperLookup lookup);
}
