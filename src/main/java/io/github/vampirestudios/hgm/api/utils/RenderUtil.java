package io.github.vampirestudios.hgm.api.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.api.AppInfo;
import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.item.ItemStack;

public class RenderUtil {
    public static void renderItem(int x, int y, ItemStack stack, boolean overlay) {
        RenderSystem.disableDepthTest();
        RenderSystem.enableLighting();
        GuiLighting.enableForItems();
        MinecraftClient.getInstance().getItemRenderer().renderGuiItem(stack, x, y);
        if (overlay)
            MinecraftClient.getInstance().getItemRenderer().renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, stack, x, y);
        RenderSystem.enableAlphaTest();
        RenderSystem.disableLighting();
    }

    public static void drawRectWithTexture(double x, double y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        drawRectWithTexture(x, y, 0, u, v, width, height, textureWidth, textureHeight);
    }

    /**
     * Texture size must be 256x256
     *
     * @param x
     * @param y
     * @param z
     * @param u
     * @param v
     * @param width
     * @param height
     * @param textureWidth
     * @param textureHeight
     */
    public static void drawRectWithTexture(double x, double y, double z, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        float scale = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        buffer.begin(7, VertexFormats.POSITION_UV);
        buffer.vertex(x, y + height, z).texture(u * scale, (double) (v + textureHeight) * scale).next();
        buffer.vertex(x + width, y + height, z).texture((double) (u + textureWidth) * scale, (double) (v + textureHeight) * scale).next();
        buffer.vertex(x + width, y, z).texture((double) (u + textureWidth) * scale, v * scale).next();
        buffer.vertex(x, y, z).texture(u * scale, v * scale).next();
        tessellator.draw();
    }

    public static void drawRectWithFullTexture(double x, double y, float u, float v, int width, int height) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        buffer.begin(7, VertexFormats.POSITION_UV);
        buffer.vertex(x, y + height, 0).texture(0, 1).next();
        buffer.vertex(x + width, y + height, 0).texture(1, 1).next();
        buffer.vertex(x + width, y, 0).texture(1, 0).next();
        buffer.vertex(x, y, 0).texture(0, 0).next();
        tessellator.draw();
    }

    public static void drawRectWithTexture(double x, double y, float u, float v, int width, int height, float textureWidth, float textureHeight, int sourceWidth, int sourceHeight) {
        double scaleWidth = 1.0 / sourceWidth;
        double scaleHeight = 1.0 / sourceHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        buffer.begin(7, VertexFormats.POSITION_UV);
        buffer.vertex(x, y + height, 0).texture(u * scaleWidth, (v + textureHeight) * scaleHeight).next();
        buffer.vertex(x + width, y + height, 0).texture((u + textureWidth) * scaleWidth, (v + textureHeight) * scaleHeight).next();
        buffer.vertex(x + width, y, 0).texture((u + textureWidth) * scaleWidth, v * scaleHeight).next();
        buffer.vertex(x, y, 0).texture(u * scaleWidth, v * scaleHeight).next();
        tessellator.draw();
    }

    public static void drawApplicationIcon(AppInfo info, double x, double y) {
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(BaseDevice.ICON_TEXTURES);
        if (info != null) {
            drawRectWithTexture(x, y, info.getIconU(), info.getIconV(), 14, 14, 14, 14, 224, 224);
        } else {
            drawRectWithTexture(x, y, 0, 0, 14, 14, 14, 14, 224, 224);
        }
    }

    public static void drawStringClipped(String text, int x, int y, int width, int color, boolean shadow) {
        TextRenderer fontRenderer = MinecraftClient.getInstance().textRenderer;
        if (shadow)
            fontRenderer.drawWithShadow(clipStringToWidth(text, width), x, y, color);
        else
            fontRenderer.draw(clipStringToWidth(text, width), x, y, color);
    }

    public static String clipStringToWidth(String text, int width) {
        TextRenderer fontRenderer = MinecraftClient.getInstance().textRenderer;
        String clipped = text;
        if (fontRenderer.getStringWidth(clipped) > width) {
            clipped = fontRenderer.trimToWidth(clipped, width - 8) + "...";
        }
        return clipped;
    }

    public static boolean isMouseInside(double mouseX, double mouseY, int x1, int y1, int x2, int y2) {
        return mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2;
    }
}
