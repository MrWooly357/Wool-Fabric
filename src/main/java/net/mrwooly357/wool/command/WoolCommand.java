package net.mrwooly357.wool.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
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
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryHolder;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryManager;
import net.mrwooly357.wool.registry.WoolRegistries;
import net.mrwooly357.wool.registry.WoolRegistryKeys;
import net.mrwooly357.wool.util.debug.Debuggable;
import net.mrwooly357.wool.util.text.TextKeys;
import net.mrwooly357.wool.util.text.TextUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class WoolCommand {

    private static final Text WOOL = Texts.bracketed(Text.translatable("command." + Wool.MOD_ID + ".wool.wool"))
            .styled(
            style -> style.withColor(Formatting.YELLOW)
                    .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/wool "))
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("chat." + Wool.MOD_ID + ".clickToInsertCommand")))
    );

    private static final DynamicCommandExceptionType INVALID_CONFIG_ID_EXCEPTION = new DynamicCommandExceptionType(id ->
            Text.stringifiedTranslatable("command." + Wool.MOD_ID + ".wool.config.invalid_config_id", WOOL, id)
    );


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access) {
        dispatcher.register(CommandManager.literal("wool")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.literal("config")
                        .then(CommandManager.argument("id", RegistryEntryReferenceArgumentType.registryEntry(access, WoolRegistryKeys.CONFIG))
                                .then(CommandManager.literal("load")
                                        .executes(context -> ConfigCommand.executeLoad(context.getSource(), WoolCommand.ConfigCommand.getConfig(context)))
                                )
                                .then(CommandManager.literal("resetToDefault")
                                        .executes(context -> ConfigCommand.executeResetToDefault(context.getSource(),  WoolCommand.ConfigCommand.getConfig(context)))
                                )
                        )
                        .then(CommandManager.literal("loadAll")
                                .executes(context -> ConfigCommand.executeLoadAll(context.getSource()))
                        )
                        .then(CommandManager.literal("resetToDefaultAll")
                                .executes(context -> ConfigCommand.executeResetToDefaultAll(context.getSource()))
                        )
                )
                .then(CommandManager.literal("accessory")
                        .then(CommandManager.argument("targets", EntityArgumentType.entities())
                                .then(CommandManager.argument("unit", IdentifierArgumentType.identifier())
                                        .then(CommandManager.literal("get")
                                                .executes(context ->
                                                        AccessoryCommand.executeGet(context.getSource(), EntityArgumentType.getEntities(context, "targets"), IdentifierArgumentType.getIdentifier(context, "unit")))
                                        )
                                        .then(CommandManager.literal("set")
                                                .then(CommandManager.argument("item", ItemStackArgumentType.itemStack(access))
                                                        .executes(context ->
                                                                AccessoryCommand.executeSet(context.getSource(), EntityArgumentType.getEntities(context, "targets"),
                                                                        IdentifierArgumentType.getIdentifier(context, "unit"), ItemStackArgumentType.getItemStackArgument(context, "item"), 1))
                                                        .then(CommandManager.argument("count", IntegerArgumentType.integer(1, 64))
                                                                .executes(context ->
                                                                        AccessoryCommand.executeSet(context.getSource(), EntityArgumentType.getEntities(context, "targets"),
                                                                                IdentifierArgumentType.getIdentifier(context, "unit"), ItemStackArgumentType.getItemStackArgument(context, "item"),
                                                                                IntegerArgumentType.getInteger(context, "count")))
                                                        )
                                                )
                                        )
                                )
                        )
                )
                .then(CommandManager.literal("debug")
                        .then(CommandManager.literal("data")
                                .then(CommandManager.literal("block entity")
                                        .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                                                .executes(context ->
                                                        DebugCommand.executeDataBlockEntity(context.getSource(), BlockPosArgumentType.getBlockPos(context, "pos")))
                                        )
                                )
                        )
                        .then(CommandManager.literal("settings")
                                .then(CommandManager.literal("get")
                                        .then(CommandManager.literal("block entity")
                                                .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                                                        .executes(context ->
                                                                DebugCommand.executeSettingsGetBlockEntity(context.getSource(), BlockPosArgumentType.getBlockPos(context, "pos")))
                                                )
                                        )
                                )
                                .then(CommandManager.literal("set")
                                        .then(CommandManager.literal("block entity")
                                                .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                                                        .then(CommandManager.argument("setting index", IntegerArgumentType.integer(0, Byte.MAX_VALUE))
                                                                .then(CommandManager.argument("value index", IntegerArgumentType.integer(0, Byte.MAX_VALUE))
                                                                        .executes(context ->
                                                                                DebugCommand.executeSettingsSetBlockEntity(
                                                                                        context.getSource(),
                                                                                        BlockPosArgumentType.getBlockPos(context, "pos"),
                                                                                        (byte) IntegerArgumentType.getInteger(context, "setting index"),
                                                                                        (byte) IntegerArgumentType.getInteger(context, "value index")
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }


    private static final class ConfigCommand {


        private static Identifier getConfig(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
            RegistryEntry.Reference<Config> reference = RegistryEntryReferenceArgumentType.getRegistryEntry(context, "id", WoolRegistryKeys.CONFIG);
            Identifier id = WoolRegistries.CONFIG.getId(reference.value());

            if (id == null)
                throw INVALID_CONFIG_ID_EXCEPTION.create(reference.registryKey().getValue().toString());
            else
                return id;
        }

        private static int executeLoad(ServerCommandSource source, Identifier id) {
            Config config = WoolRegistries.CONFIG.get(id);

            if (config != null) {
                config.load();
                source.sendFeedback(() -> TextUtil.woolTranslatable(
                        TextKeys.COMMAND, "wool.config.load", WOOL, TextUtil.woolTranslatable(TextKeys.CHAT, "configInfo", id.toString()).formatted(Formatting.GREEN)
                ), true);
            }

            return 1;
        }

        private static int executeResetToDefault(ServerCommandSource source, Identifier id) {
            Config config = WoolRegistries.CONFIG.get(id);

            if (config != null) {
                config.resetToDefault();
                source.sendFeedback(() -> Text.translatable(
                        "command." + Wool.MOD_ID + ".wool.config.resetToDefault", WOOL, Text.translatable("chat." + Wool.MOD_ID + ".configInfo", id.toString()).formatted(Formatting.GREEN)
                ), true);
            }

            return 1;
        }

        private static int executeLoadAll(ServerCommandSource source) {
            Config.Manager.loadAll();
            source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.config.loadAll", WOOL), true);

            return 1;
        }

        private static int executeResetToDefaultAll(ServerCommandSource source) {
            Config.Manager.resetToDefaultAll();
            source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.config.resetToDefaultAll", WOOL), true);

            return 1;
        }
    }


    private static final class AccessoryCommand {


        private static int executeGet(ServerCommandSource source, Collection<? extends Entity> targets, Identifier unit) {
            List<Entity> valid = new ArrayList<>();

            for (Entity entity : targets) {
                EntityType<?> entityType = entity.getType();

                if (entity instanceof AccessoryInventoryHolder holder && holder.isValid() && holder.getRegistry() != null && holder.getId() != null
                        && holder.getFullAccessoryInventory() != null && AccessoryInventoryManager.ENTITY_TYPE_TO_REGISTRY.containsKey(entityType)) {
                    ItemStack stack = holder.getAccessoryInventoryUnit(unit, true).getStack();

                    valid.add(entity);
                    source.sendFeedback(() -> Text.translatable("command." + Wool.MOD_ID + ".wool.accessory.get", WOOL, Texts.bracketed(entity.getName()).styled(
                            style -> style
                                    .withColor(Formatting.GREEN)
                                    .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + entity.getX() + " " + entity.getY() + " " + entity.getZ()))
                                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("chat.coordinates.tooltip")))
                    ), Text.translatable("chat." + Wool.MOD_ID + ".entityInfo", entityType.toString(), entity.getUuidAsString()).formatted(Formatting.DARK_GREEN),
                            Text.translatable("chat." + Wool.MOD_ID + ".itemStackInfo", Registries.ITEM.getId(stack.getItem()).toString(), stack.getCount()).formatted(Formatting.AQUA),
                            Text.translatable("chat." + Wool.MOD_ID + ".accessoryInventoryUnitInfo", unit.toString()).formatted(Formatting.DARK_AQUA)), true);
                }
            }

            return valid.size();
        }

        private static int executeSet(ServerCommandSource source, Collection<? extends Entity> targets, Identifier unit, ItemStackArgument item, int count) {
            List<Entity> valid = new ArrayList<>();

            for (Entity entity : targets) {
                EntityType<?> entityType = entity.getType();

                if (entity instanceof AccessoryInventoryHolder holder && holder.isValid() && holder.getRegistry() != null && holder.getId() != null
                        && holder.getFullAccessoryInventory() != null && AccessoryInventoryManager.ENTITY_TYPE_TO_REGISTRY.containsKey(entityType)) {
                    ItemStack stack = new ItemStack(item.getItem(), count);

                    valid.add(entity);
                    holder.getAccessoryInventoryUnit(unit, true).setStack(stack);
                    source.sendFeedback(() -> Text.translatable(
                            "command." + Wool.MOD_ID + ".wool.accessory.set", WOOL,
                            Text.translatable("chat." + Wool.MOD_ID + ".itemStackInfo", Registries.ITEM.getId(stack.getItem()).toString(), stack.getCount()).formatted(Formatting.AQUA),
                            Text.translatable("chat." + Wool.MOD_ID + ".accessoryInventoryUnitInfo", unit.toString()).formatted(Formatting.DARK_AQUA), Texts.bracketed(entity.getName()).styled(
                                    style -> style
                                            .withColor(Formatting.GREEN)
                                            .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + entity.getX() + " " + entity.getY() + " " + entity.getZ()))
                                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.translatable("chat.coordinates.tooltip")))
                            ), Text.translatable("chat." + Wool.MOD_ID + ".entityInfo", entityType.toString(), entity.getUuidAsString()).formatted(Formatting.DARK_GREEN)
                    ), true);
                }
            }

            return valid.size();
        }
    }


    private static final class DebugCommand {

        private static final String DEBUG_KEY = "wool.debug";
        private static final String BLOCK_ENTITY_INFO_KEY = ".block_entity_info";

        private static final DynamicCommandExceptionType NON_DEBUGGABLE_OBJECT_EXCEPTION = new DynamicCommandExceptionType(object ->
                TextUtil.woolTranslatable(TextKeys.COMMAND, DEBUG_KEY + ".non_debuggable_object", WOOL, object)
        );
        public static final DynamicCommandExceptionType NON_EXISTENT_SETTING_EXCEPTION = new DynamicCommandExceptionType(object ->
                TextUtil.woolTranslatable(TextKeys.COMMAND, DEBUG_KEY + "non_existent_setting", WOOL, object)
        );


        private static int executeDataBlockEntity(ServerCommandSource source, BlockPos pos) throws CommandSyntaxException {
            BlockEntity blockEntity = source.getWorld().getBlockEntity(pos);

            if (blockEntity instanceof Debuggable debuggable)
                source.sendFeedback(() ->
                        TextUtil.woolTranslatable(
                                TextKeys.COMMAND,
                                DEBUG_KEY + ".data.block_entity",
                                WOOL,
                                TextUtil.woolTranslatable(TextKeys.CHAT, BLOCK_ENTITY_INFO_KEY, blockEntity.getType().toString(), blockEntity.getPos().toString()).formatted(Formatting.AQUA),
                                Text.literal(debuggable.getDebugData().toString()).formatted(Formatting.GREEN)
                        ), true
                );
            else
                throw NON_DEBUGGABLE_OBJECT_EXCEPTION.create(blockEntity);

            return Command.SINGLE_SUCCESS;
        }

        private static int executeSettingsGetBlockEntity(ServerCommandSource source, BlockPos pos) throws CommandSyntaxException {
            BlockEntity blockEntity = source.getWorld().getBlockEntity(pos);

            if (blockEntity instanceof Debuggable debuggable)
                source.sendFeedback(() ->
                        TextUtil.woolTranslatable(
                                TextKeys.COMMAND,
                                DEBUG_KEY + ".settings.get.block_entity",
                                WOOL,
                                TextUtil.woolTranslatable(TextKeys.CHAT, BLOCK_ENTITY_INFO_KEY, blockEntity.getType().toString(), blockEntity.getPos().toString()).formatted(Formatting.AQUA),
                                Text.literal(debuggable.getDebugSettings().get()).formatted(Formatting.GREEN)
                        ), true
                );
            else
                throw NON_DEBUGGABLE_OBJECT_EXCEPTION.create(blockEntity);

            return Command.SINGLE_SUCCESS;
        }

        private static int executeSettingsSetBlockEntity(ServerCommandSource source, BlockPos pos, byte settingIndex, byte valueIndex) throws CommandSyntaxException {
            BlockEntity blockEntity = source.getWorld().getBlockEntity(pos);

            if (blockEntity instanceof Debuggable debuggable) {
                Debuggable.Setting<?> setting = debuggable.getDebugSettings().getSetting(settingIndex);

                if (setting != null) {
                    setting.setValue(valueIndex);

                    Debuggable.Setting.Value value = setting.getValue();

                    source.sendFeedback(() ->
                            TextUtil.woolTranslatable(
                                    TextKeys.COMMAND,
                                    DEBUG_KEY + ".settings.set.block_entity",
                                    WOOL,
                                    TextUtil.woolTranslatable(TextKeys.CHAT, BLOCK_ENTITY_INFO_KEY, blockEntity.getType().toString(), blockEntity.getPos().toString()).formatted(Formatting.AQUA),
                                    Text.literal(String.valueOf(setting.getIndex())).formatted(Formatting.YELLOW),
                                    Text.literal(setting.getName()).formatted(Formatting.YELLOW),
                                    Text.literal(String.valueOf(value.getIndex())).formatted(Formatting.GREEN),
                                    Text.literal(value.getName()).formatted(Formatting.GREEN)
                            ), true
                    );
                } else
                    throw NON_EXISTENT_SETTING_EXCEPTION.create(settingIndex);
            } else
                throw NON_DEBUGGABLE_OBJECT_EXCEPTION.create(blockEntity);

            return Command.SINGLE_SUCCESS;
        }
    }
}
