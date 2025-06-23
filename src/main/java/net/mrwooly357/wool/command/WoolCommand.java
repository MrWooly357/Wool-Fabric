package net.mrwooly357.wool.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.ConfigManager;
import net.mrwooly357.wool.entity.util.AccessoryInventoryHolder;
import net.mrwooly357.wool.entity.util.EntityTypeAccessoryInventoryManager;

import java.util.Collection;

public class WoolCommand {


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {

        dispatcher.register(
                CommandManager.literal("wool")
                        .requires(source -> source.hasPermissionLevel(3))
                        .then(
                                CommandManager.literal("config")
                                        .then(
                                                CommandManager.argument("id", IdentifierArgumentType.identifier())
                                                        .then(
                                                                CommandManager.literal("load")
                                                                        .executes(context -> Config.executeLoad(context.getSource(), IdentifierArgumentType.getIdentifier(context, "id")))
                                                        )
                                                        .then(
                                                                CommandManager.literal("resetToDefault")
                                                                        .executes(context -> Config.executeResetToDefault(context.getSource(), IdentifierArgumentType.getIdentifier(context, "id")))
                                                        )
                                        )
                                        .then(
                                                CommandManager.literal("loadAll")
                                                        .executes(context -> Config.executeLoadAll(context.getSource()))
                                        )
                                        .then(
                                                CommandManager.literal("resetToDefaultAll")
                                                        .executes(context -> Config.executeResetToDefaultAll(context.getSource()))
                                        )
                        )
                        .then(
                                CommandManager.literal("accessory")
                                        .then(
                                                CommandManager.argument("targets", EntityArgumentType.entities())
                                                        .then(
                                                                CommandManager.argument("slot", IdentifierArgumentType.identifier())
                                                                        .then(
                                                                                CommandManager.literal("get")
                                                                                        .executes(context -> Accessory.executeGet(context.getSource(), EntityArgumentType.getEntities(context, "targets"), IdentifierArgumentType.getIdentifier(context, "slot")))
                                                                        )
                                                                        .then(
                                                                                CommandManager.literal("set")
                                                                                        .then(
                                                                                                CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                                                                        .executes(context -> Accessory.executeSet(context.getSource(), EntityArgumentType.getEntities(context, "targets"), IdentifierArgumentType.getIdentifier(context, "slot"), ItemStackArgumentType.getItemStackArgument(context, "item"), 1))
                                                                                                        .then(
                                                                                                                CommandManager.argument("count", IntegerArgumentType.integer(1, 64))
                                                                                                                        .executes(context -> Accessory.executeSet(context.getSource(), EntityArgumentType.getEntities(context, "targets"), IdentifierArgumentType.getIdentifier(context, "slot"), ItemStackArgumentType.getItemStackArgument(context, "item"), IntegerArgumentType.getInteger(context, "count")))
                                                                                                        )
                                                                                        )

                                                                        )
                                                        )
                                        )
                        )
        );
    }


    private static class Config {


        private static int executeLoad(ServerCommandSource source, Identifier id) {
            System.out.println(ConfigManager.getIdToConfig());
            ConfigManager.getIdToConfig().get(id).load();
            System.out.println(ConfigManager.getIdToConfig().get(id));
            source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.config.load").append(Text.literal(id.toString())), true);

            return 1;
        }

        private static int executeResetToDefault(ServerCommandSource source, Identifier id) {
            ConfigManager.getIdToConfig().get(id).resetToDefault();
            source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.config.resetToDefault").append(Text.literal(id.toString())), true);

            return 1;
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


    private static class Accessory {


        private static int executeGet(ServerCommandSource source, Collection<? extends Entity> targets, Identifier slot) {
            for (Entity entity : targets) {

                if (EntityTypeAccessoryInventoryManager.getEntityTypeToRegistry().containsKey(entity.getType()) && entity instanceof AccessoryInventoryHolder holder) {
                    System.out.println(holder.getAccessoryInventory().get(slot).getStack());
                    //source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.accessory.get", targets.iterator().next().getDisplayName(), entity.getUuid(), holder.getAccessoryInventory().get(slot).getStack(), slot), true);
                }
            }

            return targets.size();
        }

        private static int executeSet(ServerCommandSource source, Collection<? extends Entity> targets, Identifier slot, ItemStackArgument item, int count) throws CommandSyntaxException {

            for (Entity entity : targets) {

                if (entity instanceof AccessoryInventoryHolder holder && holder.isValid()) {
                    System.out.println("slot " + holder.getAccessoryInventory().get(slot));
                    holder.getAccessoryInventory().get(slot).setStack(new ItemStack(item.getItem(), count));
                }

                source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.accessory.set"), true);
            }

            return targets.size();
        }
    }
}
