package net.mrwooly357.wool;

import net.fabricmc.api.ModInitializer;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wool implements ModInitializer {

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
	}
}
