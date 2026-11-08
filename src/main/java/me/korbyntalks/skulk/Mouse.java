package me.korbyntalks.skulk;

import me.korbyntalks.skulk.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.mob.player.ClientPlayerEntity;

@SuppressWarnings({"all"})
public class Mouse {
    /**
     * Gets the X position of where the mouse is. (Might still get its position when it is out of the Window, haven't tested it.)
     * @return float
     */
    public static float basicMouseX() {
        float finalX = 0;

        finalX = org.lwjgl.input.Mouse.getX();

        return finalX;
    }
    /**
     * Gets the Y position of where the mouse is. (Might still get its position when it is out of the Window, haven't tested it.)
     * @return float
     */
    public static float basicMouseY() {
        float finalY = 0;

        finalY = org.lwjgl.input.Mouse.getY();

        return finalY;
    }
    /**
     * Easier way to use org.lwjgl.input.Mouse.getDX();
     * @return float
     */
    public static float dX() {
        float finalX = 0;

        finalX = (float)org.lwjgl.input.Mouse.getDX();

        return finalX;
    }
    /**
     * Easier way to use org.lwjgl.input.Mouse.getDY();
     * @return float
     */
    public static float dY() {
        float finalY = 0;

        finalY = (float)org.lwjgl.input.Mouse.getDY();

        return finalY;
    }
    /**
     * Ungrabs the Mouse and sets the Cursors position (x, y).
     */
    public static void unlock(int x, int y) {
        org.lwjgl.input.Mouse.setGrabbed(false);
        org.lwjgl.input.Mouse.setCursorPosition(x, y);
    }

    public static void mouseMovement(Minecraft minecraft) {
        Thread mouseThread = new Thread(() -> {
            if(minecraft.focused) {
                float mouseX = dX();
                float mouseY = dY();

                int invert = 1;
                if(minecraft.options.invertMouseY) {
                    invert = -1;
                }

                ClientPlayerEntity player = minecraft.player;

                if(minecraft.screen == null && !org.lwjgl.input.Mouse.isGrabbed()) {
                    org.lwjgl.input.Mouse.setGrabbed(true);
                }

                player.yaw = (float)(player.yaw + mouseX * Config.GrabSensitivity());
                player.pitch = (float)(player.pitch - mouseY * invert * Config.GrabSensitivity());

                if(player.pitch < -90.0f) {
                    player.pitch = -90.0f;
                } else if (player.pitch > 90.0f) {
                    player.pitch = 90.0f;
                }

            }
        });
        if(!mouseThread.isAlive()) {
            mouseThread.start();
        }
    }
}
