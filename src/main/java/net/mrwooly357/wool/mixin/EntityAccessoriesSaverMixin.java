package net.mrwooly357.wool.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryHolder;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryUnit;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryManager;
import net.mrwooly357.wool.accessory.item.Accessory;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(Entity.class)
public abstract class EntityAccessoriesSaverMixin implements AccessoryInventoryHolder {

    @Shadow
    private World world;
    @Nullable
    @Unique
    private Map<Identifier, AccessoryInventoryUnit> fullAccessoryInventory;
    @Nullable
    @Unique
    private Map<Identifier, AccessoryInventoryUnit> accessoryInventory;

    @Shadow
    public abstract EntityType<?> getType();

    @Shadow
    @Nullable
    public abstract ItemEntity dropStack(ItemStack stack);

    @Override
    @Nullable
    public Registry<AccessoryInventoryUnit> getRegistry() {
        return AccessoryInventoryManager.ENTITY_TYPE_TO_REGISTRY.get(getType());
    }

    @Override
    @Nullable
    public Identifier getId() {
        return AccessoryInventoryManager.REGISTRY_TO_ID.get(getRegistry());
    }

    @Override
    @Nullable
    public Map<Identifier, AccessoryInventoryUnit> getFullAccessoryInventory() {
        if (isValid() && getRegistry() != null && getId() != null) {

            if (fullAccessoryInventory == null)
                tryCreateAccessoryInventory();

            return fullAccessoryInventory;
        }

        return null;
    }

    @Override
    public @Nullable Map<Identifier, AccessoryInventoryUnit> getAccessoryInventory() {
        return accessoryInventory;
    }

    @Override
    public boolean isValid() {
        return AccessoryInventoryHolder.super.isValid() && AccessoryInventoryManager.ENTITY_TYPE_TO_REGISTRY.containsKey(getType()) && AccessoryInventoryManager.REGISTRY_TO_ID.containsKey(getRegistry());
    }

    @Unique
    private void tryCreateAccessoryInventory() {
        if (fullAccessoryInventory == null && isValid() && getRegistry() != null && getId() != null) {
            List<Identifier> order = AccessoryInventoryManager.UNIT_ORDER.get(getRegistry());
            fullAccessoryInventory = new LinkedHashMap<>();
            accessoryInventory = new LinkedHashMap<>();

            for (Identifier id : order) {
                AccessoryInventoryUnit template = getRegistry().get(id);

                if (template != null) {
                    AccessoryInventoryUnit unit = new AccessoryInventoryUnit(template.getType(), template.getStack(), template.isAvailable());

                    fullAccessoryInventory.put(id, unit);

                    if (unit.isAvailable())
                       accessoryInventory.put(id, unit);
                }
            }
        }
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectConstructor(EntityType<?> type, World world, CallbackInfo info) {
        tryCreateAccessoryInventory();
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    private void injectWriteNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> info) {
        if (isValid() && getRegistry() != null && getId() != null) {
            NbtCompound compound = new NbtCompound();
            NbtCompound compound1 = new NbtCompound();

            tryCreateAccessoryInventory();

            for (Map.Entry<Identifier, AccessoryInventoryUnit> entry : fullAccessoryInventory.entrySet())
                compound.put(entry.getKey().toString(), AccessoryInventoryUnit.toNbt(world, entry.getValue()));

            for (Map.Entry<Identifier, AccessoryInventoryUnit> entry : accessoryInventory.entrySet())
                compound1.put(entry.getKey().toString(), AccessoryInventoryUnit.toNbt(world, entry.getValue()));

            nbt.put(Wool.MOD_ID + ".FullAccessoryInventory", compound);
            nbt.put(Wool.MOD_ID + ".AccessoryInventory", compound1);
        }
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    private void injectReadNbt(NbtCompound nbt, CallbackInfo info) {
        if (isValid() && getRegistry() != null && getId() != null) {
            NbtCompound compound = nbt.getCompound(Wool.MOD_ID + ".FullAccessoryInventory");
            NbtCompound compound1 = nbt.getCompound(Wool.MOD_ID + ".AccessoryInventory");

            tryCreateAccessoryInventory();

            for (String key : compound.getKeys())
                fullAccessoryInventory.get(Identifier.of(key)).setStack(AccessoryInventoryUnit.fromNbt(compound.getCompound(key), world));

            for (String key : compound1.getKeys())
                accessoryInventory.get(Identifier.of(key)).setStack(AccessoryInventoryUnit.fromNbt(compound1.getCompound(key), world));
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void injectTick(CallbackInfo info) {
        if (isValid() && getRegistry() != null && getId() != null && accessoryInventory != null) {

            for (Map.Entry<Identifier, AccessoryInventoryUnit> entry : accessoryInventory.entrySet()) {
                AccessoryInventoryUnit unit = entry.getValue();
                ItemStack stack = unit.getStack();

                if (!stack.isEmpty() && stack.getItem() instanceof Accessory accessory && !world.isClient())
                    accessory.tick(((Entity) (Object) this), stack, unit);
            }
        }
    }

    @Inject(method = "remove", at = @At("TAIL"))
    private void injectRemove(Entity.RemovalReason reason, CallbackInfo info) {
        if (reason == Entity.RemovalReason.KILLED && isValid() && getRegistry() != null && getId() != null && accessoryInventory != null) {

            for (Map.Entry<Identifier, AccessoryInventoryUnit> entry : accessoryInventory.entrySet()) {
                AccessoryInventoryUnit unit = entry.getValue();
                ItemStack stack = unit.getStack();

                if (!stack.isEmpty() && stack.getItem() instanceof Accessory accessory) {
                    accessory.onDeath(((Entity) (Object) this), stack, unit);

                    if (getType() == EntityType.PLAYER) {

                        if (!accessory.keepOnDeath(((PlayerEntity) (Object) this), stack, unit) && !world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
                            dropStack(stack);
                            unit.setStack(ItemStack.EMPTY);
                        }
                    } else {
                        dropStack(stack);
                        unit.setStack(ItemStack.EMPTY);
                    }
                } else {
                    dropStack(stack);
                    unit.setStack(ItemStack.EMPTY);
                }
            }
        }
    }
}
