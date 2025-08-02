package net.mrwooly357.wool.util.misc;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public final class WoolUtil {


    /**
     * Calculates distance between to points. "From" point is the place you start. "To" point is the place you finish.
     * @param fromX is x coordinates of "from" point.
     * @param fromY is y coordinates of "from" point.
     * @param fromZ is z coordinates of "from" point.
     * @param toX is x coordinates of "to" point.
     * @param toY is y coordinates of "to" point.
     * @param toZ is z coordinates of "to" point.
     * @return the distance between "from" point and "to" point.
     */
    public static double getDistanceBetween(double fromX, double fromY, double fromZ, double toX, double toY, double toZ) {
        double xDistance = toX - fromX;
        double yDistance = toY - fromY;
        double zDistance = toZ - fromZ;

        return MathHelper.sqrt((float) (xDistance * xDistance + yDistance * yDistance + zDistance * zDistance));
    }

    /**
     * Converts a rgb colour to packed int colour.
     * @param red the red value of the colour.
     * @param green the green value of the colour.
     * @param blue the blue value of the colour.
     * @return a packed int colour from rgb.
     */
    public static int rgbToPackedInt(int red, int green, int blue) {
        return (red << 16) | (green << 8) | blue;
    }

    public static boolean insertStack(ItemStack useStack, ItemStack exchangeStack, PlayerInventory inventory, int slot) {
        PlayerEntity player = inventory.player;
        int count = useStack.getCount();
        boolean bl = false;
        boolean isExchangeStackEmpty = exchangeStack.isEmpty();
        boolean isPlayerInCreativeMode = player.isInCreativeMode();

        if (count <= 1 && !isExchangeStackEmpty && !isPlayerInCreativeMode) {
            useStack.decrement(1);
            inventory.setStack(slot, exchangeStack);

            bl = true;
        } else if (count > 1 && !isExchangeStackEmpty && !isPlayerInCreativeMode) {

            for (int a = 0; a < inventory.main.size(); a++) {

                if (inventory.getStack(a).isEmpty()) {
                    useStack.decrement(1);
                    inventory.setStack(a, exchangeStack);

                    bl = true;

                    break;
                }
            }
        } else if (!isExchangeStackEmpty) {

            for (int a = 0; a < inventory.main.size(); a++) {

                if (inventory.getStack(a).getItem() == exchangeStack.getItem()) return true;
            }

            for (int a = 0; a < inventory.main.size(); a++) {

                if (inventory.getStack(a).getItem() == exchangeStack.getItem() || inventory.offHand.getFirst().getItem() == exchangeStack.getItem()) {
                    bl = true;

                    break;
                } else if (inventory.getStack(a).isEmpty()) {
                    inventory.setStack(a, exchangeStack);

                    bl = true;

                    break;
                }
            }
        }

        return bl;
    }
}
