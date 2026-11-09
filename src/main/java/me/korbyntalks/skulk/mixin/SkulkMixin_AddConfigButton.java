package me.korbyntalks.skulk.mixin;

import me.korbyntalks.skulk.gui.screen.SkulkConfigScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.menu.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = OptionsScreen.class)
public class SkulkMixin_AddConfigButton extends Screen {
    @Inject(method = "init", at = @At(value = "HEAD"))
    private void AddButton(CallbackInfo ci) {
        this.buttons.clear();
        this.buttons.add(new OptionButtonWidget(300, this.width / 2 +5, this.height / 6 + 96, "Skulk Config"));
    }

    @Inject(method = "buttonClicked", at = @At(value = "TAIL"))
    private void buttonClicked(ButtonWidget par1, CallbackInfo ci) {
        if(par1.id == 300) {
            this.minecraft.openScreen(new SkulkConfigScreen(this));
        }
    }
}
