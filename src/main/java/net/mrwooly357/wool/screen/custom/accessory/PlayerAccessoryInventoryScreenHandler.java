package net.mrwooly357.wool.screen.custom.accessory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.entity.accessory.inventory.AccessoryInventoryHolder;
import net.mrwooly357.wool.entity.accessory.inventory.AccessoryInventoryUnit;
import net.mrwooly357.wool.screen.WoolScreenHandlerTypes;
import net.mrwooly357.wool.screen.slot.custom.accessory.AccessorySlot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerAccessoryInventoryScreenHandler extends ScreenHandler {

    private Map<Identifier, AccessoryInventoryUnit> accessoryInventory;

    public PlayerAccessoryInventoryScreenHandler(int syncId, PlayerEntity player) {
        super(WoolScreenHandlerTypes.PLAYER_ACCESSORY_INVENTORY, syncId);

        PlayerInventory playerInventory = player.getInventory();

        if (player instanceof AccessoryInventoryHolder holder)
            accessoryInventory = holder.getAccessoryInventory();

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
        addAccessorySlots(player);
    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
    }

    private void addAccessorySlots(PlayerEntity player) {
        List<AccessoryInventoryUnit> units = new ArrayList<>();
        Inventory inventory;
        short slot = 0;
        int x = 73;
        int y = 52;

        for (Map.Entry<Identifier, AccessoryInventoryUnit> entry : accessoryInventory.entrySet())
            units.add(entry.getValue());

        inventory = new AccessoryInventoryUnit.Wrapper(units);

        for (AccessoryInventoryUnit unit : units) {
            slot++;

            if (slot % 3 == 1 && slot >= 3) {
                x += 18;
                y += 36;
            } else if (slot > 1)
                y -= 18;

            addSlot(new AccessorySlot(inventory, unit, slot - 1, x, y, player));
        }
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int a = 0; a < 3; a++) {

            for (int b = 0; b < 9; b++) {
                addSlot(new Slot(playerInventory, b + a * 9 + 9, 8 + b * 18, 84 + a * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int a = 0; a < 9; a++) {
            addSlot(new Slot(playerInventory, a, 8 + a * 18, 142));
        }
    }
}
