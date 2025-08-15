package net.mrwooly357.wool.util.debug;

import net.mrwooly357.wool.util.misc.Data;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public interface Debuggable {


    Data<DebugData> getDebugData();

    Settings getDebugSettings();


    final class Setting<T> extends Data<T> {

        private final String key;

        private Setting(@Nullable T initialValue, String key) {
            super(initialValue);

            this.key = key;
        }


        public String getKey() {
            return key;
        }
    }


    final class Settings {

        private final Map<String, Setting<?>> settings = new HashMap<>();

        public Settings(Setting<?>... settings) {
            Arrays.stream(settings)
                    .forEach(setting -> this.settings.put(setting.key, setting));
        }


        public Setting<?> get(String key) {
            return settings.get(key);
        }

        public void ifEqualTo(String key, Object value, Consumer<Object> action) {
            if (get(key).get() == value)
                action.accept(value);
        }

        public List<String> getKeys() {
            return settings.keySet().stream()
                    .toList();
        }
    }
}
