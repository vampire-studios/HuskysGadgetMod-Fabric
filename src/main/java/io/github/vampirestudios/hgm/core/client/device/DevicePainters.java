package io.github.vampirestudios.hgm.core.client.device;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.core.ScreenDrawing;
import net.minecraft.util.Identifier;

public class DevicePainters {
    private static Identifier LAPTOP_GUI = new Identifier(HuskysGadgetMod.MOD_ID, "textures/gui/laptop.png");
    
    public static BackgroundPainter BEVEL_FRAME = (x, y, widget)-> {
        int BORDER = 10;
        
        /* Corners */
        ScreenDrawing.textureFillGui(x-BORDER,              y-BORDER,               BORDER, BORDER, LAPTOP_GUI,  0,  0); //TOP-LEFT
        ScreenDrawing.textureFillGui(x + widget.getWidth(), y-BORDER,               BORDER, BORDER, LAPTOP_GUI, 11,  0); //TOP-RIGHT
        ScreenDrawing.textureFillGui(x + widget.getWidth(), y + widget.getHeight(), BORDER, BORDER, LAPTOP_GUI, 11, 11); // BOTTOM-RIGHT
        ScreenDrawing.textureFillGui(x-BORDER,              y + widget.getHeight(), BORDER, BORDER, LAPTOP_GUI,  0, 11); // BOTTOM-LEFT

        /* Edges */
        ScreenDrawing.textureFillGui(x,                     y-BORDER,               widget.getWidth(), BORDER,             LAPTOP_GUI, 10,  0,      1, BORDER); //TOP
        ScreenDrawing.textureFillGui(x + widget.getWidth(), y,                      BORDER,            widget.getHeight(), LAPTOP_GUI, 11, 10, BORDER,      1); //RIGHT
        ScreenDrawing.textureFillGui(x,                     y + widget.getHeight(), widget.getWidth(), BORDER,             LAPTOP_GUI, 10, 11,      1, BORDER); //BOTTOM
        ScreenDrawing.textureFillGui(x-BORDER,              y,                      BORDER,            widget.getHeight(), LAPTOP_GUI,  0, 11, BORDER,      1); //LEFT
        
        /* Background */
        ScreenDrawing.colorFill(x, y, widget.getWidth(), widget.getHeight(), 0x171717);
    };
    
    public static BackgroundPainter TOOLBAR = (x, y, widget) -> {
        ScreenDrawing.colorFill(x-2, y-2, widget.getWidth()+4, widget.getHeight()+4, 0x505050);
        ScreenDrawing.colorHollowRect(x-2, y-2, widget.getWidth()+4, widget.getHeight()+4, 0x707070);
    };
    
    public static BackgroundPainter translucentColorRect(int margin, int color) {
        return (x, y, widget) -> {
            ScreenDrawing.translucentColorFill(x-margin, y-margin, widget.getWidth()+(margin*2), widget.getHeight()+(margin*2), color);
        };
    }
}
