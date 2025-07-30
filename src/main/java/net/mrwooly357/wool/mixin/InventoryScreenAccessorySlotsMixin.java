package net.mrwooly357.wool.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.accessory.network.packet.custom.c2s.OpenPlayerAccessoryInventoryC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenAccessorySlotsMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {

    @Unique
    private TexturedButtonWidget button;
    @Unique
    private int buttonX;
    @Unique
    private int buttonY;

    public InventoryScreenAccessorySlotsMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }


    @Inject(method = "<init>", at = @At("TAIL"))
    public void injectConstructor(PlayerEntity player, CallbackInfo info) {
        buttonX = 0;
        buttonY = 0;
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void injectInit(CallbackInfo info) {
        buttonX = x + 81;
        buttonY = y + 48;
        ButtonTextures buttonTextures = new ButtonTextures(Identifier.of(Wool.MOD_ID, "accessory/button/unfocused"), Identifier.of(Wool.MOD_ID, "accessory/button/focused"));
        button = new TexturedButtonWidget(buttonX, buttonY, 8, 8, buttonTextures, button1 -> ClientPlayNetworking.send(new OpenPlayerAccessoryInventoryC2SPacket()));

        addDrawableChild(button);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void injectRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo info) {
        button.setX(x + 81);
    }
}
