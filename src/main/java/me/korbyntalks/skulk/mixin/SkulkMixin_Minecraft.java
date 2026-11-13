package me.korbyntalks.skulk.mixin;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class SkulkMixin_Minecraft {
    @Shadow
    public int width;
    @Shadow
    public int height;

    @Redirect(method = "lockMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Mouse;lock()V"))
    private void lockMouse(net.minecraft.client.Mouse instance) {
        org.lwjgl.input.Mouse.setCursorPosition(width / 2, height / 2);
        org.lwjgl.input.Mouse.setGrabbed(true);
    }

    @Inject(method = "shutdown", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;destroy()V"))
    private void fullyShutdown(CallbackInfo ci) {
        Display.destroy();
        System.exit(0);
    }
}