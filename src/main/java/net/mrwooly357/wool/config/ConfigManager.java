package net.mrwooly357.wool.config;

import net.minecraft.registry.DynamicRegistryManager;
import net.mrwooly357.wool.config.custom.GeneralConfig;
import net.mrwooly357.wool.config.custom.WorldConfig;
import net.mrwooly357.wool.util.consumer.TriConsumer;

import java.util.Map;
import java.util.function.*;

public sealed interface ConfigManager {


    final class General<C extends Config> implements ConfigManager {

        private final BooleanSupplier doesNotExist;
        private final Consumer<C> saver;
        private final GeneralConfig.Instance<C> instance;
        private final Supplier<C> loader;

        public General(BooleanSupplier doesNotExist, Consumer<C> saver, GeneralConfig.Instance<C> instance, Supplier<C> loader) {
            this.doesNotExist = doesNotExist;
            this.saver = saver;
            this.instance = instance;
            this.loader = loader;
        }


        public boolean doesNotExist() {
            return doesNotExist.getAsBoolean();
        }

        public synchronized void save() {
            saver.accept(instance.get());
        }

        public synchronized void load() {
            instance.set(loader.get());
        }
    }


    final class World<C extends Config> implements ConfigManager {

        private final Function<String, Boolean> doesNotExist;
        private final TriConsumer<C, String, DynamicRegistryManager> saver;
        private final Map<String, WorldConfig.Instance<C>> instances;
        private final BiFunction<String, DynamicRegistryManager, C> loader;
        private final C initial;
        private final Consumer<String> deleter;

        public World(
                Function<String, Boolean> doesNotExist,
                TriConsumer<C, String, DynamicRegistryManager> saver,
                Map<String,  WorldConfig.Instance<C>> instances,
                BiFunction<String, DynamicRegistryManager, C> loader,
                C initial,
                Consumer<String> deleter
        ) {
            this.doesNotExist = doesNotExist;
            this.saver = saver;
            this.instances = instances;
            this.loader = loader;
            this.initial = initial;
            this.deleter = deleter;
        }


        public boolean doesNotExist(String name) {
            return doesNotExist.apply(name);
        }

        public synchronized void save(String name, DynamicRegistryManager registryManager) {
            saver.accept(getInstance(name).get(), name, registryManager);
        }

        public synchronized void load(String name, DynamicRegistryManager registryManager) {
            if (instances.size() >= 1024)
                instances.clear();

            getInstance(name).set(loader.apply(name, registryManager));
        }

        private Config.Instance<C> getInstance(String name) {
            return instances.computeIfAbsent(name, s -> new Config.Instance<>(initial));
        }

        public synchronized void delete(String name) {
            instances.remove(name);
            deleter.accept(name);
        }
    }
}
