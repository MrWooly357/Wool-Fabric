package net.mrwooly357.wool.data_gen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.mrwooly357.wool.Wool;

import java.util.concurrent.CompletableFuture;

public final class WoolRuRuLanguageProvider extends FabricLanguageProvider {

    private static final String MOD_ID = Wool.MOD_ID;

    public WoolRuRuLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "ru_ru", registryLookup);
    }


    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder builder) {
        addCommand(builder, "server_config.general.save.feedback", "Сохранение %s основного серверного конфига.");
        addCommand(builder, "server_config.world.save.feedback", "Сохранение %s серверного конфига мира.");
        addCommand(builder, "server_config.general.load.feedback", "Загрузка %s основного серверного конфига.");
        addCommand(builder, "server_config.world.load.feedback", "Загрузка %s серверного конфига мира.");
    }

    private static void addCommand(TranslationBuilder builder, String path, String translation) {
        builder.add("command." + MOD_ID + "." + path, translation);
    }
}
