package me.korbyntalks.skulk.mixin;

import me.korbyntalks.skulk.config.Config;
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
     * @reason For more precise mouse movement, the variables that store the data have to be floats.
     * The original method uses integers, and it doesn't help that a lot of variables in the
     * original method are unused, using memory for no reason.
     */
    @Overwrite
    public final void render(float tickDelta) {
        Object object;
        if (this.displayActive && !Display.isActive()) {
            this.minecraft.pauseGame();
        }
        this.displayActive = Display.isActive();
        if (this.minecraft.focused) {
            int invert = 1;
            if (this.minecraft.options.invertMouseY) {
                invert = -1;
            }
            float mouseX = Mouse.dX();
            float mouseY = Mouse.dY();

            if(this.minecraft.screen == null && !org.lwjgl.input.Mouse.isGrabbed()) {
                org.lwjgl.input.Mouse.setGrabbed(true);
            }

            float finalMouseY = mouseY * invert;
            object = this.minecraft.player;
            float playerPitch = ((Entity)object).pitch;
            float playerYaw = ((Entity)object).yaw;
            ((Entity)object).yaw = (float)(((Entity)object).yaw + mouseX * Config.sensitivity());
            ((Entity)object).pitch = (float)(((Entity)object).pitch - finalMouseY * Config.sensitivity());
            if (((Entity)object).pitch < -90.0f) {
                ((Entity)object).pitch = -90.0f;
            }
            if (((Entity)object).pitch > 90.0f) {
                ((Entity)object).pitch = 90.0f;
            }
            ((Entity)object).lastPitch += ((Entity)object).pitch - playerPitch;
            ((Entity)object).lastYaw += ((Entity)object).yaw - playerYaw;
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
