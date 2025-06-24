package net.mrwooly357.wool.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.ConfigManager;
import net.mrwooly357.wool.entity.util.AccessoryInventoryHolder;
import net.mrwooly357.wool.entity.util.EntityTypeAccessoryInventoryManager;
import net.mrwooly357.wool.registry.WoolRegistries;
import net.mrwooly357.wool.registry.WoolRegistryKeys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WoolCommand {

    private static final Text WOOL = Texts.bracketed(Text.translatable("command." + Wool.MOD_ID + ".wool.wool"))
            .styled(
            style -> style.withColor(Formatting.YELLOW)
                    .withClickEvent(
                            new ClickEvent(
                                    ClickEvent.Action.SUGGEST_COMMAND, "/wool "
                            )
                    )
                    .withHoverEvent(
                            new HoverEvent(
                                    HoverEvent.Action.SHOW_TEXT, Text.translatable("chat." + Wool.MOD_ID + ".clickToInsertCommand")
                            )
                    )
    );
    private static final DynamicCommandExceptionType INVALID_CONFIG_ID_EXCEPTION = new DynamicCommandExceptionType(
            id -> Text.stringifiedTranslatable("command." + Wool.MOD_ID + ".wool.config.invalid_id", WOOL, id)
    );


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access) {
        dispatcher.register(
                CommandManager.literal("wool")
                        .requires(source -> source.hasPermissionLevel(3))
                        .then(
                                CommandManager.literal("config")
                                        .then(
                                                CommandManager.argument("id", RegistryEntryReferenceArgumentType.registryEntry(access, WoolRegistryKeys.CONFIG))
                                                        .then(
                                                                CommandManager.literal("load")
                                                                        .executes(context -> ConfigCommand.executeLoad(context.getSource(), WoolCommand.ConfigCommand.getConfig(context)))
                                                        )
                                                        .then(
                                                                CommandManager.literal("resetToDefault")
                                                                        .executes(context -> ConfigCommand.executeResetToDefault(context.getSource(),  WoolCommand.ConfigCommand.getConfig(context)))
                                                        )
                                        )
                                        .then(
                                                CommandManager.literal("loadAll")
                                                        .executes(context -> ConfigCommand.executeLoadAll(context.getSource()))
                                        )
                                        .then(
                                                CommandManager.literal("resetToDefaultAll")
                                                        .executes(context -> ConfigCommand.executeResetToDefaultAll(context.getSource()))
                                        )
                        )
                        .then(
                                CommandManager.literal("accessory")
                                        .then(
                                                CommandManager.argument("targets", EntityArgumentType.entities())
                                                        .then(
                                                                CommandManager.argument("unit", IdentifierArgumentType.identifier())
                                                                        .then(
                                                                                CommandManager.literal("get")
                                                                                        .executes(context -> AccessoryCommand.executeGet(context.getSource(), EntityArgumentType.getEntities(context, "targets"), IdentifierArgumentType.getIdentifier(context, "unit")))
                                                                        )
                                                                        .then(
                                                                                CommandManager.literal("set")
                                                                                        .then(
                                                                                                CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                                                                        .executes(context -> AccessoryCommand.executeSet(context.getSource(), EntityArgumentType.getEntities(context, "targets"), IdentifierArgumentType.getIdentifier(context, "unit"), ItemStackArgumentType.getItemStackArgument(context, "item"), 1))
                                                                                                        .then(
                                                                                                                CommandManager.argument("count", IntegerArgumentType.integer(1, 64))
                                                                                                                        .executes(context -> AccessoryCommand.executeSet(context.getSource(), EntityArgumentType.getEntities(context, "targets"), IdentifierArgumentType.getIdentifier(context, "unit"), ItemStackArgumentType.getItemStackArgument(context, "item"), IntegerArgumentType.getInteger(context, "count")))
                                                                                                        )
                                                                                        )

                                                                        )
                                                        )
                                        )
                        )
        );
    }


    private static class ConfigCommand {


        private static Identifier getConfig(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
            RegistryEntry.Reference<Config> reference = RegistryEntryReferenceArgumentType.getRegistryEntry(context, "id", WoolRegistryKeys.CONFIG);
            Identifier id = WoolRegistries.CONFIG.getId(reference.value());

            if (id == null) {
                throw INVALID_CONFIG_ID_EXCEPTION.create(reference.registryKey().getValue().toString());
            } else
                return id;
        }

        private static int executeLoad(ServerCommandSource source, Identifier id) {
            Config config = ConfigManager.getIdToConfig().get(id);

            if (config != null) {
                config.load();
                source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.config.load", WOOL, Text.translatable("chat." + Wool.MOD_ID + ".configInfo", id.toString()).formatted(Formatting.GREEN)), true);
            }

            return 1;
        }

        private static int executeResetToDefault(ServerCommandSource source, Identifier id) {
            Config config = ConfigManager.getIdToConfig().get(id);

            if (config != null) {
                config.resetToDefault();
                source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.config.resetToDefault", WOOL, Text.translatable("chat." + Wool.MOD_ID + ".configInfo", id.toString()).formatted(Formatting.GREEN)), true);
            }

            return 1;
        }

        private static int executeLoadAll(ServerCommandSource source) {
            ConfigManager.loadAll();
            source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.config.loadAll", WOOL), true);

            return 1;
        }

        private static int executeResetToDefaultAll(ServerCommandSource source) {
            ConfigManager.resetToDefaultAll();
            source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.config.resetToDefaultAll", WOOL), true);

            return 1;
        }
    }


    private static class AccessoryCommand {


        private static int executeGet(ServerCommandSource source, Collection<? extends Entity> targets, Identifier unit) {
            List<Entity> valid = new ArrayList<>();

            for (Entity entity : targets) {

                if (entity instanceof AccessoryInventoryHolder holder && holder.isValid() && holder.getRegistry() != null && holder.getId() != null && holder.getAccessoryInventory() != null && !unit.toString().equals("wool:empty") && EntityTypeAccessoryInventoryManager.getEntityTypeToRegistry().containsKey(entity.getType())) {
                    ItemStack stack = holder.getAccessoryInventory().get(unit).getStack();

                    valid.add(entity);
                    source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.accessory.get", WOOL, Texts.bracketed(entity.getName()).styled(
                            style -> style
                                    .withColor(Formatting.GREEN)
                                    .withClickEvent(
                                            new ClickEvent(
                                                    ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + entity.getX() + " " + entity.getY() + " " + entity.getZ()
                                            )
                                    )
                                    .withHoverEvent(
                                            new HoverEvent(
                                                    HoverEvent.Action.SHOW_TEXT, Text.translatable("chat.coordinates.tooltip")
                                            )
                                    )
                    ), Text.translatable("chat." + Wool.MOD_ID + ".entityInfo", entity.getType().toString(), entity.getUuidAsString()).formatted(Formatting.DARK_GREEN), Text.translatable("chat." + Wool.MOD_ID + ".itemStackInfo", Registries.ITEM.getId(stack.getItem()).toString(), stack.getCount()).formatted(Formatting.AQUA), Text.translatable("chat." + Wool.MOD_ID + ".accessoryInventoryUnitInfo", unit.toString()).formatted(Formatting.DARK_AQUA)), true);
                }
            }

            return valid.size();
        }

        private static int executeSet(ServerCommandSource source, Collection<? extends Entity> targets, Identifier unit, ItemStackArgument item, int count) {
            List<Entity> valid = new ArrayList<>();

            for (Entity entity : targets) {

                if (entity instanceof AccessoryInventoryHolder holder && holder.isValid() && holder.getRegistry() != null && holder.getId() != null && holder.getAccessoryInventory() != null && !unit.toString().equals("wool:empty") && EntityTypeAccessoryInventoryManager.getEntityTypeToRegistry().containsKey(entity.getType())) {
                    ItemStack stack = new ItemStack(item.getItem(), count);

                    valid.add(entity);
                    holder.getAccessoryInventory().get(unit).setStack(stack);
                    source.sendFeedback(() -> Text.translatable(
                            "command." + Wool.MOD_ID + ".wool.accessory.set", WOOL, Text.translatable("chat." + Wool.MOD_ID + ".itemStackInfo", Registries.ITEM.getId(stack.getItem()).toString(), stack.getCount()).formatted(Formatting.AQUA), Text.translatable("chat." + Wool.MOD_ID + ".accessoryInventoryUnitInfo", unit.toString()).formatted(Formatting.DARK_AQUA), Texts.bracketed(entity.getName()).styled(
                                    style -> style
                                            .withColor(Formatting.GREEN)
                                            .withClickEvent(
                                                    new ClickEvent(
                                                            ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + entity.getX() + " " + entity.getY() + " " + entity.getZ()
                                                    )
                                            )
                                            .withHoverEvent(
                                                    new HoverEvent(
                                                            HoverEvent.Action.SHOW_TEXT, Text.translatable("chat.coordinates.tooltip")
                                                    )
                                            )
                            ), Text.translatable("chat." + Wool.MOD_ID + ".entityInfo", entity.getType().toString(), entity.getUuidAsString()).formatted(Formatting.DARK_GREEN)
                    ), true);
                }
            }

            return valid.size();
        }
    }
}
