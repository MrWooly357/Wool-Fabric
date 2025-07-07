package net.mrwooly357.wool.network.packet.c2s;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;

public record CanTickAnimationUpdateC2SPacket(int entityId, boolean canTickAnimation) implements CustomPayload {

    public static final Id<CanTickAnimationUpdateC2SPacket> ID = new Id<>(
            Identifier.of(
                    Wool.MOD_ID, "can_tick_animation_update_c2s_packet"
            )
    );
    public static final PacketCodec<PacketByteBuf, CanTickAnimationUpdateC2SPacket> CODEC = CustomPayload.codecOf(
            CanTickAnimationUpdateC2SPacket::write, CanTickAnimationUpdateC2SPacket::read
    );


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    private static void write(CanTickAnimationUpdateC2SPacket packet, PacketByteBuf buf) {
        buf.writeVarInt(packet.entityId);
        buf.writeBoolean(packet.canTickAnimation);
    }

    private static CanTickAnimationUpdateC2SPacket read(PacketByteBuf buf) {
        return new CanTickAnimationUpdateC2SPacket(buf.readVarInt(), buf.readBoolean());
    }
}
