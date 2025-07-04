package net.mrwooly357.wool.screen.custom.accessory;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.entity.util.AccessoryInventoryHolder;
import net.mrwooly357.wool.entity.util.AccessoryInventoryUnit;

import java.util.Map;

public class PlayerAccessoryInventoryScreen extends HandledScreen<PlayerAccessoryInventoryScreenHandler> {

    private Map<Identifier, AccessoryInventoryUnit> accessoryInventory;

    private static final Identifier GUI_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/player_accessory_inventory.png"
    );
    private static final Identifier FULL_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/full.png"
    );
    private static final Identifier REGULAR_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/regular.png"
    );
    private static final Identifier BOTTOM_CONNECTED_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/bottom_connected.png"
    );
    private static final Identifier TOP_CONNECTED_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/top_connected.png"
    );
    private static final Identifier RIGHT_BOTTOM_CORNER_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/right_bottom_corner.png"
    );
    private static final Identifier LEFT_BOTTOM_CORNER_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/left_bottom_corner.png"
    );
    private static final Identifier RIGHT_TOP_CORNER_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/right_top_corner.png"
    );
    private static final Identifier LEFT_TOP_CORNER_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/left_top_corner.png"
    );
    private static final Identifier BOTTOM_SIDE_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/bottom_side.png"
    );
    private static final Identifier RIGHT_SIDE_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/right_side.png"
    );
    private static final Identifier LEFT_SIDE_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/left_side.png"
    );
    private static final Identifier RIGHT_AND_LEFT_SIDES_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/right_and_left_sides.png"
    );
    private static final Identifier TOP_SIDE_TEXTURE = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/top_side.png"
    );
    private static final Identifier CONNECTING_CORNER_TEXTURE_0 = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/connecting_corner_0.png"
    );
    private static final Identifier CONNECTING_CORNER_TEXTURE_1 = Identifier.of(
            Wool.MOD_ID, "textures/gui/sprites/accessory/slot/connecting_corner_1.png"
    );

    public PlayerAccessoryInventoryScreen(PlayerAccessoryInventoryScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        if (inventory.player instanceof AccessoryInventoryHolder holder)
            accessoryInventory = holder.getAccessoryInventory();
    }


    @Override
    protected void init() {
        super.init();

        titleX -= 6;
        titleY -= 5;
        playerInventoryTitleY += 3;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        short slot = 0;
        int size = accessoryInventory.size();

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        InventoryScreen.drawEntity(context, x + 9, y + 10, x + 58, y + 74, 30, 0.0625F, mouseX, mouseY, client.player);

        for (Map.Entry<Identifier, AccessoryInventoryUnit> entry : accessoryInventory.entrySet()) {
            AccessoryInventoryUnit unit = entry.getValue();
            Identifier icon = unit.getIcon();

            slot++;

            if (size == 1) {
                context.drawTexture(FULL_TEXTURE, x + 67, y + 46, 0, 0, 28, 28, 28, 28);

                if (icon != null)
                    context.drawTexture(icon, x + 73, y + 52, 0, 0, 16, 16, 16, 16);
            } else if (size == 2) {

                if (slot == 1) {
                    context.drawTexture(TOP_CONNECTED_TEXTURE, x + 67, y + 46, 0, 0, 28, 28, 28, 28);

                    if (icon != null)
                        context.drawTexture(icon, x + 73, y + 52, 0, 0, 16, 16, 16, 16);
                } else if (slot == 2) {
                    context.drawTexture(BOTTOM_CONNECTED_TEXTURE, x + 67, y + 28, 0, 0, 28, 28, 28, 28);

                    if (icon != null)
                        context.drawTexture(icon, x + 73, y + 34, 0, 0, 16, 16, 16, 16);
                }
            } else if (size == 3) {

                if (slot == 1) {
                    context.drawTexture(TOP_CONNECTED_TEXTURE, x + 67, y + 46, 0, 0, 28, 28, 28, 28);

                    if (icon != null)
                        context.drawTexture(icon, x + 73, y + 52, 0, 0, 16, 16, 16, 16);
                } else if (slot == 2) {
                    context.drawTexture(RIGHT_AND_LEFT_SIDES_TEXTURE, x + 67, y + 28, 0, 0, 28, 28, 28, 28);

                    if (icon != null)
                        context.drawTexture(icon, x + 73, y + 34, 0, 0, 16, 16, 16, 16);
                } else if (slot == 3) {
                    context.drawTexture(BOTTOM_CONNECTED_TEXTURE, x + 67, y + 10, 0, 0, 28, 28, 28, 28);

                    if (icon != null)
                        context.drawTexture(icon, x + 73, y + 16, 0, 0, 16, 16, 16, 16);
                }
            } else {

                if (slot == 1) {
                    context.drawTexture(LEFT_BOTTOM_CORNER_TEXTURE, x + 67, y + 46, 0, 0, 28, 28, 28, 28);

                    if (icon != null)
                        context.drawTexture(icon, x + 73, y + 52, 0, 0, 16, 16, 16, 16);
                } else if (slot == 2) {
                    context.drawTexture(LEFT_SIDE_TEXTURE, x + 67, y + 28, 0, 0, 28, 28, 28, 28);

                    if (icon != null)
                        context.drawTexture(icon, x + 73, y + 34, 0, 0, 16, 16, 16, 16);
                } else if (slot == 3) {

                    if (size >= 6) {
                        context.drawTexture(LEFT_TOP_CORNER_TEXTURE, x + 67, y + 10, 0, 0, 28, 28, 28, 28);
                    } else {
                        context.drawTexture(BOTTOM_CONNECTED_TEXTURE, x + 67, y + 10, 0, 0, 28, 28, 28, 28);
                    }

                    if (icon != null)
                        context.drawTexture(icon, x + 73, y + 16, 0, 0, 16, 16, 16, 16);
                } else if (slot % 3 == 1) {

                    if (slot == size) {
                        context.drawTexture(RIGHT_BOTTOM_CORNER_TEXTURE, x + 85 + ((int) ((double) slot / 3) - 1) * 18, y + 46, 0, 0, 28, 28, 28, 28);
                        context.drawTexture(CONNECTING_CORNER_TEXTURE_0, x + 90 + ((int) ((double) slot / 3) - 1) * 18, y + 23, 0, 0, 28, 28, 28, 28);
                    } else if (size >= slot + 3) {
                        context.drawTexture(BOTTOM_SIDE_TEXTURE, x + 85 + ((int) ((double) slot / 3) - 1) * 18, y + 46, 0, 0, 28, 28, 28, 28);
                    } else
                        context.drawTexture(RIGHT_BOTTOM_CORNER_TEXTURE, x + 85 + ((int) ((double) slot / 3) - 1) * 18, y + 46, 0, 0, 28, 28, 28, 28);

                    if (icon != null)
                        context.drawTexture(icon, x + 91 + ((int) ((double) slot / 3) - 1) * 18, y + 52, 0, 0, 16, 16, 16, 16);

                } else if (slot % 3 == 2) {

                    if (slot == size) {
                        context.drawTexture(RIGHT_SIDE_TEXTURE, x + 85 + ((int) ((double) slot / 3) - 1) * 18, y + 28, 0, 0, 28, 28, 28, 28);
                        context.drawTexture(CONNECTING_CORNER_TEXTURE_1, x + 90 + ((int) ((double) slot / 3) - 1) * 18, y + 5, 0, 0, 28, 28, 28, 28);
                    } else if (size >= slot + 2) {
                        context.drawTexture(REGULAR_TEXTURE, x + 85 + ((int) ((double) slot / 3) - 1) * 18, y + 28, 0, 0, 28, 28, 28, 28);
                    } else
                        context.drawTexture(RIGHT_SIDE_TEXTURE, x + 85 + ((int) ((double) slot / 3) - 1) * 18, y + 28, 0, 0, 28, 28, 28, 28);

                    if (icon != null)
                        context.drawTexture(icon, x + 91 + ((int) ((double) slot / 3) - 1) * 18, y + 34, 0, 0, 16, 16, 16, 16);
                } else if (slot % 3 == 0) {

                    if (slot == size || size >= slot + 1) {
                        context.drawTexture(RIGHT_TOP_CORNER_TEXTURE, x + 85 + ((int) ((double) slot / 3) - 2) * 18, y + 10, 0, 0, 28, 28, 28, 28);
                    } else
                        context.drawTexture(TOP_SIDE_TEXTURE, x + 85 + ((int) ((double) slot / 3) - 2) * 18, y + 10, 0, 0, 28, 28, 28, 28);

                    if (icon != null)
                        context.drawTexture(icon, x + 91 + ((int) ((double) slot / 3) - 2) * 18, y + 16, 0, 0, 16, 16, 16, 16);
                }
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
