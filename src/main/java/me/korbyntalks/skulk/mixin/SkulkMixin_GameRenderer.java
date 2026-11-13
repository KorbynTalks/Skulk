package me.korbyntalks.skulk.mixin;

import me.korbyntalks.skulk.Mouse;
import me.korbyntalks.skulk.Skulk;
import me.korbyntalks.skulk.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.mob.player.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;

import net.minecraft.client.render.Window;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(GameRenderer.class)
public abstract class SkulkMixin_GameRenderer {
    @Shadow
    private Minecraft minecraft;
    @Shadow
    protected abstract void renderWorld(float tickDelta);
    @Shadow
    public abstract void setupGuiState();
    @Unique
    private int invert() {
        if(minecraft.options.invertMouseY) {
            return -1;
        }
        return 1;
    }
    @Unique
    private ClientPlayerEntity player;

    @Unique
    protected final Thread mouseThread = new Thread(() -> {
        Thread.currentThread().setName("mouseThread");

        if (Config.GrabDebug() == 1) {
            Skulk.LOGGER.info("{} started.", Thread.currentThread().getName());
        }

        while(!Thread.currentThread().isInterrupted()) {
            float mouseX = Mouse.dX();
            float mouseY = Mouse.dY();

            if (Config.GrabImprovedMouse() == 1) {
                if(minecraft.world == null) {
                    player = null;
                }

                if (minecraft.screen == null) {
                    if(!org.lwjgl.input.Mouse.isGrabbed()) {
                        org.lwjgl.input.Mouse.setGrabbed(true);
                    }

                    if(player == null) {
                        player = minecraft.player;
                    }

                    player.yaw = (float)(player.yaw + mouseX * Config.GrabSensitivity());
                    player.pitch = (float)(player.pitch - mouseY * invert() * Config.GrabSensitivity());

                    if(player.pitch < -90.0f) {
                        player.pitch = -90.0f;
                    } else if (player.pitch > 90.0f) {
                        player.pitch = 90.0f;
                    }
                } else {
                    if(org.lwjgl.input.Mouse.isGrabbed()) {
                        org.lwjgl.input.Mouse.setGrabbed(false);
                    }
                }
            }
        }
    });

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
        if (!Display.isActive()) {
            this.minecraft.pauseGame();
        }

        if(Config.GrabImprovedMouse() == 1) {
            if(!mouseThread.isAlive()) {
                mouseThread.start();
            }
        } else {
            if(this.minecraft.screen == null) {
                this.minecraft.mouse.tick();

                float mouseX = this.minecraft.mouse.x;
                float mouseY = this.minecraft.mouse.y;

                player = minecraft.player;

                player.yaw = (float)(player.yaw + mouseX * Config.GrabSensitivity());
                player.pitch = (float)(player.pitch + mouseY * invert() * Config.GrabSensitivity());

                if(player.pitch < -90.0f) {
                    player.pitch = -90.0f;
                } else if (player.pitch > 90.0f) {
                    player.pitch = 90.0f;
                }
            } else {
                if(org.lwjgl.input.Mouse.isGrabbed()) {
                    org.lwjgl.input.Mouse.setGrabbed(false);
                }
            }
        }

        Window window = new Window(this.minecraft.width, this.minecraft.height);
        int windowWidth = window.getWidth();
        int windowHeight = window.getHeight();
        int uiMouseX = (int)Mouse.basicMouseX() * windowWidth / this.minecraft.width;
        int uiMouseY = windowHeight - (int)Mouse.basicMouseY() * windowHeight / this.minecraft.height - 1;
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
            this.minecraft.screen.render(uiMouseX, uiMouseY, tickDelta);
        }
        Display.update();
    }

}
