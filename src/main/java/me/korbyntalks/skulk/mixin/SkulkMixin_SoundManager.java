package me.korbyntalks.skulk.mixin;

import me.korbyntalks.skulk.config.Config;
import net.minecraft.client.sound.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SoundEngine.class)
@SuppressWarnings({"all"})
public abstract class SkulkMixin_SoundManager {

    @ModifyArg(method = "playMusic", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystem;newStreamingSource(ZLjava/lang/String;Ljava/net/URL;Ljava/lang/String;ZFFFIF)V"), index = 9)
    @SuppressWarnings({"NumericOverflow","divzero"})
    public final float EverlastingMusic(float f) {
        if (Config.GrabEverlastingMusic() == 1) {
            f = Float.MAX_VALUE;
        } else {
            f = 32.0f;
        }
        return f;
    }
}
