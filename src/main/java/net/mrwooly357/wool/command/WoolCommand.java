package net.mrwooly357.wool.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.ConfigManager;

public class WoolCommand {


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        dispatcher.register(
                CommandManager.literal("wool")
                        .requires(source -> source.hasPermissionLevel(1))
                        .then(
                                CommandManager.literal("config")
                                        .then(
                                                CommandManager.literal("loadAll")
                                                        .executes(context -> executeLoadAll(context.getSource()))
                                        )
                                        .then(
                                                CommandManager.literal("resetToDefaultAll")
                                                        .executes(context -> executeResetToDefaultAll(context.getSource()))
                                        )
                        )
        );
    }

    private static int executeLoadAll(ServerCommandSource source) {

        ConfigManager.loadAll();
        source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.config.loadAll"), true);

        return 1;
    }

    private static int executeResetToDefaultAll(ServerCommandSource source) {

        ConfigManager.resetToDefaultAll();
        source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.config.resetToDefaultAll"), true);

        return 1;
    }
}
