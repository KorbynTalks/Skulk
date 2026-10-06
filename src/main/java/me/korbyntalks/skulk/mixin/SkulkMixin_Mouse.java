package me.korbyntalks.skulk.mixin;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;

@Mixin(Mouse.class)
public abstract class SkulkMixin_Mouse {

    @Shadow
    private Component component;
    @Shadow
    private int scaledX;
    @Shadow
    private int scaledY;
    @Shadow
    private int f_87086150;
    @Shadow
    private Robot robot;
    @Shadow
    public int x;
    @Shadow
    public int y;

    /**
     * @author korbyntalks
     * @reason WIP, does nothing.
     */
    @Overwrite
    public final void tick() {
        Point var1 = MouseInfo.getPointerInfo().getLocation();
        Point var2 = this.component.getLocationOnScreen();
        this.robot.mouseMove(this.scaledX, this.scaledY);
        this.scaledX = var2.x + this.component.getWidth() / 2;
        this.scaledY = var2.y + this.component.getHeight() / 2;
        if (this.f_87086150 == 0) {
            this.x = var1.x - this.scaledX;
            this.y = var1.y - this.scaledY;
        } else {
            this.x = this.y = 0;
            --this.f_87086150;
        }
    }
}
