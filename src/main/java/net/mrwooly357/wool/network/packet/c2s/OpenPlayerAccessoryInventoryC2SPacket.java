package net.mrwooly357.wool.network.packet.c2s;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;

public record OpenPlayerAccessoryInventoryC2SPacket() implements CustomPayload {

    public static final Id<OpenPlayerAccessoryInventoryC2SPacket> ID = new Id<>(
            Identifier.of(
                    Wool.MOD_ID, "open_player_accessory_inventory_c2s"
            )
    );
    public static final PacketCodec<PacketByteBuf, OpenPlayerAccessoryInventoryC2SPacket> CODEC = CustomPayload.codecOf(
            OpenPlayerAccessoryInventoryC2SPacket::write, OpenPlayerAccessoryInventoryC2SPacket::read
    );


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    private static void write(OpenPlayerAccessoryInventoryC2SPacket packet, PacketByteBuf buf) {}

    private static OpenPlayerAccessoryInventoryC2SPacket read(PacketByteBuf buf) {
        return new OpenPlayerAccessoryInventoryC2SPacket();
    }
}
