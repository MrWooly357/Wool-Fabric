package net.mrwooly357.wool.config.custom;

import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.field_type.FieldTypes;

public final class WoolConfig extends Config {

    public static boolean enableDeveloperMode;

    private static final Entry.Category MISC_C = Entry.Category.builder()
            .build("Miscellaneous");
    private static final Entry<Boolean> ENABLE_DEVELOPER_MODE_E = Entry.<Boolean>builder()
            .category(MISC_C)
            .build("EnableDeveloperMode", FieldTypes.BOOLEAN, false);

    public WoolConfig() {
        super(Wool.MOD_ID);

        category(MISC_C);
        emptyLine();
        entry(ENABLE_DEVELOPER_MODE_E, "For developers! Logs additional information and enables certain debug functions.");
    }


    @Override
    public void onUpdate() {
        enableDeveloperMode = ENABLE_DEVELOPER_MODE_E.getValue();
    }
}
