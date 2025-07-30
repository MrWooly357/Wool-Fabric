package net.mrwooly357.wool.config.custom;

import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.field_type.WoolFieldTypes;

public class WoolConfig extends Config {

    public static boolean enableDeveloperMode;
    public static boolean enableRestrictions;

    protected static final Entry.Category GENERAL = Entry.Category.builder()
            .build("General");
    private static final Entry<Boolean> ENABLE_DEVELOPER_MODE = Entry.<Boolean>builder()
            .category(GENERAL)
            .build("enableDeveloperMode", WoolFieldTypes.BOOLEAN, false);
    private static final Entry<Boolean> ENABLE_RESTRICTIONS_ENTRY = Entry.<Boolean>builder()
            .category(GENERAL)
            .build("enableRestrictions", WoolFieldTypes.BOOLEAN, true);

    public WoolConfig() {
        super(Wool.MOD_ID);

        comment(GENERAL.getName());
        emptyLine();
        entry(ENABLE_DEVELOPER_MODE, "For developers! Logs additional information and enables certain debug functions.");
        emptyLine();
        entry(ENABLE_RESTRICTIONS_ENTRY, "Enables config entry restrictions. Not recommended to disable!");
    }


    @Override
    public void onUpdate() {
        enableDeveloperMode = ENABLE_DEVELOPER_MODE.getValue();
        enableRestrictions = ENABLE_RESTRICTIONS_ENTRY.getValue();
    }
}
