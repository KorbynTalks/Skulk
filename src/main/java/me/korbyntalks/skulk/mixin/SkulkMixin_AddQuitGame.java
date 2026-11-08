package me.korbyntalks.skulk.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = {"net.minecraft.client.gui.screen.menu.GameMenuScreen", "net.minecraft.client.gui.screen.menu.TitleScreen"})
@SuppressWarnings({"all"})
public final class SkulkMixin_AddQuitGame extends Screen {
    int finalHeight = 144;

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 1))
    private void addQuitGame(CallbackInfo ci) {
        if(this.getClass().getName() == "net.minecraft.client.gui.screen.menu.TitleScreen") {
            finalHeight = 156;
        }

        this.buttons.add(new ButtonWidget(5, this.width / 2 - 100, this.height / 4 + finalHeight, "Quit Game"));
    }

    @Inject(method = "buttonClicked", at = @At(value = "HEAD"))
    private void quitGame(ButtonWidget par1, CallbackInfo ci) {
        if (par1.id == 5) {
            this.minecraft.shutdown();
        }
    }
}
