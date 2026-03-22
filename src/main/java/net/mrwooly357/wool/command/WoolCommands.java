package net.mrwooly357.wool.command;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.command.custom.ClientConfigCommand;
import net.mrwooly357.wool.command.custom.ServerConfigCommand;

public final class WoolCommands {

    private WoolCommands() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate WoolCommands!");
    }


    public static void initialize() {
        Wool.logInitialization("commands");
        register((dispatcher, registryAccess, environment) -> ServerConfigCommand.register(dispatcher));
    }

    public static void initializeClient() {
        Wool.logInitialization("client commands");
        registerClient((dispatcher, registryAccess) -> ClientConfigCommand.register(dispatcher));
    }

    private static void register(CommandRegistrationCallback command) {
        CommandRegistrationCallback.EVENT.register(command);
    }

    private static void registerClient(ClientCommandRegistrationCallback command) {
        ClientCommandRegistrationCallback.EVENT.register(command);
    }
}
