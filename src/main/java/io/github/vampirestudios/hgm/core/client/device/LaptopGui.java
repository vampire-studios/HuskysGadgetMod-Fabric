package io.github.vampirestudios.hgm.core.client.device;

import java.text.DateFormat;
import java.util.Date;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LibGuiClient;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class LaptopGui extends LightweightGuiDescription {
    DateFormat TIME_FORMAT = DateFormat.getTimeInstance(DateFormat.SHORT);
    
    WPlainPanel root = new WPlainPanel();
    WGridPanel taskbar = new WGridPanel();
    WGridPanel tray = new WGridPanel();
    
    public LaptopGui() {
        this.setRootPanel(root);
        root.setSize(400, 240);
        
        WSprite wallpaper = new WSprite(new Identifier("hgm:textures/gui/laptop_wallpaper_2.png"));
        
        root.add(wallpaper, 0, 0, 400, 240);
        
        taskbar.setBackgroundPainter(BackgroundPainter.VANILLA);
        root.add(taskbar, 2, root.getHeight()-(20) );
        taskbar.setSize(root.getWidth()-4, 14);
        
        WSprite startButton = new WSprite(new Identifier("textures/item/redstone.png"));
        taskbar.add(startButton, 0, 0);
        
        int items = 4;
        taskbar.add(tray, (taskbar.getWidth()/18)-(items+2), 0, items+2, 1);
        
        
        WLabel time = new WLabel(new LiteralText("0:00"), 0xFFFFFF) {
            @Override
            public void paintBackground(int x, int y) {
                String date = TIME_FORMAT.format(new Date());
                ScreenDrawing.drawString(date, x-1, y+6, LibGuiClient.config.darkMode ? darkmodeColor : color);
            }
        };
        
        tray.add(time, items, 0, 2, 1);
    }
    
    
    
    @Override
    public void addPainters() {
        root.setBackgroundPainter(DevicePainters.BEVEL_FRAME);
        taskbar.setBackgroundPainter(DevicePainters.translucentColorRect(2, 0x40000000));
        tray.setBackgroundPainter(DevicePainters.translucentColorRect(0, 0x40000000));
    }
}
