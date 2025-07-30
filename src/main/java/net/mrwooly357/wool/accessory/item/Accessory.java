package net.mrwooly357.wool.accessory.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryUnit;

public interface Accessory {


    boolean canEquip(Entity entity, ItemStack stack, AccessoryInventoryUnit unit);

    boolean canUnequip(Entity entity, ItemStack stack, AccessoryInventoryUnit unit);

    void onEquip(Entity entity, ItemStack stack, AccessoryInventoryUnit unit);

    void onUnequip(Entity entity, ItemStack stack, AccessoryInventoryUnit unit);

    void tick(Entity entity, ItemStack stack, AccessoryInventoryUnit unit);

    void onDeath(Entity entity, ItemStack stack, AccessoryInventoryUnit unit);

    boolean keepOnDeath(PlayerEntity player, ItemStack stack, AccessoryInventoryUnit unit);
}
