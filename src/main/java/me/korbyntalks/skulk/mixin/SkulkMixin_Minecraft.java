package me.korbyntalks.skulk.mixin;

import me.korbyntalks.skulk.Mouse;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class)
public abstract class SkulkMixin_Minecraft {
    @Shadow
    public int width;
    @Shadow
    public int height;

    @Inject(method = "openScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;unlockMouse()V"))
    private void unlockMouse(CallbackInfo ci) {
        Mouse.unlock(this.width / 2, this.height / 2);
    }

    @Inject(method = "shutdown", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;destroy()V"))
    private void fullyShutdown(CallbackInfo ci) {
        Display.destroy();
        System.exit(0);
    }
}