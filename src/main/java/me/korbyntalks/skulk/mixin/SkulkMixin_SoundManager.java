package me.korbyntalks.skulk.mixin;

import me.korbyntalks.skulk.Skulk;
import net.minecraft.client.sound.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundEngine.class)
public abstract class SkulkMixin_SoundManager {

    @ModifyArg(method = "playMusic", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystem;newStreamingSource(ZLjava/lang/String;Ljava/net/URL;Ljava/lang/String;ZFFFIF)V"), index = 9)
    @SuppressWarnings({"NumericOverflow","divzero"})
    public final float EverlastingMusic(float f) {
        f = (float)(1.0 / 0.0);
        return f;
    }
}
