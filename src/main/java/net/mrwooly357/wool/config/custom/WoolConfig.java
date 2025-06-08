package net.mrwooly357.wool.config.custom;

import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.ConfigEntry;

public class WoolConfig extends Config {

    protected static final ConfigEntry.Category general = new ConfigEntry.Category("general");
    public ConfigEntry<Boolean> enableRestrictionsEntry;
    public ConfigEntry<Boolean> developerModeEntry;
    public static boolean enableRestrictions;
    public static boolean developerMode;

    public WoolConfig() {
        super(Wool.MOD_ID);

        addComment("General");
        addEmptyLine();

        enableRestrictionsEntry = booleanField("Enables config entry restrictions. Not recommended to disable!", general, "enableRestrictions", true);
        developerModeEntry = booleanField("If this is true, the game will log additional information.", general, "developerMode", false);
    }


    @Override
    public void onUpdate() {
        enableRestrictions = enableRestrictionsEntry.getValue();
        developerMode = developerModeEntry.getValue();
    }
}
