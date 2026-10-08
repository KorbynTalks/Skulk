package me.korbyntalks.skulk.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class SkulkMixin_Minecraft {

    @Inject(method = "pauseGame", at = @At(value = "TAIL"))
    private void UnlockCursorOnGamePause(CallbackInfo ci) {
        org.lwjgl.input.Mouse.setGrabbed(false);
    }
}
