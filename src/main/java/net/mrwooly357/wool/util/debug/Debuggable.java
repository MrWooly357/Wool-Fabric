package net.mrwooly357.wool.util.debug;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.mrwooly357.wool.util.misc.NbtSerializable;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;

@Debug
public interface Debuggable {

    String DEBUG_DATA_KEY = "DebugData";
    String DEBUG_SETTINGS_KEY = "DebugSettings";


    DebugData getDebugData();

    Settings getDebugSettings();


    final class Setting<V extends Setting.Value> implements NbtSerializable {

        private final byte index;
        private final String name;
        private V value;
        private final List<V> values;

        String INDEX_KEY = "Index";
        String VALUE_KEY = "Value";

        @SafeVarargs
        public Setting(byte index, String name, V... values) {
            this.index = index;
            this.name = name;
            this.values = Arrays.stream(values)
                    .toList();
        }


        public byte getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }

        public V getValue() {
            return value;
        }

        public void setValue(byte index) {
            this.value = values.get(index);
        }

        @Override
        public NbtCompound toNbt(RegistryWrapper.WrapperLookup registryLookup) {
            NbtCompound nbt = new NbtCompound();

            nbt.putByte(INDEX_KEY, index);
            nbt.putByte(VALUE_KEY, value.getIndex());

            return nbt;
        }


        public interface Value {


            byte getIndex();

            String getName();
        }
    }


    final class Settings implements NbtSerializable {

        private final Map<Byte, Setting<?>> settings = new HashMap<>();

        private static final String SIZE_KEY = "Size";
        private static final String SETTING_KEY = "Setting";

        public Settings(Setting<?>... settings) {
            this(Arrays.stream(settings)
                    .toList());
        }

        private Settings(List<Setting<?>> settings) {
            settings.forEach(setting -> this.settings.put(setting.index, setting));
        }


        public String get() {
            StringBuilder builder = new StringBuilder();

            for (Map.Entry<Byte, Setting<?>> entry : settings.entrySet()) {
                Setting<?> setting = entry.getValue();

                builder.append("Setting[Index: ")
                        .append(entry.getKey())
                        .append(", Name: ")
                        .append(setting.name)
                        .append(", Values: [");

                for (Setting.Value value : setting.values) {
                    builder
                            .append("Value[Index: ")
                            .append(value.getIndex())
                            .append(", Name: ")
                            .append(value.getName())
                            .append("]");

                    if (value != setting.values.getLast())
                        builder.append(", ");
                }

                builder.append("]");

                if (entry != settings.entrySet().stream().toList().getLast())
                    builder.append(", ");
            }

            return builder.toString();
        }

        @Nullable
        public Setting<?> getSetting(byte index) {
            return settings.get(index);
        }

        @Override
        public NbtCompound toNbt(RegistryWrapper.WrapperLookup registryLookup) {
            NbtCompound nbt = new NbtCompound();

            nbt.putByte(SIZE_KEY, (byte) settings.size());

            for (Setting<?> setting : settings.values())
                nbt.put(SETTING_KEY + setting.index, setting.toNbt(registryLookup));

            return nbt;
        }

        public static Settings fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, BiFunction<NbtCompound, RegistryWrapper.WrapperLookup, Setting<?>> settingDeserializer) {
            List<Setting<?>> settings = new ArrayList<>();

            byte size = nbt.getByte(SIZE_KEY);

            for (byte b = 0; b < size; b++)
                settings.add(settingDeserializer.apply(nbt.getCompound(SETTING_KEY + b), registryLookup));

            return new Settings(settings);
        }
    }
}
