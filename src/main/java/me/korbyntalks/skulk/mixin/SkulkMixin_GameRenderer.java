package me.korbyntalks.skulk.mixin;

import me.korbyntalks.skulk.Mouse;
import me.korbyntalks.skulk.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.mob.player.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;

import net.minecraft.client.render.Window;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GameRenderer.class)
public abstract class SkulkMixin_GameRenderer {

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
     * @reason There are unused stuff in the unmodified method that probably wastes some resources, negligible amounts, but I don't care.
     * I don't want a block game using resources that it is not using.
     * There isn't any reason to modify this method except for fixing the weird mouse behaviour and this mod does that already.
     *
     * Thread.yield() is also useless so that is removed too.
     */
    @Overwrite
    public final void render(float tickDelta) {
        if (this.displayActive && !Display.isActive()) {
            this.minecraft.pauseGame();
        }
        this.displayActive = Display.isActive();

        if(Config.GrabImprovedMouse() == 1) {
            Mouse.mouseMovement(this.minecraft);
        } else {
            if(this.minecraft.focused) {
                this.minecraft.mouse.tick();

                float mouseX = this.minecraft.mouse.x;
                float mouseY = this.minecraft.mouse.y;

                int invert = 1;
                if(this.minecraft.options.invertMouseY) {
                    invert = -1;
                }

                ClientPlayerEntity player = minecraft.player;

                player.yaw = (float)(player.yaw + mouseX * Config.GrabSensitivity());
                player.pitch = (float)(player.pitch + mouseY * invert * Config.GrabSensitivity());

                if(player.pitch < -90.0f) {
                    player.pitch = -90.0f;
                } else if (player.pitch > 90.0f) {
                    player.pitch = 90.0f;
                }

            }
        }

        Window window = new Window(this.minecraft.width, this.minecraft.height);
        int n = window.getWidth();
        int n6 = window.getHeight();
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
        Display.update();
    }

}
