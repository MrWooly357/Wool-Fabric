package net.mrwooly357.wool;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.block.util.WoolMultiblockConstructionBlueprints;
import net.mrwooly357.wool.command.WoolCommand;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.entity.util.EntityTypeAccessoryInventoryManager;
import net.mrwooly357.wool.entity.util.WoolEmptyAccessoryInventory;
import net.mrwooly357.wool.entity.util.WoolEntityAccessoryInventories;
import net.mrwooly357.wool.entity.util.WoolPlayerAccessoryInventory;
import net.mrwooly357.wool.screen.slot.custom.accessory.WoolAccessorySlotTypes;
import net.mrwooly357.wool.registry.ConfigRegistryHelper;
import net.mrwooly357.wool.registry.WoolRegistries;
import net.mrwooly357.wool.registry.WoolRegistryKeys;
import net.mrwooly357.wool.util.WoolItemTags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wool implements ModInitializer, WoolEntrypoint {

	public static final String MOD_ID = "wool";
	public static final Logger LOGGER = LoggerFactory.getLogger("Wool");
	public static final Config CONFIG = new WoolConfig();


	@Override
	public void onInitialize() {
		ConfigRegistryHelper.register(Identifier.of(MOD_ID, "config"), CONFIG);
		WoolRegistries.initialize();
		WoolRegistryKeys.initialize();
		WoolItemTags.initialize();
		WoolMultiblockConstructionBlueprints.initialize();
		CommandRegistrationCallback.EVENT.register((dispatcher, access, environment) -> WoolCommand.register(dispatcher, access));
		WoolAccessorySlotTypes.initialize();
		EntityTypeAccessoryInventoryManager.initialize();
		EntityTypeAccessoryInventoryManager.addToEntityTypeToRegistry(EntityType.PLAYER, WoolRegistries.PLAYER_ACCESSORY_INVENTORY);
		EntityTypeAccessoryInventoryManager.addToRegistryTyRegistryKey(WoolRegistries.PLAYER_ACCESSORY_INVENTORY, Identifier.of(MOD_ID, "player_accessory_inventory"));
		WoolEntityAccessoryInventories.initialize();
		WoolEmptyAccessoryInventory.initialize();
		WoolPlayerAccessoryInventory.initialize();
		WoolEntrypoint.invokeEntrypoint();
		onWoolInitialized();
	}

	@Override
	public void onWoolInitialized() {
		if (WoolConfig.developerMode)
			Wool.LOGGER.info("Wool initialized");
	}
}
