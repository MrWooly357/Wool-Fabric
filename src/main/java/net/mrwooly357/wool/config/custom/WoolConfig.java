package net.mrwooly357.wool.config.custom;

import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.ConfigEntry;

public class WoolConfig extends Config {

    public static final ConfigEntry.Category general = new ConfigEntry.Category("general");
    public ConfigEntry<Boolean> developerModeEntry = booleanField("If this is true, the game will log additional information.", general, "developerMode", false);
    public static boolean developerMode;

    public WoolConfig() {
        super(Wool.MOD_ID);

        onUpdate();
    }


    @Override
    public void onUpdate() {
        developerMode = developerModeEntry.getValue();
    }
}
