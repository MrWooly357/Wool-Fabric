package net.mrwooly357.wool.accessory.screen.slot.custom;

import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class AccessorySlotType {

    @Nullable
    private final Identifier icon;
    @Nullable
    private final TagKey<Item> tag;

    private AccessorySlotType(@Nullable Identifier icon, @Nullable TagKey<Item> tag) {
        if (icon != null) {
            this.icon = Identifier.of(icon.getNamespace(), "textures/gui/sprites/accessory/slot_overlay/" + icon.getPath() + ".png");
        } else
            this.icon = null;

        this.tag = tag;
    }


    public @Nullable Identifier getIcon() {
        return icon;
    }

    public @Nullable TagKey<Item> getTag() {
        return tag;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {

        public Builder() {}

        @Nullable
        private Identifier icon;
        @Nullable
        private TagKey<Item> tag;

        public AccessorySlotType build() {
            return new AccessorySlotType(icon, tag);
        }

        public Builder icon(Identifier icon) {
            this.icon = icon;

            return this;
        }

        public Builder tag(TagKey<Item> tag) {
            this.tag = tag;

            return this;
        }
    }
}
