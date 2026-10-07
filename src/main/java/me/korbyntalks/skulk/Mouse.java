package me.korbyntalks.skulk;

import net.minecraft.client.Minecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Mouse {
    /**
     * Gets the X position of where the mouse is. (Might still get its position when it is out of the Window, haven't tested it.
     * @return float
     */
    public static float basicMouseX() {
        float finalX = 0;

        finalX = org.lwjgl.input.Mouse.getX();

        return finalX;
    }
    /**
     * Gets the Y position of where the mouse is. (Might still get its position when it is out of the Window, haven't tested it.
     * @return float
     */
    public static float basicMouseY() {
        float finalY = 0;

        finalY = org.lwjgl.input.Mouse.getY();

        return finalY;
    }
    public static float x() {
        float finalX = 0;

        finalX = (float)org.lwjgl.input.Mouse.getDX();

        return finalX;
    }
    public static float y() {
        float finalY = 0;

        finalY = (float)org.lwjgl.input.Mouse.getDY();

        return finalY;
    }
    public static void lock(Minecraft m) {

    }
}
