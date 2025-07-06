package net.mrwooly357.wool.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.entity.animation.Animatable;
import net.mrwooly357.wool.network.packet.c2s.CurrentActionSyncC2SPacket;
import net.mrwooly357.wool.network.packet.c2s.ElapsedAnimationTicksSyncC2SPacket;
import net.mrwooly357.wool.network.packet.c2s.OpenPlayerAccessoryInventoryC2SPacket;
import net.mrwooly357.wool.screen.custom.accessory.PlayerAccessoryInventoryScreenHandler;

public class WoolServerPlayNetworking {


    public static void initialize() {
        PayloadTypeRegistry.playC2S().register(OpenPlayerAccessoryInventoryC2SPacket.ID, OpenPlayerAccessoryInventoryC2SPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(OpenPlayerAccessoryInventoryC2SPacket.ID, (payload, context) -> context.player().openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player) -> new PlayerAccessoryInventoryScreenHandler(syncId, player), Text.translatable("gui." + Wool.MOD_ID + ".player_accessory_inventory"))));
        PayloadTypeRegistry.playC2S().register(CurrentActionSyncC2SPacket.ID, CurrentActionSyncC2SPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(CurrentActionSyncC2SPacket.ID, (payload, context) -> {
            if (context.player().getWorld().getEntityById(payload.entityId()) instanceof Animatable.Server serverAnimatable)
                serverAnimatable.setCurrentAction(serverAnimatable.getIdsToActions().get(Identifier.of(payload.action())));
        });
        PayloadTypeRegistry.playC2S().register(ElapsedAnimationTicksSyncC2SPacket.ID, ElapsedAnimationTicksSyncC2SPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ElapsedAnimationTicksSyncC2SPacket.ID, (payload, context) -> ((Animatable.Server) context.player().getWorld().getEntityById(payload.entityId())).setElapsedAnimationTicks(payload.elapsedAnimationTicks()));
    }
}
