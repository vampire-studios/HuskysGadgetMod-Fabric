package io.github.vampirestudios.hgm_rewrite.core;

import io.github.vampirestudios.hgm.api.app.component.*;
import io.github.vampirestudios.hgm.core.DevicePainters;
import io.github.vampirestudios.hgm.core.ScreenDrawing;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

import java.text.DateFormat;
import java.util.Date;

public class Laptop extends LightweightGuiDescription {

    private WPlainPanel root = new WPlainPanel();
    private WGridPanel taskbar = new WGridPanel();
    private WGridPanel tray = new WGridPanel();

    private Laptop() {
        this.setRootPanel(root);
        root.setSize(400, 220);

        WSprite wallpaper = new WSprite(new Identifier("hgm:textures/gui/retro_wallpaper.jpg"));

        root.add(wallpaper, 0, 0, 400, 220);

        root.add(taskbar, 2, root.getHeight()-(20));
        taskbar.setSize(root.getWidth()-4, 14);

        int items = 4;
        taskbar.add(tray, (taskbar.getWidth()/18)-(items+2), 0, items+2, 1);

        Label time = new Label(new LiteralText("0:00"), 0xFFFFFF) {
            @Override
            public void paintBackground(int x, int y) {
                String date = DateFormat.getTimeInstance(DateFormat.DEFAULT).format(new Date());
                ScreenDrawing.drawString(new MatrixStack(), date, x-4, y+6, color);
            }
        };

        tray.add(time, items, 0, 2, 1);
    }

    @Override
    public void addPainters() {
        root.setBackgroundPainter(DevicePainters.BEVEL_FRAME);
        taskbar.setBackgroundPainter(DevicePainters.translucentColorRect(2, 0x40000000));
    }

    public static Device asDevice() {
        return new Device(new Laptop());
    }

}
