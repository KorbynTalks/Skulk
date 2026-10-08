package me.korbyntalks.skulk;

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
}
