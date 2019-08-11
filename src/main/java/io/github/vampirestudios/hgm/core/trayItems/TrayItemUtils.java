package io.github.vampirestudios.hgm.core.trayItems;

import io.github.vampirestudios.hgm.api.app.Layout;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.core.ScreenDrawing;

import java.awt.*;

public class TrayItemUtils {

    public static Layout createMenu(int menuWidth, int menuHeight) {
        Layout layout = new Layout.Context(menuWidth, menuHeight);
        layout.y = 70;
        layout.setBackground((gui, mc, x, y, width, height, mouseX, mouseY, windowActive) ->
                ScreenDrawing.colorFill(x, y, 1 + width - 1, 1 + height - 1, new Color(BaseDevice.getSystem().getSettings().getColourScheme().getMainApplicationBarColour()).darker().getRGB()));
        return layout;
    }

}
