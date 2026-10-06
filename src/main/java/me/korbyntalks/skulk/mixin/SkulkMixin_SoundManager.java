package me.korbyntalks.skulk.mixin;

import me.korbyntalks.skulk.Skulk;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundEngine;
import net.minecraft.client.sound.Sounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystem;

@Mixin(SoundEngine.class)
public class SkulkMixin_SoundManager {
    @Shadow
    private boolean started;
    @Shadow
    private GameOptions options;
    @Shadow
    private SoundSystem system;
    @Shadow
    private Sounds music;

    /**
     * @author korbyntalks
     * @reason changes this.system.newStreamingSource to have its falloff value set to basically positive infinity so Music doesn't fade out after traveling some distance in game.
     */
    @Overwrite
    public final void playMusic (float x, float y, float z) {
        if (!this.started || !this.options.playMusic) {
            return;
        }
        if (!this.system.playing("BgMusic")) {
            Sound sound = this.music.getRandom("calm");
            this.system.newStreamingSource(true, "BgMusic", sound.url, sound.path, false, x, y, z, 2, (float)(1.0 / 0.0));
            this.system.play("BgMusic");
        }
    }
}
