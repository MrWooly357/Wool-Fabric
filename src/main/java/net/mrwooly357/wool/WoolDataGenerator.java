package net.mrwooly357.wool;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.mrwooly357.wool.data_gen.WoolEnUKLanguageProvider;
import net.mrwooly357.wool.data_gen.WoolEnUSLanguageProvider;
import net.mrwooly357.wool.data_gen.WoolRuRuLanguageProvider;

public final class WoolDataGenerator implements DataGeneratorEntrypoint {

	private static final boolean GENERATE = false;


	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		if (GENERATE) {
			FabricDataGenerator.Pack pack = generator.createPack();
			pack.addProvider(WoolEnUSLanguageProvider::new);
			pack.addProvider(WoolEnUKLanguageProvider::new);
			pack.addProvider(WoolRuRuLanguageProvider::new);
		}
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);

		if (GENERATE) {}
	}
}
