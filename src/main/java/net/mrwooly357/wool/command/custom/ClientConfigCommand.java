package net.mrwooly357.wool.command.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.command.CommandSource;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.text.Text;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.ConfigManagersRegistry;

@Environment(EnvType.CLIENT)
public final class ClientConfigCommand {


    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
                ClientCommandManager.literal("client_config")
                        .then(
                                ClientCommandManager.literal("save")
                                        .then(
                                                ClientCommandManager.literal("general")
                                                        .then(
                                                                ClientCommandManager.argument("id", StringArgumentType.word())
                                                                        .suggests((context, builder) -> CommandSource.suggestMatching(ConfigManagersRegistry.getAllGeneralServerIds(), builder))
                                                                        .executes(context -> {
                                                                            String id = StringArgumentType.getString(context, "id");
                                                                            ConfigManagersRegistry.saveGeneralClient(id);
                                                                            context.getSource().sendFeedback(Text.translatable("command." + Wool.MOD_ID + ".client_config.general.save.feedback", id));

                                                                            return Command.SINGLE_SUCCESS;
                                                                        })
                                                        )
                                        )
                                        .then(
                                                ClientCommandManager.literal("world")
                                                        .then(
                                                                ClientCommandManager.argument("id", StringArgumentType.word())
                                                                        .suggests((context, builder) -> CommandSource.suggestMatching(ConfigManagersRegistry.getAllServerWorldIds(), builder))
                                                                        .executes(context -> {
                                                                            String id = StringArgumentType.getString(context, "id");
                                                                            FabricClientCommandSource source = context.getSource();
                                                                            ConfigManagersRegistry.saveServerWorld(id, getClientWorldName(source), source.getRegistryManager());
                                                                            context.getSource().sendFeedback(Text.translatable("command." + Wool.MOD_ID + ".client_config.world.save.feedback", id));

                                                                            return Command.SINGLE_SUCCESS;
                                                                        })
                                                        )
                                        )

                        )
                        .then(
                                ClientCommandManager.literal("load")
                                        .then(
                                                ClientCommandManager.literal("general")
                                                        .then(
                                                                ClientCommandManager.argument("id", StringArgumentType.word())
                                                                        .suggests((context, builder) -> CommandSource.suggestMatching(ConfigManagersRegistry.getAllGeneralServerIds(), builder))
                                                                        .executes(context -> {
                                                                            String id = StringArgumentType.getString(context, "id");
                                                                            ConfigManagersRegistry.loadGeneralClient(id);
                                                                            context.getSource().sendFeedback(Text.translatable("command." + Wool.MOD_ID + ".client_config.general.load.feedback", id));

                                                                            return Command.SINGLE_SUCCESS;
                                                                        })
                                                        )
                                        )
                                        .then(
                                                ClientCommandManager.literal("world")
                                                        .then(
                                                                ClientCommandManager.argument("id", StringArgumentType.word())
                                                                        .suggests((context, builder) -> CommandSource.suggestMatching(ConfigManagersRegistry.getAllServerWorldIds(), builder))
                                                                        .executes(context -> {
                                                                            String id = StringArgumentType.getString(context, "id");
                                                                            FabricClientCommandSource source = context.getSource();
                                                                            ConfigManagersRegistry.loadServerWorld(id, getClientWorldName(source), source.getRegistryManager());
                                                                            context.getSource().sendFeedback(Text.translatable("command." + Wool.MOD_ID + ".client_config.world.load.feedback", id));

                                                                            return Command.SINGLE_SUCCESS;
                                                                        })
                                                        )
                                        )
                        )
        );
    }

    private static String getClientWorldName(FabricClientCommandSource source) {
        MinecraftClient client = source.getClient();
        String name;
        IntegratedServer server = client.getServer();

        if (server != null)
            name = server.getSaveProperties().getLevelName();
        else {
            ServerInfo info = client.getCurrentServerEntry();

            if (info != null)
                name = info.name;
            else
                name = "unknown";
        }

        return name;
    }
}
