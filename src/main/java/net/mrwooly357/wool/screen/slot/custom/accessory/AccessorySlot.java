package net.mrwooly357.wool.screen.slot.custom.accessory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.mrwooly357.wool.entity.util.AccessoryInventoryUnit;
import net.mrwooly357.wool.item.accessory.Accessory;

public class AccessorySlot extends Slot {

    private final AccessorySlotType type;
    private final Entity entity;
    private final AccessoryInventoryUnit unit;

    public AccessorySlot(Inventory inventory, int index, int x, int y, AccessorySlotType type, Entity entity, AccessoryInventoryUnit unit) {
        super(inventory, index, x, y);

        this.type = type;
        this.entity = entity;
        this.unit = unit;
    }


    @Override
    public boolean canInsert(ItemStack stack) {
        TagKey<Item> tag = type.getTag();

        return tag == null || stack.isIn(tag);
    }

    @Override
    public void setStack(ItemStack stack, ItemStack previousStack) {
        super.setStack(stack, previousStack);

        if (stack.getItem() instanceof Accessory accessory)
            accessory.onEquip(entity, stack, unit);
    }

    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        super.onTakeItem(player, stack);

        if (stack.getItem() instanceof Accessory accessory)
            accessory.onUnequip(player, stack, unit);
    }
}
