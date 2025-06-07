package net.mrwooly357.wool;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.mrwooly357.wool.animation.AnimationLoader;
import net.mrwooly357.wool.animation.Animations;
import net.mrwooly357.wool.animation.condition.Conditions;
import net.mrwooly357.wool.animation.interpolation.Interpolations;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprints;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.ConfigManager;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.ModRegistries;
import net.mrwooly357.wool.registry.ModRegistryKeys;
import net.mrwooly357.wool.util.WoolInitializerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Wool implements ModInitializer, WoolInitializer {

	public static final String MOD_ID = "wool";
	public static final Logger LOGGER = LoggerFactory.getLogger("Wool");
	public static final Config CONFIG = new WoolConfig();


	@Override
	public void onInitialize() {
		ConfigManager.register(CONFIG);
		ModRegistries.initialize();
		ModRegistryKeys.initialize();
		Conditions.initialize();
		Interpolations.initialize();
		Animations.initialize();
		AnimationLoader.initialize();
		MultiblockConstructionBlueprints.initialize();
		WoolInitializerUtil.invokeEnvious();
	}

	@Override
	public void onWoolInitialize() {
		if (WoolConfig.developerMode) Wool.LOGGER.info("Wool initialized");
	}
}
