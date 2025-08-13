package net.mrwooly357.wool.config.custom;

import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.field_type.WoolFieldTypes;

public final class WoolConfig extends Config {

    public static boolean enableDeveloperMode;

    private static final Entry.Category DEVELOPMENT_CATEGORY = Entry.Category.builder()
            .build("Development");
    private static final Entry<Boolean> ENABLE_DEVELOPER_MODE = Entry.<Boolean>builder()
            .category(DEVELOPMENT_CATEGORY)
            .build("enableDeveloperMode", WoolFieldTypes.BOOLEAN, false);

    public WoolConfig() {
        super(Wool.MOD_ID);

        category(DEVELOPMENT_CATEGORY);
        emptyLine();
        entry(ENABLE_DEVELOPER_MODE, "For developers! Logs additional information and enables certain debug functions.");
    }


    @Override
    public void onUpdate() {
        enableDeveloperMode = ENABLE_DEVELOPER_MODE.getValue();
    }
}
