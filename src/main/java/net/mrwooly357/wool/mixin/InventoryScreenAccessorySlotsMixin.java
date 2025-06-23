package net.mrwooly357.wool.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.entity.util.AccessoryInventoryUnit;
import net.mrwooly357.wool.entity.util.WoolEntityAccessoryInventories;
import net.mrwooly357.wool.registry.WoolRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenAccessorySlotsMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {

    @Unique
    private static final List<AccessoryInventoryUnit> slots = WoolEntityAccessoryInventories.PLAYER.stream().toList();
    @Unique
    private static final Identifier FULL_TEXTURE = Identifier.of(Wool.MOD_ID, "textures/gui/accessory/full.png");
    @Unique
    private static final Identifier RIGHT_CONNECTED_TEXTURE = Identifier.of(Wool.MOD_ID, "textures/gui/accessory/right_connected.png");
    @Unique
    private static final Identifier LEFT_CONNECTED_TEXTURE = Identifier.of(Wool.MOD_ID, "textures/gui/accessory/left_connected.png");
    @Unique
    private static final Identifier RIGHT_UPPER_CORNER_TEXTURE = Identifier.of(Wool.MOD_ID, "textures/gui/accessory/right_upper_corner.png");
    @Unique
    private static final Identifier LEFT_UPPER_CORNER_TEXTURE = Identifier.of(Wool.MOD_ID, "textures/gui/accessory/left_upper_corner.png");
    @Unique
    private static final Identifier RIGHT_LOWER_CORNER_TEXTURE = Identifier.of(Wool.MOD_ID, "textures/gui/accessory/right_lower_corner.png");
    @Unique
    private static final Identifier LEFT_LOWER_CORNER_TEXTURE = Identifier.of(Wool.MOD_ID, "textures/gui/accessory/left_lower_corner.png");
    @Unique
    private static final Identifier RIGHT_SIDE_TEXTURE = Identifier.of(Wool.MOD_ID, "textures/gui/accessory/right_side.png");
    @Unique
    private static final Identifier LEFT_SIDE_TEXTURE = Identifier.of(Wool.MOD_ID, "textures/gui/accessory/left_side.png");
    @Unique
    private static final Identifier CONNECTING_CORNER_TEXTURE = Identifier.of(Wool.MOD_ID, "textures/gui/accessory/connecting_corner.png");

    public InventoryScreenAccessorySlotsMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }


    @Inject(method = "render", at = @At("TAIL"))
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo info) {}
}
