package net.mrwooly357.wool.data_gen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.mrwooly357.wool.Wool;

import java.util.concurrent.CompletableFuture;

public final class WoolEnUSLanguageProvider extends FabricLanguageProvider {

    private static final String MOD_ID = Wool.MOD_ID;

    public WoolEnUSLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }


    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder builder) {
        addCommand(builder, "server_config.general.save.feedback", "Saving %s general server config.");
        addCommand(builder, "server_config.world.save.feedback", "Saving %s server world config.");
        addCommand(builder, "server_config.general.load.feedback", "Loading %s general server config.");
        addCommand(builder, "server_config.world.load.feedback", "Loading %s server world config.");
        addCommand(builder, "client_config.general.save.feedback", "Saving %s general client config.");
        addCommand(builder, "client_config.world.save.feedback", "Saving %s client world config.");
        addCommand(builder, "client_config.general.load.feedback", "Loading %s general client config.");
        addCommand(builder, "client_config.world.load.feedback", "Loading %s client world config.");
    }

    private static void addCommand(TranslationBuilder builder, String path, String translation) {
        builder.add("command." + MOD_ID + "." + path, translation);
    }
}
