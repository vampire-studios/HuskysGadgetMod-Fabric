package io.github.vampirestudios.hgm.core;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.api.app.Layout;
import io.github.vampirestudios.hgm.api.app.component.*;
import io.github.vampirestudios.hgm.api.app.component.render.BackgroundPainter;
import io.github.vampirestudios.hgm.core.OSLayouts.LayoutStartMenu;
import io.github.vampirestudios.hgm.utils.GuiIcon;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

import java.text.DateFormat;
import java.util.Date;

public class LaptopGui extends LightweightGuiDescription {

    DateFormat TIME_FORMAT = DateFormat.getTimeInstance(DateFormat.DEFAULT);

    private WPlainPanel root = new WPlainPanel();
    private WGridPanel taskbar = new WGridPanel();
    private WGridPanel tray = new WGridPanel();

    public LaptopGui() {
        this.setRootPanel(root);
        root.setSize(400, 220);

        WSprite wallpaper = new WSprite(new Identifier("hgm:textures/gui/laptop_wallpaper_2.png"));

        root.add(wallpaper, 0, 0, 400, 220);

        /*
         * Taskbar's vertical position's a little weird here: The taskbar is 20px high, but icons
         * are 14px. That's 6px difference, and we could do worse than to have a 3px margin on all
         * sides. We'll make the difference back up with the background painter for this widget.
         */
        taskbar.setBackgroundPainter(BackgroundPainter.VANILLA);
        root.add(taskbar, 2, root.getHeight()-(20));
        taskbar.setSize(root.getWidth()-4, 14);

        /*Button startButton = new Button(0, -4, new Identifier("textures/item/redstone.png"), 0, 0, 16, 16);
        startButton.setClickListener((mouseX, mouseY, mouseButton) -> {
            if (mouseButton == 0) {
                Layout layout = new LayoutStartMenu();
                layout.init();
                root.add(layout, 10, 10);
            }
        });
        taskbar.add(startButton, 0, 0);*/

        WButton startButton = new WButton(new Icon(0, -4, new GuiIcon() {
            @Override
            public int getWidth() {
                return 16;
            }

            @Override
            public int getHeight() {
                return 16;
            }

            @Override
            public int getU() {
                return 0;
            }

            @Override
            public int getV() {
                return 0;
            }

            @Override
            public void renderAt(int var1, int var2, float var3, boolean var4, boolean var5) {

            }

            @Override
            public Identifier getTexture() {
                return new Identifier("textures/item/redstone.png");
            }
        }));
        startButton.setOnClick(() -> {
            Layout layout = new LayoutStartMenu();
            layout.init();
            layout.setBackground(BackgroundPainter.VANILLA);
            layout.
            root.add(layout, root.x + 10, root.y + 10);
        });
        taskbar.add(startButton, 0, 0);

        int items = 4;
        taskbar.add(tray, (taskbar.getWidth()/18)-(items+2), 0, items+2, 1);

        Label time = new Label(new LiteralText("0:00"), 0xFFFFFF) {
            @Override
            public void paintBackground(int x, int y) {
                String date = TIME_FORMAT.format(new Date());
                ScreenDrawing.drawString(date, x-4, y+6, HuskysGadgetMod.config.laptopSettings.darkMode ? darkmodeColor : color);
            }
        };

        tray.add(time, items, 0, 2, 1);

        /*WPlainPanel applicationTopBar = new WPlainPanel();
        applicationTopBar.setBackgroundPainter((left, top, panel) ->
                ScreenDrawing.translucentColorFill(left, top, panel.width, panel.height, 0x773d3d3d));
        root.add(applicationTopBar, 20, root.getHeight() - 200);
        applicationTopBar.setSize(360, 5);

        WPlainPanel applicationBody = new WPlainPanel();
        applicationBody.setBackgroundPainter((left, top, panel) -> {
            ScreenDrawing.colorFill(left, top, panel.width, panel.height, 0xFFFFFF);
        });
        applicationTopBar.add(applicationBody, 0, root.getHeight() - 210);
        applicationBody.setSize(360, 140);*/
    }

    @Override
    public void addPainters() {
        root.setBackgroundPainter(DevicePainters.BEVEL_FRAME);
        taskbar.setBackgroundPainter(DevicePainters.translucentColorRect(2, 0x40000000));
        tray.setBackgroundPainter(DevicePainters.translucentColorRect(0, 0x40000000));
    }

}