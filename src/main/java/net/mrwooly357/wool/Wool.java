package net.mrwooly357.wool;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;
import net.mrwooly357.wool.block_util.multiblock_construction.WoolMultiblockConstructionBlueprints;
import net.mrwooly357.wool.command.WoolCommand;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryManager;
import net.mrwooly357.wool.accessory.entity.inventory.WoolEntityAccessoryInventories;
import net.mrwooly357.wool.accessory.entity.inventory.custom.WoolPlayerAccessoryInventory;
import net.mrwooly357.wool.animation.interpolation.WoolInterpolations;
import net.mrwooly357.wool.network.packet.WoolServerPlayNetworking;
import net.mrwooly357.wool.accessory.screen.WoolScreenHandlerTypes;
import net.mrwooly357.wool.accessory.screen.slot.WoolAccessorySlotTypes;
import net.mrwooly357.wool.custom_biome.region.VanillaRegionTypes;
import net.mrwooly357.wool.registry.WoolRegistries;
import net.mrwooly357.wool.custom_biome.SurfaceRuleManager;
import net.mrwooly357.wool.custom_biome.event.RegionTypeServerLifecycleEvents;
import net.mrwooly357.wool.util.random.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public final class Wool implements ModInitializer {

	public static final String MOD_ID = "wool";
	public static final Logger LOGGER = LoggerFactory.getLogger("Wool");
	public static final WoolConfig CONFIG = new WoolConfig();


	@Override
	public void onInitialize() {
        // Basic stuff
        WoolRegistries.initialize();
        Config.Manager.register(Identifier.of(MOD_ID, "config"), CONFIG);
        CommandRegistrationCallback.EVENT.register((dispatcher, access, environment) -> WoolCommand.register(dispatcher, access));

        // Block util
        WoolMultiblockConstructionBlueprints.initialize();

        // Accessory
		AccessoryInventoryManager.ENTITY_TYPE_TO_REGISTRY.put(EntityType.PLAYER, WoolRegistries.PLAYER_ACCESSORY_INVENTORY);
		AccessoryInventoryManager.REGISTRY_TO_ID.put(WoolRegistries.PLAYER_ACCESSORY_INVENTORY, Identifier.of(MOD_ID, "player_accessory_inventory"));
		AccessoryInventoryManager.UNIT_ORDER.put(WoolRegistries.PLAYER_ACCESSORY_INVENTORY, new ArrayList<>());
        WoolAccessorySlotTypes.initialize();
		WoolEntityAccessoryInventories.initialize();
		WoolPlayerAccessoryInventory.initialize();

        // Custom biomes
        SurfaceRuleManager.addRegionType(VanillaRegionTypes.OVERWORLD, VanillaSurfaceRules.createOverworldSurfaceRule());
        RegionTypeServerLifecycleEvents.initialize();

        // Misc
		WoolScreenHandlerTypes.initialize();
        WoolServerPlayNetworking.initialize();

        // In development
        WoolInterpolations.initialize();
	}

    public static void logInitializing(String message) {
        if (WoolConfig.enableDeveloperMode)
            LOGGER.info("Initializing " + MOD_ID + " {}", message);
    }
}
