package io.github.vampirestudios.hgm.core.OSLayouts;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.vampirestudios.hgm.api.app.Layout;
import io.github.vampirestudios.hgm.api.os.OperatingSystem;
import io.github.vampirestudios.hgm.api.utils.RenderUtil;
import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static io.github.vampirestudios.hgm.core.BaseDevice.*;

public class LayoutDesktopOS extends Layout {

    private OperatingSystem OS;

    public LayoutDesktopOS(OperatingSystem OS) {
        super(0, 10, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.OS = OS;
    }

    @Override
    public void render(BaseDevice laptop, MinecraftClient mc, int x, int y, int mouseX, int mouseY, boolean windowActive, float partialTicks) {
        Color bgColor = new Color(laptop.getSettings().getColourScheme().getBackgroundColour()).brighter().brighter();
        float[] hsb = Color.RGBtoHSB(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), null);
        bgColor = new Color(Color.HSBtoRGB(hsb[0], hsb[1], 1.0F));

        if (BaseDevice.getSystem().getSettings().hasWallpaperOrColor().equals("Wallpaper")) {
            GL11.glColor4f(bgColor.getRed() / 255F, bgColor.getGreen() / 255F, bgColor.getBlue() / 255F, 0.3F);
//            mc.getTextureManager().bindTexture(getWallapapers().get(getCurrentWallpaper()));
            mc.getTextureManager().bindTexture(new Identifier("hgm:textures/gui/test2.png"));
            RenderUtil.drawRectWithFullTexture(x, y, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

            if (getWallapapers().get(getCurrentWallpaper()).equals("hgm:textures/gui/wallpapers/default.png")) {
                GL11.glColor4f(bgColor.getRed() / 255F, bgColor.getGreen() / 255F, bgColor.getBlue() / 255F, 0.3F);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.7f);
                GlStateManager.enableBlend();
                mc.getTextureManager().bindTexture(OS.bootLogo());
                this.blit(x + 170, y + 100, 2, 94, 128, 30);
            }
        } else {
            fill(x, y, x + SCREEN_WIDTH, y + SCREEN_HEIGHT, new Color(bgColor.getRed() / 255F, bgColor.getGreen() / 255F, bgColor.getBlue() / 255F, 1.0F).getRGB());
        }
    }

}
