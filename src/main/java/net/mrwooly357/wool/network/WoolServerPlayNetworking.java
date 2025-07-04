package net.mrwooly357.wool.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.network.packet.c2s.OpenPlayerAccessoryInventoryC2SPacket;
import net.mrwooly357.wool.screen.custom.accessory.PlayerAccessoryInventoryScreenHandler;

public class WoolServerPlayNetworking {


    public static void initialize() {
        PayloadTypeRegistry.playC2S().register(OpenPlayerAccessoryInventoryC2SPacket.ID, OpenPlayerAccessoryInventoryC2SPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(OpenPlayerAccessoryInventoryC2SPacket.ID, (payload, context) -> context.player().openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player) -> new PlayerAccessoryInventoryScreenHandler(syncId, player), Text.translatable("gui." + Wool.MOD_ID + ".player_accessory_inventory"))));
    }
}
