package me.korbyntalks.skulk.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import me.korbyntalks.skulk.Mouse;

import net.minecraft.client.render.Window;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(GameRenderer.class)
public abstract class SkulkMixin_Mouse {

    @Shadow
    private boolean displayActive;
    @Shadow
    private Minecraft minecraft;
    @Shadow
    protected abstract void renderWorld(float tickDelta);
    @Shadow
    public abstract void setupGuiState();

    /**
     * @author korbyntalks
     * @reason WIP
     */
    @Overwrite
    public final void render(float tickDelta) {
        Object object;
        if (this.displayActive && !Display.isActive()) {
            this.minecraft.pauseGame();
        }
        this.displayActive = Display.isActive();
        if (this.minecraft.focused) {
            Mouse.lock(this.minecraft);
            int n3 = 1;
            if (this.minecraft.options.invertMouseY) {
                n3 = -1;
            }
            float n4 = Mouse.x(this.minecraft);
            float n5 = 0;

            float f = n5 * n3;
            object = this.minecraft.player;
            float f3 = ((Entity)object).pitch;
            float f4 = ((Entity)object).yaw;
            ((Entity)object).yaw = (float)(((Entity)object).yaw + n4 * 0.15);
            ((Entity)object).pitch = (float)(((Entity)object).pitch - f * 0.15);
            if (((Entity)object).pitch < -90.0f) {
                ((Entity)object).pitch = -90.0f;
            }
            if (((Entity)object).pitch > 90.0f) {
                ((Entity)object).pitch = 90.0f;
            }
            ((Entity)object).lastPitch += ((Entity)object).pitch - f3;
            ((Entity)object).lastYaw += ((Entity)object).yaw - f4;
        }
        object = new Window(this.minecraft.width, this.minecraft.height);
        int n = ((Window)object).getWidth();
        int n6 = ((Window)object).getHeight();
        float n7 = Mouse.basicMouseX() * n / this.minecraft.width;
        float n8 = n6 - Mouse.basicMouseY() * n6 / this.minecraft.height - 1;
        if (this.minecraft.world != null) {
            this.renderWorld(tickDelta);
            this.minecraft.gui.render(tickDelta);
        } else {
            GL11.glViewport(0, 0, this.minecraft.width, this.minecraft.height);
            GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL11.glClear(16640);
            GL11.glMatrixMode(GL11.GL_PROJECTION);
            GL11.glLoadIdentity();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
            this.setupGuiState();
        }
        if (this.minecraft.screen != null) {
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
            this.minecraft.screen.render((int)n7, (int)n8, tickDelta);
        }
        Thread.yield();
        Display.update();
    }

}
