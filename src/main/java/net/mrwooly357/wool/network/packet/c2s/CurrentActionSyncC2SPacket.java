package net.mrwooly357.wool.network.packet.c2s;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;

public record CurrentActionSyncC2SPacket(int entityId, String action) implements CustomPayload {

    public static final Id<CurrentActionSyncC2SPacket> ID = new Id<>(
            Identifier.of(
                    Wool.MOD_ID, "current_action_sync_c2s"
            )
    );
    public static final PacketCodec<PacketByteBuf, CurrentActionSyncC2SPacket> CODEC = CustomPayload.codecOf(
            CurrentActionSyncC2SPacket::write, CurrentActionSyncC2SPacket::read
    );


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    private static void write(CurrentActionSyncC2SPacket packet, PacketByteBuf buf) {
        buf.writeVarInt(packet.entityId);
        buf.writeString(packet.action);
    }

    private static CurrentActionSyncC2SPacket read(PacketByteBuf buf) {
        return new CurrentActionSyncC2SPacket(buf.readVarInt(), buf.readString());
    }
}
