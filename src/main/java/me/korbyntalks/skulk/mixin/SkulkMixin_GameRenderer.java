package me.korbyntalks.skulk.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.korbyntalks.skulk.Mouse;
import me.korbyntalks.skulk.config.Config;
import net.minecraft.client.entity.mob.player.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;

import net.minecraft.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public abstract class SkulkMixin_GameRenderer {

    @WrapOperation(
            method = "render",
            at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;yaw:F", opcode = Opcodes.PUTFIELD)
    )
    private void betterYaw(Entity instance, float value, Operation<Void> original, @Local ClientPlayerEntity player) {
        if(Config.GrabImprovedMouse()) {
            player.yaw = (float)(player.yaw + Mouse.dX() * Config.GrabSensitivity());
        } else {
            original.call(instance, value);
        }
    }

}
