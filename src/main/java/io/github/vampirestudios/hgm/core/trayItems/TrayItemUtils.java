package io.github.vampirestudios.hgm.core.trayItems;

import io.github.vampirestudios.hgm.api.app.Layout;
import io.github.vampirestudios.hgm.core.ScreenDrawing;

public class TrayItemUtils {

    public static Layout createMenu(int menuWidth, int menuHeight) {
        Layout layout = new Layout.Context(menuWidth, menuHeight);
        layout.y = 70;
        layout.setBackground((x, y, panel) ->
                ScreenDrawing.rect(0, 0, 0, 0, 0));
        return layout;
    }

}
