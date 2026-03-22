package net.mrwooly357.wool;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;

public final class WoolDataGenerator implements DataGeneratorEntrypoint {

	private static final boolean GENERATE = false;


	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		if (GENERATE) {}
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);

		if (GENERATE) {}
	}
}
