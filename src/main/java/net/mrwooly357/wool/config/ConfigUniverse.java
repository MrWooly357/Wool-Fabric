package net.mrwooly357.wool.config;

import net.minecraft.registry.DynamicRegistryManager;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class ConfigUniverse<SC extends Config<SC>, SWC extends Config<SWC>, CC extends Config<CC>, CWC extends Config<CWC>> {

    private volatile Optional<SC> server;
    private final Consumer<Access<SC>> serverReloader;
    private volatile Optional<SWC> serverWorld;
    private final BiConsumer<Access<SWC>, DynamicRegistryManager> serverWorldReloader;
    private volatile Optional<CC> client;
    private final Consumer<Access<CC>> clientReloader;
    private volatile Optional<CWC> clientWorld;
    private final BiConsumer<Access<CWC>, DynamicRegistryManager> clientWorldReloader;

    public ConfigUniverse(
            Optional<SC> defaultServer,
            Consumer<Access<SC>> serverReloader,
            Optional<SWC> defaultServerWorld,
            BiConsumer<Access<SWC>, DynamicRegistryManager> serverWorldReloader,
            Optional<CC> defaultClient,
            Consumer<Access<CC>> clientReloader,
            Optional<CWC> defaultClientWorld,
            BiConsumer<Access<CWC>, DynamicRegistryManager> clientWorldReloader
    ) {
        server = defaultServer;
        this.serverReloader = serverReloader;
        serverWorld = defaultServerWorld;
        this.serverWorldReloader = serverWorldReloader;
        client = defaultClient;
        this.clientReloader = clientReloader;
        clientWorld = defaultClientWorld;
        this.clientWorldReloader = clientWorldReloader;
    }


    public Optional<SC> getServer() {
        return server;
    }

    public Optional<SWC> getServerWorld() {
        return serverWorld;
    }

    public Optional<CC> getClient() {
        return client;
    }

    public Optional<CWC> getClientWorld() {
        return clientWorld;
    }

    public synchronized void reloadServer() {
        try (Access<SC> access = new Access<>(config -> server = Optional.ofNullable(config))) {
            serverReloader.accept(access);
        }
    }

    public synchronized void reloadServerWorld(DynamicRegistryManager registryManager) {
        try (Access<SWC> access = new Access<>(config -> serverWorld = Optional.ofNullable(config))) {
            serverWorldReloader.accept(access, registryManager);
        }
    }

    public synchronized void reloadClient() {
        try (Access<CC> access = new Access<>(config -> client = Optional.ofNullable(config))) {
            clientReloader.accept(access);
        }
    }

    public synchronized void reloadClientWorld(DynamicRegistryManager registryManager) {
        try (Access<CWC> access = new Access<>(config -> clientWorld = Optional.ofNullable(config))) {
            clientWorldReloader.accept(access, registryManager);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, serverWorld, client, clientWorld, serverReloader, clientReloader);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) || (other instanceof ConfigUniverse<?, ?, ?, ?> universe
                && server.equals(universe.server)
                && serverWorld.equals(universe.serverWorld)
                && client.equals(universe.client)
                && clientWorld.equals(universe.clientWorld)
                && serverReloader.equals(universe.serverReloader)
                && clientReloader.equals(universe.clientReloader));
    }

    @Override
    public String toString() {
        return "ConfigUniverse[server: " + server
                + ", server_world: " + serverWorld
                + ", client: " + client
                + ", client_world: " + clientWorld
                + ", server_reloader: " + serverReloader
                + ", client_reloader: " + clientReloader + "]";
    }


    public static final class Access<C extends Config<C>> implements AutoCloseable {

        private Consumer<C> setter;

        private Access(Consumer<C> setter) {
            this.setter = setter;
        }


        public void set(C config) {
            setter.accept(config);
        }

        @Override
        public void close() {
            setter = null;
        }

        @Override
        public int hashCode() {
            return Objects.hash(setter);
        }

        @Override
        public boolean equals(Object other) {
            return super.equals(other) || (other instanceof Access<?> access
                    && Objects.equals(setter, access.setter));
        }

        @Override
        public String toString() {
            return "ConfigUniverse.ServerAccess[general_setter: " + setter + "]";
        }
    }
}
