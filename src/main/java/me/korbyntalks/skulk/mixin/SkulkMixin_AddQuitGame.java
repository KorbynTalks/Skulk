package me.korbyntalks.skulk.mixin;

import me.korbyntalks.skulk.widget.AdjustableButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(targets = {"net.minecraft.client.gui.screen.menu.GameMenuScreen", "net.minecraft.client.gui.screen.menu.TitleScreen"})
@SuppressWarnings({"all"})
public final class SkulkMixin_AddQuitGame extends Screen {
    private String[] titleScreenClassNames = {"TitleScreen", "C_95462098"};

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 3, shift = At.Shift.AFTER))
    private void addQuitGame(CallbackInfo ci) {
        boolean isPauseScreen = Arrays.stream(titleScreenClassNames).anyMatch(this.getClass().getSimpleName()::equals);

        if(isPauseScreen) {
            this.buttons.add(new AdjustableButtonWidget(5, this.width / 2 + 2, this.height / 4 + 132, 98, 20, "Quit Game"));
            this.buttons.set(3, new AdjustableButtonWidget(0, this.width / 2 - 100, this.height / 4 + 132, 98, 20, "Options..."));
            ((ButtonWidget) this.buttons.get(2)).active = true;
        } else {
            this.buttons.add(new ButtonWidget(5, this.width / 2 - 100, this.height / 4 + 144, "Quit Game"));
        }
    }

    @Inject(method = "buttonClicked", at = @At(value = "HEAD"))
    private void quitGame(ButtonWidget par1, CallbackInfo ci) {
        if (par1.id == 5) {
            this.minecraft.shutdown();
        }
    }
}
