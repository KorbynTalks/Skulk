package me.korbyntalks.skulk.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.korbyntalks.skulk.Mouse;
import me.korbyntalks.skulk.Skulk;
import me.korbyntalks.skulk.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;

import net.minecraft.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class SkulkMixin_GameRenderer {

    @Shadow
    private Minecraft minecraft;

    @Inject(
            method = "render",
            at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Mouse;getDY()I", shift = At.Shift.AFTER)
    )
    private void grabMouse(float par1, CallbackInfo ci) {
        if(minecraft.screen == null && !org.lwjgl.input.Mouse.isGrabbed()) {
            org.lwjgl.input.Mouse.setGrabbed(true);
            if(Config.GrabDebug() == 1) {
                Skulk.LOGGER.info("Mouse grabbed.");
            }
        }
    }

    @WrapOperation(
            method = "render",
            at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;yaw:F", opcode = Opcodes.PUTFIELD)
    )
    private void betterYaw(Entity instance, float value, Operation<Void> original) {
        if(Config.GrabImprovedMouse() == 1) {
            value = (float)(value + Mouse.dX() * Config.GrabSensitivity());
        }
        original.call(instance, value);
    }
    @WrapOperation(
            method = "render",
            at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;pitch:F", opcode = Opcodes.PUTFIELD)
    )
    private void betterPitch(Entity instance, float value, Operation<Void> original) {
        if(Config.GrabImprovedMouse() == 1) {
            value = (float)(value + Mouse.dY() * 1 * Config.GrabSensitivity());
        }
        original.call(instance, value);
    }

}
