package me.korbyntalks.skulk;

public class Mouse {
    /**
     * Gets the X position of where the mouse is. (Might still get its position when it is out of the Window, haven't tested it.)
     * @return float
     */
    public static float basicMouseX() {
        return org.lwjgl.input.Mouse.getX();
    }
    /**
     * Gets the Y position of where the mouse is. (Might still get its position when it is out of the Window, haven't tested it.)
     * @return float
     */
    public static float basicMouseY() {
        return org.lwjgl.input.Mouse.getY();
    }
    /**
     * Easier way to use org.lwjgl.input.Mouse.getDX();
     * @return float
     */
    public static float dX() {
        return (float)org.lwjgl.input.Mouse.getDX();
    }
    /**
     * Easier way to use org.lwjgl.input.Mouse.getDY();
     * @return float
     */
    public static float dY() {
        return (float)org.lwjgl.input.Mouse.getDY();
    }
    /**
     * Ungrabs the Mouse and sets the Cursors position (x, y).
     */
    public static void unlock(int x, int y) {
        org.lwjgl.input.Mouse.setCursorPosition(x, y);
        org.lwjgl.input.Mouse.setGrabbed(false);
    }
}
