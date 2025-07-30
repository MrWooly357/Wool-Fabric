package net.mrwooly357.wool.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.mrwooly357.wool.Wool;
import net.mrwooly357.wool.entity_data_saver.EntitySimpleDataSaver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntitySimpleDataSaverMixin implements EntitySimpleDataSaver {

    @Unique
    private NbtCompound persistentData;


    @Override
    public NbtCompound getPersistentData() {
        return persistentData != null ? persistentData : new NbtCompound();
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    private void injectWriteCustomDataNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> infoReturnable) {
        if (persistentData == null)
            persistentData = new NbtCompound();

        nbt.put(Wool.MOD_ID + ".PersistentSimpleData", persistentData);
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    private void injectReadCustomDataNbt(NbtCompound nbt, CallbackInfo info) {
        String key = Wool.MOD_ID + ".PersistentSimpleData";

        persistentData = nbt.getCompound(key);
    }
}
