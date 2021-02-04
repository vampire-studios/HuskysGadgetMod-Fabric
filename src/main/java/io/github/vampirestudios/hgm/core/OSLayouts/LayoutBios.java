package io.github.vampirestudios.hgm.core.OSLayouts;

import io.github.vampirestudios.hgm.api.app.Layout;
import io.github.vampirestudios.hgm.api.utils.RenderUtil;
import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import static io.github.vampirestudios.hgm.core.BaseDevice.SCREEN_HEIGHT;
import static io.github.vampirestudios.hgm.core.BaseDevice.SCREEN_WIDTH;

public class LayoutBios extends Layout {

    public LayoutBios() {
        super(908, 472);
        this.setLocation(0, 10);
    }

    @Override
    public void render(MatrixStack matrixStack, BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        mc.getTextureManager().bindTexture(BaseDevice.WALLPAPERS.get(BaseDevice.currentWallpaper));
        RenderUtil.drawRectWithFullTexture(x, y, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        /*RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 20);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 35);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 50);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 65);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 80);
        RenderUtil.drawApplicationIcon(ApplicationManager.getApplication("hgm:icons"), x + 5, y + 95);*/
    }

}
