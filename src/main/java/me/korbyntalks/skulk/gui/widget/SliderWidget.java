package me.korbyntalks.skulk.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiElement;
import net.minecraft.client.gui.widget.ButtonWidget;

public class SliderWidget extends ButtonWidget {
    private int width = 200;
    private int height = 20;
    private int x;
    private int y;
    public String message;
    public int id;
    public boolean active = true;
    public boolean visible = true;

    public SliderWidget(int id, int x, int y, int width, int height, String message) {
        super(id, x, y, width, height, message);
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.message = message;
    }
}
