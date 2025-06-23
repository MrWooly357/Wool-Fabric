package net.mrwooly357.wool.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.entity.util.AccessoryInventoryHolder;
import net.mrwooly357.wool.entity.util.AccessoryInventoryUnit;
import net.mrwooly357.wool.entity.util.EntityTypeAccessoryInventoryManager;
import net.mrwooly357.wool.item.accessory.Accessory;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Mixin(Entity.class)
public abstract class EntityAccessoriesMixin implements AccessoryInventoryHolder {

    @Shadow
    private World world;
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
        return EntityTypeAccessoryInventoryManager.getEntityTypeToRegistry().get(getType());
    }

    @Override
    @Nullable
    public Identifier getId() {
        return EntityTypeAccessoryInventoryManager.getRegistryToId().get(getRegistry());
    }

    @Override
    public boolean isValid() {
        return AccessoryInventoryHolder.super.isValid() && getRegistry() != null && getId() != null && EntityTypeAccessoryInventoryManager.getEntityTypeToRegistry().containsKey(getType()) && EntityTypeAccessoryInventoryManager.getRegistryToId().containsKey(getRegistry());
    }

    @Override
    @Nullable
    public Map<Identifier, AccessoryInventoryUnit> getAccessoryInventory() {
        if (isValid()) {

            if (accessoryInventory == null)
                createAccessoryInventory();

            return accessoryInventory;
        }

        return null;
    }

    @Unique
    private void createAccessoryInventory() {
        if (accessoryInventory == null && isValid()) {
            accessoryInventory = new HashMap<>();

            for (byte a = 0; a < getRegistry().getKeys().size(); a++) {
                AccessoryInventoryUnit template = getRegistry().get(a);

                if (template != null) {
                    AccessoryInventoryUnit unit = new AccessoryInventoryUnit(template.getType(), template.getStack());
                    Optional<RegistryKey<AccessoryInventoryUnit>> keyTemplate = getRegistry().getKey(template);
                    keyTemplate.ifPresent(key -> accessoryInventory.put(key.getValue(), unit));
                }
            }
        }
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectInit(EntityType<?> type, World world, CallbackInfo info) {
        createAccessoryInventory();
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    private void injectWriteNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> info) {
        if (isValid()) {
            NbtCompound compound = new NbtCompound();

            createAccessoryInventory();

            for (Map.Entry<Identifier, AccessoryInventoryUnit> entry : accessoryInventory.entrySet()) {
                compound.put(entry.getKey().toString(), AccessoryInventoryUnit.toNbt(world, entry.getValue()));
                System.out.println("keyToString" + entry.getKey().toString());
                System.out.println("toNbt: " + AccessoryInventoryUnit.toNbt(world, entry.getValue()));
            }

            nbt.put("entity.nbt." + Wool.MOD_ID + ".custom_data", compound);
            System.out.println("nbt: " + nbt);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    private void injectReadNbt(NbtCompound nbt, CallbackInfo info) {
        if (isValid()) {
            NbtCompound compound = nbt.getCompound("entity.nbt." + Wool.MOD_ID + ".custom_data");

            createAccessoryInventory();

            for (String key : compound.getKeys()) {

                if (!Objects.equals(key, "wool:empty")) {
                    NbtCompound compound1 = compound.getCompound(key);
                    System.out.println(compound1);

                    accessoryInventory.get(Identifier.of(key)).setStack(AccessoryInventoryUnit.fromNbt(compound1, world));
                }
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void injectTick(CallbackInfo info) {
        if (isValid() && accessoryInventory != null) {

            for (Map.Entry<Identifier, AccessoryInventoryUnit> entry : accessoryInventory.entrySet()) {
                AccessoryInventoryUnit unit = entry.getValue();
                ItemStack stack = unit.getStack();

                if (!Objects.equals(entry.getKey().toString(), "wool:empty") && !stack.isEmpty() && stack.getItem() instanceof Accessory accessory)
                    accessory.tick(((Entity) ((Object) this)), stack, unit);
            }
        }
    }

    @Inject(method = "remove", at = @At("HEAD"))
    private void injectRemove(Entity.RemovalReason reason, CallbackInfo info) {
        if (reason == Entity.RemovalReason.KILLED && isValid() && accessoryInventory != null) {

            for (Map.Entry<Identifier, AccessoryInventoryUnit> entry : accessoryInventory.entrySet()) {
                AccessoryInventoryUnit unit = entry.getValue();
                ItemStack stack = unit.getStack();

                if (!Objects.equals(entry.getKey().toString(), "wool:empty") && !stack.isEmpty() && stack.getItem() instanceof Accessory accessory) {
                    accessory.onDeath(((Entity) ((Object) this)), stack, unit);

                    if (getType() == EntityType.PLAYER) {

                        if (!accessory.keepOnDeath(((PlayerEntity) ((Object) this)), stack, unit) && !world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
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
