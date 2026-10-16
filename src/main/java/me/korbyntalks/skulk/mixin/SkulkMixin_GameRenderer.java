package me.korbyntalks.skulk.mixin;

import me.korbyntalks.skulk.Mouse;
import me.korbyntalks.skulk.config.Config;
import net.minecraft.client.render.GameRenderer;

import net.minecraft.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public abstract class SkulkMixin_GameRenderer {
    @Redirect(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;yaw:F", opcode = Opcodes.PUTFIELD))
    private void betterYaw(Entity instance, float value) {
        instance.yaw = (float)(instance.yaw + Mouse.dX() * Config.GrabSensitivity());
    }
    @Redirect(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;pitch:F", opcode = Opcodes.PUTFIELD))
    private void betterPitch(Entity instance, float value) {
        instance.pitch = (float)(instance.pitch - Mouse.dY() * Config.GrabSensitivity());
    }

}
