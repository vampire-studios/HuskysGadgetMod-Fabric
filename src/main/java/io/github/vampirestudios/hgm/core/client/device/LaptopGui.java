package io.github.vampirestudios.hgm.core.client.device;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.vampirestudios.hgm.core.client.device.widget.WSizedGridPanel;
import net.minecraft.util.Identifier;

public class LaptopGui extends LightweightGuiDescription {
    public LaptopGui() {
        WPlainPanel root = new WPlainPanel();
        this.setRootPanel(root);
        root.setSize(400, 240);
        //TODO: Set a BackgroundPainter on root that creates the laptop bezel around it.
        
        /*
         * Taskbar's vertical position's a little weird here: The taskbar is 20px high, but icons
         * are 14px. That's 6px difference, and we could do worse than to have a 3px margin on all
         * sides. We'll make the difference back up with the background painter for this widget.
         */
        WSizedGridPanel taskbar = new WSizedGridPanel(14);
        taskbar.setBackgroundPainter(BackgroundPainter.VANILLA);
        root.add(taskbar, 3, root.getHeight()-20+2);
        taskbar.setSize(root.getWidth()-6, 14);
        
        WSprite startButton = new WSprite(new Identifier("textures/item/redstone.png"));
        taskbar.add(startButton, 0, 0);
    }
}
