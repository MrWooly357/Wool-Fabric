package net.mrwooly357.wool.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.ConfigManagersRegistry;

public final class ServerConfigCommand {


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("server_config")
                        .then(
                                CommandManager.literal("save")
                                        .then(
                                                CommandManager.literal("general")
                                                        .then(
                                                                CommandManager.argument("id", StringArgumentType.word())
                                                                        .suggests((context, builder) -> CommandSource.suggestMatching(ConfigManagersRegistry.getAllGeneralServerIds(), builder))
                                                                        .executes(context -> {
                                                                            String id = StringArgumentType.getString(context, "id");
                                                                            ConfigManagersRegistry.saveGeneralServer(id);
                                                                            context.getSource().sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".server_config.general.save.feedback", id), false);

                                                                            return Command.SINGLE_SUCCESS;
                                                                        })
                                                        )
                                        )
                                        .then(
                                                CommandManager.literal("world")
                                                        .then(
                                                                CommandManager.argument("id", StringArgumentType.word())
                                                                        .suggests((context, builder) -> CommandSource.suggestMatching(ConfigManagersRegistry.getAllServerWorldIds(), builder))
                                                                        .executes(context -> {
                                                                            String id = StringArgumentType.getString(context, "id");
                                                                            ServerCommandSource source = context.getSource();
                                                                            ConfigManagersRegistry.saveServerWorld(id, source.getServer().getSaveProperties().getLevelName(), source.getRegistryManager());
                                                                            context.getSource().sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".server_config.world.save.feedback", id), false);

                                                                            return Command.SINGLE_SUCCESS;
                                                                        })
                                                        )
                                        )

                        )
                        .then(
                                CommandManager.literal("load")
                                        .then(
                                                CommandManager.literal("general")
                                                        .then(
                                                                CommandManager.argument("id", StringArgumentType.word())
                                                                        .suggests((context, builder) -> CommandSource.suggestMatching(ConfigManagersRegistry.getAllGeneralServerIds(), builder))
                                                                        .executes(context -> {
                                                                            String id = StringArgumentType.getString(context, "id");
                                                                            ConfigManagersRegistry.loadGeneralServer(id);
                                                                            context.getSource().sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".server_config.general.load.feedback", id), false);

                                                                            return Command.SINGLE_SUCCESS;
                                                                        })
                                                        )
                                        )
                                        .then(
                                                CommandManager.literal("world")
                                                        .then(
                                                                CommandManager.argument("id", StringArgumentType.word())
                                                                        .suggests((context, builder) -> CommandSource.suggestMatching(ConfigManagersRegistry.getAllServerWorldIds(), builder))
                                                                        .executes(context -> {
                                                                            String id = StringArgumentType.getString(context, "id");
                                                                            ServerCommandSource source = context.getSource();
                                                                            ConfigManagersRegistry.loadServerWorld(id, source.getServer().getSaveProperties().getLevelName(), source.getRegistryManager());
                                                                            context.getSource().sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".server_config.world.load.feedback", id), false);

                                                                            return Command.SINGLE_SUCCESS;
                                                                        })
                                                        )
                                        )
                        )
        );
    }
}
