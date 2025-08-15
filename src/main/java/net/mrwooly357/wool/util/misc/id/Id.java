package net.mrwooly357.wool.util.misc.id;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import org.jetbrains.annotations.Range;

import java.security.SecureRandom;
import java.util.*;

public final class Id {

    private final String key;

    private static final char[] AVAILABLE_SYMBOLS = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            ',' , '.', '?', '!', '+', '-', '*', '/', '~', '@', '#', '&', ';', ':', '%', '=', '<', '>', '^', '(', ')', '[', ']', '{', '}', '|', '$', '`'
    };
    private static final Set<String> EXISTING_KEYS = new HashSet<>();
    private static final Set<String> EXISTING_PACKED_KEYS = new HashSet<>();

    public static final Codec<Id> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.STRING.fieldOf("key").forGetter(Id::getKey)
            ).apply(instance, Id::new)
    );
    public static final PacketCodec<PacketByteBuf, Id> PACKET_CODEC = new PacketCodec<>() {


        @Override
        public Id decode(PacketByteBuf buf) {
            return new Id(buf.readString());
        }

        @Override
        public void encode(PacketByteBuf buf, Id id) {
            buf.writeString(id.key);
        }
    };

    private Id(String key) {
        this.key = key;
    }


    public String getKey() {
        return key;
    }

    public static Id create(@Range(from = 1, to = Integer.MAX_VALUE) int length) {
        String key = "";
        StringBuilder builder = new StringBuilder();

        while (!EXISTING_KEYS.contains(key)) {
            SecureRandom random = new SecureRandom();

            for (int i = 0; i < length; i++)
                builder.append(AVAILABLE_SYMBOLS[random.nextInt(AVAILABLE_SYMBOLS.length)]);

            key = builder.toString();
            builder = new StringBuilder();

            if (!EXISTING_KEYS.contains(key)) {
                EXISTING_KEYS.add(key);

                break;
            }
        }

        return new Id(key);
    }

    public static List<Id> restore() {
        return restore(EXISTING_PACKED_KEYS);
    }

    public static List<Id> restore(Set<String> packedKeys) {
        List<Id> ids = new ArrayList<>();

        for (String packedKey : packedKeys) {
            Id id = new Packed(packedKey).unpack();

            ids.add(id);
        }

        return ids;
    }

    public void destroy() {
        EXISTING_KEYS.remove(key);
        EXISTING_PACKED_KEYS.remove(pack().packedKey);
    }

    public Packed pack() {
        char[] chars = key.toCharArray();
        int length = chars.length;
        StringBuilder builder = new StringBuilder();
        int charAmount = 1;

        for (int i = 0; i < length; i++) {
            char character = chars[i];
            int nextIndex = i + 1;
            char next = ' ';

            if (nextIndex < length)
                next = chars[nextIndex];

            if (next == character) {
                charAmount++;
            } else {
                builder
                        .append(charAmount)
                        .append(character)
                        .append("—");

                charAmount = 1;
            }
        }

        String packedKey = builder.toString();

        EXISTING_KEYS.remove(key);

        if (!EXISTING_PACKED_KEYS.contains(packedKey)) {
            EXISTING_PACKED_KEYS.add(packedKey);
        } else

            try {
                throw new ExistingPackedKeyException(key);
            } catch (ExistingPackedKeyException exception) {
                throw new RuntimeException(exception);
            }

        return new Packed(packedKey);
    }

    @Override
    public String toString() {
        return "Id[Key: " + key + "]";
    }

    public static Set<String> getExistingPackedKeys() {
        return Set.copyOf(EXISTING_PACKED_KEYS);
    }


    public static class Packed {

        private final String packedKey;

        private static final String PACKED_KEY_KEY = "PackedKey";

        private Packed(String packedKey) {
            this.packedKey = packedKey;
        }


        public String getKey() {
            return packedKey;
        }

        public Id unpack() {
            String key = getUnpackedKey();

            if (!EXISTING_KEYS.contains(key)) {
                EXISTING_KEYS.add(key);
                EXISTING_PACKED_KEYS.remove(packedKey);
            } else

                try {
                    throw new ExistingKeyException(key);
                } catch (ExistingKeyException exception) {
                    throw new RuntimeException(exception);
                }

            return new Id(key);
        }

        private String getUnpackedKey() {
            StringBuilder builder = new StringBuilder();
            List<Character> characters = new ArrayList<>();
            char[] chars = packedKey.toCharArray();

            for (char character : chars)
                characters.add(character);

            while (!characters.isEmpty()) {
                StringBuilder pairBuilder = new StringBuilder();
                String pair = "";

                while (pair.isEmpty() || pair.charAt(pair.length() - 1) != '—') {
                    pairBuilder.append(characters.getFirst());
                    characters.removeFirst();

                    pair = pairBuilder.toString();
                }

                int amount = Integer.parseInt(pair.substring(0, pair.length() - 2));
                char character = pair.charAt(pair.length() - 2);

                builder.append(String.valueOf(character).repeat(Math.max(0, amount)));
            }

            return builder.toString();
        }

        @Override
        public String toString() {
            return "PackedId[Key: " + packedKey + "]";
        }

        public NbtCompound toNbt() {
            NbtCompound nbt = new NbtCompound();

            nbt.putString(PACKED_KEY_KEY, packedKey);

            return nbt;
        }

        public static Packed fromNbt(NbtCompound nbt) {
            return new Packed(nbt.getString(PACKED_KEY_KEY));
        }
    }


    private static class ExistingKeyException extends Exception {

        private ExistingKeyException(String key) {
            super("Id's key already exists. Given key: " + key);
        }
    }


    private static class ExistingPackedKeyException extends Exception {

        private ExistingPackedKeyException(String key) {
            super("Id's key already exists as packed. Can't pack the same key twice! Given key: " + key);
        }
    }
}
