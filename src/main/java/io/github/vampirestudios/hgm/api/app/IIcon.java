package io.github.vampirestudios.hgm.api.app;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.api.utils.RenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public interface IIcon {

    Identifier getIconAsset();

    int getIconSize();

    int getGridWidth();

    int getGridHeight();

    int getU();

    int getV();

    default void draw(MinecraftClient mc, int x, int y) {
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(getIconAsset());
        int size = getIconSize();
        int assetWidth = getGridWidth() * size;
        int assetHeight = getGridHeight() * size;
        RenderUtil.drawRectWithTexture(x, y, getU(), getV(), size, size, size, size, assetWidth, assetHeight);
    }
}
