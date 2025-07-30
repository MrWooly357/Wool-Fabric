package net.mrwooly357.wool.accessory.screen.slot.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryUnit;
import net.mrwooly357.wool.accessory.item.Accessory;

public class AccessorySlot extends Slot {

    private final AccessoryInventoryUnit unit;
    private final AccessorySlotType type;
    private final Entity entity;

    public AccessorySlot(Inventory inventory, AccessoryInventoryUnit unit, int index, int x, int y, Entity entity) {
        super(inventory, index, x, y);

        this.unit = unit;
        this.type = unit.getType();
        this.entity = entity;
    }


    @Override
    public boolean canInsert(ItemStack stack) {
        TagKey<Item> tag = type.getTag();
        boolean bl = true;

        if (stack.getItem() instanceof Accessory accessory)
            bl = accessory.canEquip(entity, stack, unit);

        return (tag == null || stack.isIn(tag)) && bl;
    }

    @Override
    public boolean canTakeItems(PlayerEntity player) {
        ItemStack stack = unit.getStack();

        return stack.getItem() instanceof Accessory accessory ? accessory.canUnequip(entity, stack, unit) : super.canTakeItems(player);
    }

    @Override
    public ItemStack insertStack(ItemStack stack) {
        if (stack.getItem() instanceof Accessory accessory && !unit.getStack().isEmpty())
            accessory.onEquip(stack.getHolder(), stack, unit);

        return super.insertStack(stack);
    }

    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        super.onTakeItem(player, stack);

        if (stack.getItem() instanceof Accessory accessory)
            accessory.onUnequip(player, stack, unit);
    }
}
