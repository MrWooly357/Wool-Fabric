package net.mrwooly357.wool.command;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.mrwooly357.wool.Wool;

public final class WoolCommands {

    private WoolCommands() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate WoolCommands!");
    }


    public static void initialize() {
        Wool.logInitialization("commands");
    }

    private static void register(CommandRegistrationCallback command) {
        CommandRegistrationCallback.EVENT.register(command);
    }
}
