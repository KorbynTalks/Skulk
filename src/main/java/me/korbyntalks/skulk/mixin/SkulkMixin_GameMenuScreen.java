package me.korbyntalks.skulk.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.menu.GameMenuScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public final class SkulkMixin_GameMenuScreen extends Screen {
    @Inject(method = "init", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 4))
    private void addQuitGame(CallbackInfo ci) {
        this.buttons.add(new ButtonWidget(5, this.width / 2 - 100, this.height / 4 + 144, "Quit Game"));
    }

    @Inject(method = "buttonClicked", at = @At(value = "HEAD"))
    private void quitGame(ButtonWidget par1, CallbackInfo ci) {
        if (par1.id == 5) {
            this.minecraft.shutdown();
            System.exit(0);
        }
    }
}
