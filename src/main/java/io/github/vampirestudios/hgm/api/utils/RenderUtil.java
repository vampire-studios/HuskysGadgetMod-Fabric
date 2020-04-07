package io.github.vampirestudios.hgm.api.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.api.AppInfo;
import io.github.vampirestudios.hgm.core.BaseDevice;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class RenderUtil {
    public static void renderItem(int x, int y, ItemStack stack, boolean overlay) {
        RenderSystem.disableDepthTest();
        RenderSystem.enableLighting();
        DiffuseLighting.enableGuiDepthLighting();
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
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, VertexFormats.POSITION_TEXTURE);
        buffer.vertex(x, y + height, z).texture(u * scale,  (v + textureHeight) * scale).next();
        buffer.vertex(x + width, y + height, z).texture( (u + textureWidth) * scale,  (v + textureHeight) * scale).next();
        buffer.vertex(x + width, y, z).texture( (u + textureWidth) * scale, v * scale).next();
        buffer.vertex(x, y, z).texture(u * scale, v * scale).next();
        tessellator.draw();
    }

    public static void drawRectWithFullTexture(double x, double y, float u, float v, int width, int height) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, VertexFormats.POSITION_TEXTURE);
        buffer.vertex(x, y + height, 0).texture(0, 1).next();
        buffer.vertex(x + width, y + height, 0).texture(1, 1).next();
        buffer.vertex(x + width, y, 0).texture(1, 0).next();
        buffer.vertex(x, y, 0).texture(0, 0).next();
        tessellator.draw();
    }

    public static void drawRectWithTexture(double x, double y, float u, float v, int width, int height, float textureWidth, float textureHeight, int sourceWidth, int sourceHeight) {
        float scaleWidth = 1.0F / sourceWidth;
        float scaleHeight = 1.0F / sourceHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, VertexFormats.POSITION_TEXTURE);
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

    private static MinecraftClient mc()
    {
        return MinecraftClient.getInstance();
    }

    public static void drawHoverText(int x, int y, List<String> textLines)
    {
        MinecraftClient mc = mc();

        if (textLines.isEmpty() == false && ScreenUtils.getCurrentScreen() != null)
        {
            TextRenderer font = mc.textRenderer;
            RenderSystem.disableRescaleNormal();
            disableDiffuseLighting();
            RenderSystem.disableLighting();
            RenderSystem.disableDepthTest();
            int maxLineLength = 0;
            int maxWidth = ScreenUtils.getCurrentScreen().width;
            List<String> linesNew = new ArrayList<>();

            for (String lineOrig : textLines)
            {
                String[] lines = lineOrig.split("\\n");

                for (String line : lines)
                {
                    int length = font.getStringWidth(line);

                    if (length > maxLineLength)
                    {
                        maxLineLength = length;
                    }

                    linesNew.add(line);
                }
            }

            textLines = linesNew;

            final int lineHeight = font.fontHeight + 1;
            int textHeight = textLines.size() * lineHeight - 2;
            int textStartX = x + 4;
            int textStartY = Math.max(8, y - textHeight - 6);

            if (textStartX + maxLineLength + 6 > maxWidth)
            {
                textStartX = Math.max(2, maxWidth - maxLineLength - 8);
            }

            double zLevel = 300;
            int borderColor = 0xF0100010;
            drawGradientRect(textStartX - 3, textStartY - 4, textStartX + maxLineLength + 3, textStartY - 3, zLevel, borderColor, borderColor);
            drawGradientRect(textStartX - 3, textStartY + textHeight + 3, textStartX + maxLineLength + 3, textStartY + textHeight + 4, zLevel, borderColor, borderColor);
            drawGradientRect(textStartX - 3, textStartY - 3, textStartX + maxLineLength + 3, textStartY + textHeight + 3, zLevel, borderColor, borderColor);
            drawGradientRect(textStartX - 4, textStartY - 3, textStartX - 3, textStartY + textHeight + 3, zLevel, borderColor, borderColor);
            drawGradientRect(textStartX + maxLineLength + 3, textStartY - 3, textStartX + maxLineLength + 4, textStartY + textHeight + 3, zLevel, borderColor, borderColor);

            int fillColor1 = 0x505000FF;
            int fillColor2 = 0x5028007F;
            drawGradientRect(textStartX - 3, textStartY - 3 + 1, textStartX - 3 + 1, textStartY + textHeight + 3 - 1, zLevel, fillColor1, fillColor2);
            drawGradientRect(textStartX + maxLineLength + 2, textStartY - 3 + 1, textStartX + maxLineLength + 3, textStartY + textHeight + 3 - 1, zLevel, fillColor1, fillColor2);
            drawGradientRect(textStartX - 3, textStartY - 3, textStartX + maxLineLength + 3, textStartY - 3 + 1, zLevel, fillColor1, fillColor1);
            drawGradientRect(textStartX - 3, textStartY + textHeight + 2, textStartX + maxLineLength + 3, textStartY + textHeight + 3, zLevel, fillColor2, fillColor2);

            for (String str : textLines) {
                font.drawWithShadow(str, textStartX, textStartY, 0xFFFFFFFF);
                textStartY += lineHeight;
            }

            RenderSystem.enableLighting();
            RenderSystem.enableDepthTest();
            enableDiffuseLightingGui3D();
            RenderSystem.enableRescaleNormal();
        }
    }

    public static void disableDiffuseLighting() {
        // FIXME 1.15-pre4+
        DiffuseLighting.disable();
    }

    public static void enableDiffuseLightingForLevel(MatrixStack matrixStack) {
        DiffuseLighting.enableForLevel(matrixStack.peek().getModel());
    }

    public static void enableDiffuseLightingGui3D() {
        // FIXME 1.15-pre4+
        DiffuseLighting.enableGuiDepthLighting();
    }

    public static void drawGradientRect(int left, int top, int right, int bottom, double zLevel, int startColor, int endColor) {
        int sa = (startColor >> 24 & 0xFF);
        int sr = (startColor >> 16 & 0xFF);
        int sg = (startColor >>  8 & 0xFF);
        int sb = (startColor & 0xFF);

        int ea = (endColor >> 24 & 0xFF);
        int er = (endColor >> 16 & 0xFF);
        int eg = (endColor >>  8 & 0xFF);
        int eb = (endColor & 0xFF);

        RenderSystem.disableTexture();
        RenderSystem.disableAlphaTest();
        setupBlend();
        RenderSystem.shadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);

        buffer.vertex(right, top,    zLevel).color(sr, sg, sb, sa).next();
        buffer.vertex(left,  top,    zLevel).color(sr, sg, sb, sa).next();
        buffer.vertex(left,  bottom, zLevel).color(er, eg, eb, ea).next();
        buffer.vertex(right, bottom, zLevel).color(er, eg, eb, ea).next();

        tessellator.draw();

        RenderSystem.shadeModel(GL11.GL_FLAT);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }

    public static void setupBlend() {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
    }

    public static void setupBlendSimple() {
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
    }

}
