package net.mrwooly357.wool.util.debug;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.mrwooly357.wool.util.misc.NbtSerializable;
import net.mrwooly357.wool.util.misc.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Debug
public interface Debuggable {

    String DEBUG_DATA_KEY = "DebugData";
    String DEBUG_SETTINGS_KEY = "DebugSettings";


    DebugData getDebugData();

    Settings getDebugSettings();


    final class Setting<V extends Setting.Value> implements NbtSerializable {

        private final int index;
        private final String name;
        private V value;
        private final List<V> values;

        private static final String INDEX_KEY = "Index";
        private static final String VALUE_KEY = "Value";

        @SafeVarargs
        public Setting(int index, String name, V initialValue, V... values) {
            this.index = index;
            this.name = name;
            this.value = initialValue;
            this.values = Arrays.stream(values)
                    .toList();
        }


        public int getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }

        public V getValue() {
            return value;
        }

        public void setValue(int index) {
            this.value = values.get(index);
        }

        @Override
        public NbtCompound toNbt(RegistryWrapper.WrapperLookup registryLookup) {
            NbtCompound nbt = new NbtCompound();

            nbt.putInt(INDEX_KEY, index);
            nbt.putInt(VALUE_KEY, value.getIndex());

            return nbt;
        }


        public interface Value {


            byte getIndex();

            String getName();
        }
    }


    final class Settings implements NbtSerializable {

        private final Map<Integer, Setting<?>> settings = new HashMap<>();

        private static final String SIZE_KEY = "Size";
        private static final String SETTING_KEY = "Setting";

        public Settings(Setting<?>... settings) {
            this(Arrays.stream(settings)
                    .toList());
        }

        private Settings(List<Setting<?>> settings) {
            settings.forEach(setting -> this.settings.put(setting.index, setting));
        }


        public String getAsString() {
            StringBuilder builder = new StringBuilder();

            for (Map.Entry<Integer, Setting<?>> entry : settings.entrySet()) {
                Setting<?> setting = entry.getValue();

                builder.append("Setting[index: ")
                        .append(setting.index)
                        .append(", name: ")
                        .append(setting.name)
                        .append(", value: ")
                        .append(setting.value)
                        .append(", values: [");

                for (Setting.Value value : setting.values) {
                    builder.append("Value[index: ")
                            .append(value.getIndex())
                            .append(", name: ")
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
        public Setting<?> getSetting(int index) {
            return settings.get(index);
        }

        @Override
        public NbtCompound toNbt(RegistryWrapper.WrapperLookup registryLookup) {
            NbtCompound nbt = new NbtCompound();

            nbt.putInt(SIZE_KEY, settings.size());

            for (Setting<?> setting : settings.values())
                nbt.put(SETTING_KEY + setting.index, setting.toNbt(registryLookup));

            return nbt;
        }

        public static Settings fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, TriFunction<Integer, NbtCompound, RegistryWrapper.WrapperLookup, Setting<?>> settingDeserializer) {
            List<Setting<?>> settings = new ArrayList<>();

            int size = nbt.getInt(SIZE_KEY);

            for (int i = 0; i < size; i++)
                settings.add(settingDeserializer.apply(i, nbt.getCompound(SETTING_KEY + i), registryLookup));

            return new Settings(settings);
        }
    }
}
