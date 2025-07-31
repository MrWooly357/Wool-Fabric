package net.mrwooly357.wool.accessory.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryHolder;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryUnit;
import net.mrwooly357.wool.registry.WoolRegistries;
import net.mrwooly357.wool.accessory.screen.slot.custom.AccessorySlotType;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public abstract class AccessoryItem extends Item implements Accessory {

    protected AccessoryItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(player.getActiveHand());
        Map<Identifier, AccessoryInventoryUnit> accessoryInventory = ((AccessoryInventoryHolder) player).getAccessoryInventory();
        boolean canEquip = false;
        AccessoryInventoryUnit unit = null;

        for (Map.Entry<Identifier, AccessoryInventoryUnit> entry : accessoryInventory.entrySet()) {
            unit = entry.getValue();
            AccessorySlotType type = unit.getType();
            TagKey<Item> tag = type.getTag();

            if (entry.getValue().getStack().isEmpty() && (tag == null || stack.isIn(tag)) && WoolRegistries.ACCESSORY_SLOT_TYPE.getKey(type).isPresent()) {
                canEquip = true;

                break;
            }
        }

        if (canEquip(player, stack, unit) && canEquip) {
            Random random = Random.create();
            float volume = MathHelper.nextFloat(random, 0.9F, 1.1F);
            float pitch = MathHelper.nextFloat(random, 0.9F, 1.1F);
            ItemStack newStack = stack.copy();

            unit.setStack(newStack);
            onEquip(player, newStack, unit);
            stack.decrementUnlessCreative(1, player);
            player.incrementStat(Stats.USED.getOrCreateStat(this));

            if (getEquipSound() != null)
                world.playSound(null, player.getX(), player.getY(), player.getZ(), getEquipSound(), SoundCategory.PLAYERS, volume, pitch);

            return TypedActionResult.success(stack, world.isClient());
        }

        return super.use(world, player, hand);
    }

    @Nullable
    protected abstract SoundEvent getEquipSound();
}
