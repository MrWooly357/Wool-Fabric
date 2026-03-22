package net.mrwooly357.wool;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import net.mrwooly357.wool.command.WoolCommands;
import net.mrwooly357.wool.config.custom.wool.WoolConfig;
import net.mrwooly357.wool.event.WoolEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Wool implements ModInitializer {

	public static final String MOD_ID = "wool";
	public static final Logger LOGGER = LoggerFactory.getLogger("Wool");


	@Override
	public void onInitialize() {
		WoolConfig.register();
		WoolEvents.initializeServer();
		WoolCommands.initialize();
	}

	public static void logInitialization(String message) {
        LOGGER.info("Initializing " + MOD_ID + " {}.", message);
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
