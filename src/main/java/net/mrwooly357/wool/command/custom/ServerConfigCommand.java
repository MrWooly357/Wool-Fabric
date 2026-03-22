package net.mrwooly357.wool.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.mrwooly357.wool.config.ConfigManagersRegistry;

public final class ServerConfigCommand {


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("server_config")
                        .then(
                                CommandManager.literal("general")
                                        .then(
                                                CommandManager.argument("id", StringArgumentType.word())
                                                        .suggests((context, builder) -> CommandSource.suggestMatching(ConfigManagersRegistry.getAllGeneralIds(), builder))
                                                        .executes(context -> {
                                                            ConfigManagersRegistry.loadGeneralServer(StringArgumentType.getString(context, "id"));

                                                            return Command.SINGLE_SUCCESS;
                                                        })
                                        )
                        )
                        .then(
                                CommandManager.literal("world")
                        )
        );
    }
}
