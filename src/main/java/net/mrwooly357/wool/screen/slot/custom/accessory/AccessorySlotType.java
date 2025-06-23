package net.mrwooly357.wool.screen.slot.custom.accessory;

import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class AccessorySlotType {

    private final Text name;
    @Nullable
    private final Identifier icon;
    @Nullable
    private final TagKey<Item> tag;

    public AccessorySlotType(Text name, Identifier icon) {
        this(name, icon, null);
    }

    public AccessorySlotType(Text name, TagKey<Item> tag) {
        this(name, null, tag);
    }

    public AccessorySlotType(Text name, @Nullable Identifier icon, @Nullable TagKey<Item> tag) {
        this.name = name;
        this.icon = icon;
        this.tag = tag;
    }


    public Text getName() {
        return name;
    }

    public @Nullable Identifier getIcon() {
        return icon;
    }

    public @Nullable TagKey<Item> getTag() {
        return tag;
    }
}
