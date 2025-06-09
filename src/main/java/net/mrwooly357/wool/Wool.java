package net.mrwooly357.wool;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprints;
import net.mrwooly357.wool.command.WoolCommand;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.ConfigManager;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.ModRegistries;
import net.mrwooly357.wool.registry.ModRegistryKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wool implements ModInitializer, WoolEntrypoint {

	public static final String MOD_ID = "wool";
	public static final Logger LOGGER = LoggerFactory.getLogger("Wool");
	public static final Config CONFIG = new WoolConfig();


	@Override
	public void onInitialize() {
		ConfigManager.register(CONFIG);
		ModRegistries.initialize();
		ModRegistryKeys.initialize();
		MultiblockConstructionBlueprints.initialize();
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> WoolCommand.register(dispatcher));
		WoolEntrypoint.invokeEntrypoint();
	}

	@Override
	public void onWoolInitialize() {
		if (WoolConfig.developerMode) Wool.LOGGER.info("Wool initialized");
	}
}
