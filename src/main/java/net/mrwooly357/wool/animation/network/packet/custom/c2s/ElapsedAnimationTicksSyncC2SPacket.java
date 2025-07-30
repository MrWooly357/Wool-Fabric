package net.mrwooly357.wool.animation.network.packet.custom.c2s;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;

public record ElapsedAnimationTicksSyncC2SPacket(int entityId, int elapsedAnimationTicks) implements CustomPayload {

    public static final Id<ElapsedAnimationTicksSyncC2SPacket> ID = new Id<>(
            Identifier.of(
                    Wool.MOD_ID, "elapsed_animation_ticks_sync_c2s"
            )
    );
    public static final PacketCodec<PacketByteBuf, ElapsedAnimationTicksSyncC2SPacket> CODEC = CustomPayload.codecOf(
            ElapsedAnimationTicksSyncC2SPacket::write, ElapsedAnimationTicksSyncC2SPacket::read
    );


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    private static void write(ElapsedAnimationTicksSyncC2SPacket packet, PacketByteBuf buf) {
        buf.writeVarInt(packet.entityId);
        buf.writeVarInt(packet.elapsedAnimationTicks);
    }

    private static ElapsedAnimationTicksSyncC2SPacket read(PacketByteBuf buf) {
        return new ElapsedAnimationTicksSyncC2SPacket(buf.readVarInt(), buf.readVarInt());
    }
}
