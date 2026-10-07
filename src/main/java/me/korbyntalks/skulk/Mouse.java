package me.korbyntalks.skulk;

import net.minecraft.client.Minecraft;

import java.awt.*;

public class Mouse {
    public static float basicMouseX() {
        float finalX = 0;

        finalX = org.lwjgl.input.Mouse.getX();

        return finalX;
    }
    public static float basicMouseY() {
        float finalY = 0;

        finalY = org.lwjgl.input.Mouse.getY();

        return finalY;
    }
    public static float x(Minecraft minecraft) {
        float finalX = 0;

        finalX = (float)org.lwjgl.input.Mouse.getDX();

        return finalX;
    }
    public static void lock(Minecraft m) {
        org.lwjgl.input.Mouse.setCursorPosition(m.width / 2, m.height / 2);
    }
}
