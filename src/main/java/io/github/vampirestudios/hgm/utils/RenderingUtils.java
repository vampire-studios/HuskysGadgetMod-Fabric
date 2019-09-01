//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.hgm.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.utils.InventoryOverlay.InventoryProperties;
import io.github.vampirestudios.hgm.utils.InventoryOverlay.InventoryRenderType;
import io.github.vampirestudios.hgm.utils.PositionUtils.HitPart;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.class_4493;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;

import javax.annotation.Nullable;
import java.util.*;

public class RenderingUtils {
    public static final Identifier TEXTURE_MAP_BACKGROUND = new Identifier("textures/map/map_background.png");
    private static final Random RAND = new Random();

    public RenderingUtils() {
    }

    public static void setupBlend() {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(class_4493.class_4535.SRC_ALPHA, class_4493.class_4534.ONE_MINUS_SRC_ALPHA, class_4493.class_4535.ONE, class_4493.class_4534.ZERO);
    }

    public static void bindTexture(Identifier texture) {
        mc().getTextureManager().bindTexture(texture);
    }

    public static void color(float r, float g, float b, float a) {
        RenderSystem.color4f(r, g, b, a);
    }

    public static void disableItemLighting() {
        GuiLighting.disable();
    }

    public static void enableItemLighting() {
        GuiLighting.enable();
    }

    public static void enableGuiItemLighting() {
        GuiLighting.enableForItems();
    }

    public static void drawOutlinedBox(int x, int y, int width, int height, int colorBg, int colorBorder) {
        drawOutlinedBox(x, y, width, height, colorBg, colorBorder, 0.0F);
    }

    public static void drawOutlinedBox(int x, int y, int width, int height, int colorBg, int colorBorder, float zLevel) {
        drawRect(x, y, width, height, colorBg, zLevel);
        drawOutline(x - 1, y - 1, width + 2, height + 2, colorBorder, zLevel);
    }

    public static void drawOutline(int x, int y, int width, int height, int colorBorder) {
        drawOutline(x, y, width, height, colorBorder, 0.0F);
    }

    public static void drawOutline(int x, int y, int width, int height, int colorBorder, float zLevel) {
        drawRect(x, y, 1, height, colorBorder, zLevel);
        drawRect(x + width - 1, y, 1, height, colorBorder, zLevel);
        drawRect(x + 1, y, width - 2, 1, colorBorder, zLevel);
        drawRect(x + 1, y + height - 1, width - 2, 1, colorBorder, zLevel);
    }

    public static void drawOutline(int x, int y, int width, int height, int borderWidth, int colorBorder) {
        drawOutline(x, y, width, height, borderWidth, colorBorder, 0.0F);
    }

    public static void drawOutline(int x, int y, int width, int height, int borderWidth, int colorBorder, float zLevel) {
        drawRect(x, y, borderWidth, height, colorBorder, zLevel);
        drawRect(x + width - borderWidth, y, borderWidth, height, colorBorder, zLevel);
        drawRect(x + borderWidth, y, width - 2 * borderWidth, borderWidth, colorBorder, zLevel);
        drawRect(x + borderWidth, y + height - borderWidth, width - 2 * borderWidth, borderWidth, colorBorder, zLevel);
    }

    public static void drawTexturedRect(int x, int y, int u, int v, int width, int height) {
        drawTexturedRect(x, y, u, v, width, height, 0.0F);
    }

    public static void drawRect(int x, int y, int width, int height, int color) {
        drawRect(x, y, width, height, color, 0.0F);
    }

    public static void drawRect(int x, int y, int width, int height, int color, float zLevel) {
        float a = (float)(color >> 24 & 255) / 255.0F;
        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        RenderSystem.disableTexture();
        setupBlend();
        color(r, g, b, a);
        buffer.begin(7, VertexFormats.POSITION);
        buffer.vertex(x, y, zLevel).next();
        buffer.vertex(x, y + height, zLevel).next();
        buffer.vertex(x + width, y + height, zLevel).next();
        buffer.vertex(x + width, y, zLevel).next();
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawTexturedRect(int x, int y, int u, int v, int width, int height, float zLevel) {
        float pixelWidth = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        buffer.begin(7, VertexFormats.POSITION_UV);
        buffer.vertex(x, y + height, zLevel).texture((float)u * pixelWidth, (float)(v + height) * pixelWidth).next();
        buffer.vertex(x + width, y + height, zLevel).texture((float)(u + width) * pixelWidth, (float)(v + height) * pixelWidth).next();
        buffer.vertex(x + width, y, zLevel).texture((float)(u + width) * pixelWidth, (float)v * pixelWidth).next();
        buffer.vertex(x, y, zLevel).texture((float)u * pixelWidth, (float)v * pixelWidth).next();
        tessellator.draw();
    }

    public static void drawTexturedRectBatched(int x, int y, int u, int v, int width, int height, BufferBuilder buffer) {
        drawTexturedRectBatched(x, y, u, v, width, height, 0.0F, buffer);
    }

    public static void drawTexturedRectBatched(int x, int y, int u, int v, int width, int height, float zLevel, BufferBuilder buffer) {
        float pixelWidth = 0.00390625F;
        buffer.vertex(x, y + height, zLevel).texture((float)u * pixelWidth, (float)(v + height) * pixelWidth).next();
        buffer.vertex(x + width, y + height, zLevel).texture((float)(u + width) * pixelWidth, (float)(v + height) * pixelWidth).next();
        buffer.vertex(x + width, y, zLevel).texture((float)(u + width) * pixelWidth, (float)v * pixelWidth).next();
        buffer.vertex(x, y, zLevel).texture((float)u * pixelWidth, (float)v * pixelWidth).next();
    }

    public static void drawHoverText(int x, int y, List<String> textLines) {
        MinecraftClient mc = mc();
        if (!textLines.isEmpty() && MinecraftClient.getInstance().currentScreen != null) {
            TextRenderer font = mc.textRenderer;
            RenderSystem.disableRescaleNormal();
            disableItemLighting();
            RenderSystem.disableLighting();
            RenderSystem.disableDepthTest();
            int maxLineLength = 0;
            int maxWidth = MinecraftClient.getInstance().currentScreen.width;
            List<String> linesNew = new ArrayList<>();
            Iterator var8 = textLines.iterator();

            int length;
            while(var8.hasNext()) {
                String lineOrig = (String)var8.next();
                String[] lines = lineOrig.split("\\n");
                String[] var11 = lines;
                int var12 = lines.length;

                for(int var13 = 0; var13 < var12; ++var13) {
                    String line = var11[var13];
                    length = font.getStringWidth(line);
                    if (length > maxLineLength) {
                        maxLineLength = length;
                    }

                    linesNew.add(line);
                }
            }

            font.getClass();
            int lineHeight = 9 + 1;
            int textHeight = linesNew.size() * lineHeight - 2;
            int textStartX = x + 4;
            int textStartY = Math.max(8, y - textHeight - 6);
            if (textStartX + maxLineLength + 6 > maxWidth) {
                textStartX = Math.max(2, maxWidth - maxLineLength - 8);
            }

            double zLevel = 300.0D;
            int borderColor = -267386864;
            drawGradientRect(textStartX - 3, textStartY - 4, textStartX + maxLineLength + 3, textStartY - 3, zLevel, borderColor, borderColor);
            drawGradientRect(textStartX - 3, textStartY + textHeight + 3, textStartX + maxLineLength + 3, textStartY + textHeight + 4, zLevel, borderColor, borderColor);
            drawGradientRect(textStartX - 3, textStartY - 3, textStartX + maxLineLength + 3, textStartY + textHeight + 3, zLevel, borderColor, borderColor);
            drawGradientRect(textStartX - 4, textStartY - 3, textStartX - 3, textStartY + textHeight + 3, zLevel, borderColor, borderColor);
            drawGradientRect(textStartX + maxLineLength + 3, textStartY - 3, textStartX + maxLineLength + 4, textStartY + textHeight + 3, zLevel, borderColor, borderColor);
            length = 1347420415;
            int fillColor2 = 1344798847;
            drawGradientRect(textStartX - 3, textStartY - 3 + 1, textStartX - 3 + 1, textStartY + textHeight + 3 - 1, zLevel, length, fillColor2);
            drawGradientRect(textStartX + maxLineLength + 2, textStartY - 3 + 1, textStartX + maxLineLength + 3, textStartY + textHeight + 3 - 1, zLevel, length, fillColor2);
            drawGradientRect(textStartX - 3, textStartY - 3, textStartX + maxLineLength + 3, textStartY - 3 + 1, zLevel, length, length);
            drawGradientRect(textStartX - 3, textStartY + textHeight + 2, textStartX + maxLineLength + 3, textStartY + textHeight + 3, zLevel, fillColor2, fillColor2);

            for (String str : linesNew) {
                font.drawWithShadow(str, (float) textStartX, (float) textStartY, -1);
                textStartY += lineHeight;
            }

            RenderSystem.enableLighting();
            RenderSystem.enableDepthTest();
            GuiLighting.enableForItems();
            RenderSystem.enableRescaleNormal();
        }

    }

    public static void drawGradientRect(int left, int top, int right, int bottom, double zLevel, int startColor, int endColor) {
        float sa = (float)(startColor >> 24 & 255) / 255.0F;
        float sr = (float)(startColor >> 16 & 255) / 255.0F;
        float sg = (float)(startColor >> 8 & 255) / 255.0F;
        float sb = (float)(startColor & 255) / 255.0F;
        float ea = (float)(endColor >> 24 & 255) / 255.0F;
        float er = (float)(endColor >> 16 & 255) / 255.0F;
        float eg = (float)(endColor >> 8 & 255) / 255.0F;
        float eb = (float)(endColor & 255) / 255.0F;
        RenderSystem.disableTexture();
        RenderSystem.disableAlphaTest();
        setupBlend();
        RenderSystem.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        buffer.begin(7, VertexFormats.POSITION_COLOR);
        buffer.vertex(right, top, zLevel).color(sr, sg, sb, sa).next();
        buffer.vertex(left, top, zLevel).color(sr, sg, sb, sa).next();
        buffer.vertex(left, bottom, zLevel).color(er, eg, eb, ea).next();
        buffer.vertex(right, bottom, zLevel).color(er, eg, eb, ea).next();
        tessellator.draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
    }

    public static void drawCenteredString(int x, int y, int color, String text) {
        TextRenderer textRenderer = mc().textRenderer;
        textRenderer.drawWithShadow(text, (float)(x - textRenderer.getStringWidth(text) / 2), (float)y, color);
    }

    public static void drawHorizontalLine(int x, int y, int width, int color) {
        drawRect(x, y, width, 1, color);
    }

    public static void drawVerticalLine(int x, int y, int height, int color) {
        drawRect(x, y, 1, height, color);
    }

    public static void renderSprite(int x, int y, int width, int height, String texture) {
        if (texture != null) {
            Sprite sprite = mc().getSpriteAtlas().getSprite(texture);
            RenderSystem.disableLighting();
            DrawableHelper.blit(x, y, 0, width, height, sprite);
        }

    }

    public static void renderText(int x, int y, int color, String text) {
        String[] parts = text.split("\\\\n");
        TextRenderer textRenderer = mc().textRenderer;
        String[] var6 = parts;
        int var7 = parts.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            String line = var6[var8];
            textRenderer.drawWithShadow(line, (float)x, (float)y, color);
            textRenderer.getClass();
            y += 9 + 1;
        }

    }

    public static void renderText(int x, int y, int color, List<String> lines) {
        if (!lines.isEmpty()) {
            TextRenderer textRenderer = mc().textRenderer;

            for(Iterator var5 = lines.iterator(); var5.hasNext(); y += 9 + 2) {
                String line = (String)var5.next();
                textRenderer.draw(line, (float)x, (float)y, color);
                textRenderer.getClass();
            }
        }

    }

    public static int renderText(int xOff, int yOff, double scale, int textColor, int bgColor, GuiAlignment alignment, boolean useBackground, boolean useShadow, List<String> lines) {
        TextRenderer fontRenderer = mc().textRenderer;
        int scaledWidth = MinecraftClient.getInstance().window.getScaledWidth();
        fontRenderer.getClass();
        int lineHeight = 9 + 2;
        int contentHeight = lines.size() * lineHeight - 2;
        if (scale == 0.0D) {
            return 0;
        } else {
            if (scale != 1.0D) {
                if (scale != 0.0D) {
                    xOff = (int)((double)xOff * scale);
                    yOff = (int)((double)yOff * scale);
                }

                RenderSystem.pushMatrix();
                RenderSystem.scaled(scale, scale, 0.0D);
            }

            double posX = xOff + 2;
            double posY = yOff + 2;
            posY = getHudPosY((int)posY, yOff, contentHeight, scale, alignment);
            posY += getHudOffsetForPotions(alignment, scale, mc().player);
            Iterator var19 = lines.iterator();

            while(var19.hasNext()) {
                String line = (String)var19.next();
                int width = fontRenderer.getStringWidth(line);
                switch(alignment) {
                case TOP_RIGHT:
                case BOTTOM_RIGHT:
                    posX = (double)scaledWidth / scale - (double)width - (double)xOff - 2.0D;
                    break;
                case CENTER:
                    posX = (double)scaledWidth / scale / 2.0D - (double)(width / 2) - (double)xOff;
                }

                int x = (int)posX;
                int y = (int)posY;
                posY += lineHeight;
                if (useBackground) {
                    int var10000 = x - 2;
                    int var10001 = y - 2;
                    int var10002 = width + 2;
                    fontRenderer.getClass();
                    drawRect(var10000, var10001, var10002, 2 + 9, bgColor);
                }

                if (useShadow) {
                    fontRenderer.drawWithShadow(line, (float)x, (float)y, textColor);
                } else {
                    fontRenderer.draw(line, (float)x, (float)y, textColor);
                }
            }

            if (scale != 1.0D) {
                RenderSystem.popMatrix();
            }

            return contentHeight + 4;
        }
    }

    public static int getHudOffsetForPotions(GuiAlignment alignment, double scale, PlayerEntity player) {
        if (alignment == GuiAlignment.TOP_RIGHT) {
            if (scale == 0.0D) {
                return 0;
            }

            Collection<StatusEffectInstance> effects = player.getStatusEffects();
            if (!effects.isEmpty()) {
                int y1 = 0;
                int y2 = 0;
                Iterator var7 = effects.iterator();

                while(var7.hasNext()) {
                    StatusEffectInstance effectInstance = (StatusEffectInstance)var7.next();
                    StatusEffect effect = effectInstance.getEffectType();
                    if (effectInstance.shouldShowParticles() && effectInstance.shouldShowIcon()) {
                        if (!effect.method_5573()) {
                            y2 = 52;
                            break;
                        }

                        y1 = 26;
                    }
                }

                return (int)((double)Math.max(y1, y2) / scale);
            }
        }

        return 0;
    }

    public static int getHudPosY(int yOrig, int yOffset, int contentHeight, double scale, GuiAlignment alignment) {
        int scaledHeight = MinecraftClient.getInstance().window.getScaledWidth();
        int posY = yOrig;
        switch(alignment) {
        case BOTTOM_RIGHT:
        case BOTTOM_LEFT:
            posY = (int)((double)scaledHeight / scale - (double)contentHeight - (double)yOffset);
            break;
        case CENTER:
            posY = (int)((double)scaledHeight / scale / 2.0D - (double)contentHeight / 2.0D + (double)yOffset);
        }

        return posY;
    }

    public static void drawBlockBoundingBoxSidesBatchedQuads(BlockPos pos, Color4f color, double expand, BufferBuilder buffer) {
        double minX = (double)pos.getX() - expand;
        double minY = (double)pos.getY() - expand;
        double minZ = (double)pos.getZ() - expand;
        double maxX = (double)pos.getX() + expand + 1.0D;
        double maxY = (double)pos.getY() + expand + 1.0D;
        double maxZ = (double)pos.getZ() + expand + 1.0D;
        drawBoxAllSidesBatchedQuads(minX, minY, minZ, maxX, maxY, maxZ, color, buffer);
    }

    public static void drawBlockBoundingBoxOutlinesBatchedLines(BlockPos pos, Color4f color, double expand, BufferBuilder buffer) {
        double minX = (double)pos.getX() - expand;
        double minY = (double)pos.getY() - expand;
        double minZ = (double)pos.getZ() - expand;
        double maxX = (double)pos.getX() + expand + 1.0D;
        double maxY = (double)pos.getY() + expand + 1.0D;
        double maxZ = (double)pos.getZ() + expand + 1.0D;
        drawBoxAllEdgesBatchedLines(minX, minY, minZ, maxX, maxY, maxZ, color, buffer);
    }

    public static void drawBoxAllSidesBatchedQuads(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color4f color, BufferBuilder buffer) {
        drawBoxHorizontalSidesBatchedQuads(minX, minY, minZ, maxX, maxY, maxZ, color, buffer);
        drawBoxTopBatchedQuads(minX, minZ, maxX, maxY, maxZ, color, buffer);
        drawBoxBottomBatchedQuads(minX, minY, minZ, maxX, maxZ, color, buffer);
    }

    public static void drawBoxWithEdgesBatched(BlockPos posMin, BlockPos posMax, Color4f colorLines, Color4f colorSides, BufferBuilder bufferQuads, BufferBuilder bufferLines) {
        double x1 = posMin.getX();
        double y1 = posMin.getY();
        double z1 = posMin.getZ();
        double x2 = posMax.getX() + 1;
        double y2 = posMax.getY() + 1;
        double z2 = posMax.getZ() + 1;
        drawBoxAllSidesBatchedQuads(x1, y1, z1, x2, y2, z2, colorSides, bufferQuads);
        drawBoxAllEdgesBatchedLines(x1, y1, z1, x2, y2, z2, colorLines, bufferLines);
    }

    public static void drawBoxHorizontalSidesBatchedQuads(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color4f color, BufferBuilder buffer) {
        buffer.vertex(minX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
    }

    public static void drawBoxTopBatchedQuads(double minX, double minZ, double maxX, double maxY, double maxZ, Color4f color, BufferBuilder buffer) {
        buffer.vertex(minX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
    }

    public static void drawBoxBottomBatchedQuads(double minX, double minY, double minZ, double maxX, double maxZ, Color4f color, BufferBuilder buffer) {
        buffer.vertex(maxX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
    }

    public static void drawBoxAllEdgesBatchedLines(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color4f color, BufferBuilder buffer) {
        buffer.vertex(minX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, minY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, maxY, minZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, minY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(maxX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
        buffer.vertex(minX, maxY, maxZ).color(color.r, color.g, color.b, color.a).next();
    }

    public static void drawBox(IntBoundingBox bb, Color4f color, BufferBuilder bufferQuads, BufferBuilder bufferLines) {
        double minX = bb.minX;
        double minY = bb.minY;
        double minZ = bb.minZ;
        double maxX = bb.maxX + 1;
        double maxY = bb.maxY + 1;
        double maxZ = bb.maxZ + 1;
        drawBoxAllSidesBatchedQuads(minX, minY, minZ, maxX, maxY, maxZ, color, bufferQuads);
        drawBoxAllEdgesBatchedLines(minX, minY, minZ, maxX, maxY, maxZ, color, bufferLines);
    }

    public static void drawBox(MutableIntBoundingBox bb, Color4f color, BufferBuilder bufferQuads, BufferBuilder bufferLines) {
        double minX = bb.minX;
        double minY = bb.minY;
        double minZ = bb.minZ;
        double maxX = bb.maxX + 1;
        double maxY = bb.maxY + 1;
        double maxZ = bb.maxZ + 1;
        drawBoxAllSidesBatchedQuads(minX, minY, minZ, maxX, maxY, maxZ, color, bufferQuads);
        drawBoxAllEdgesBatchedLines(minX, minY, minZ, maxX, maxY, maxZ, color, bufferLines);
    }

    public static void drawTextPlate(List<String> text, double x, double y, double z, float scale) {
        Entity entity = mc().getCameraEntity();
        if (entity != null) {
            drawTextPlate(text, x, y, z, entity.yaw, entity.pitch, scale, -1, 1073741824, true);
        }

    }

    public static void drawTextPlate(List<String> text, double x, double y, double z, float yaw, float pitch, float scale, int textColor, int bgColor, boolean disableDepth) {
        TextRenderer textRenderer = mc().textRenderer;
        RenderSystem.alphaFunc(516, 0.1F);
        RenderSystem.pushMatrix();
        RenderSystem.translated(x, y, z);
        RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
        RenderSystem.rotatef(-yaw, 0.0F, 1.0F, 0.0F);
        RenderSystem.rotatef(pitch, 1.0F, 0.0F, 0.0F);
        RenderSystem.scalef(-scale, -scale, scale);
        RenderSystem.disableLighting();
        RenderSystem.disableCull();
        if (disableDepth) {
            RenderSystem.depthMask(false);
            RenderSystem.disableDepthTest();
        }

        setupBlend();
        RenderSystem.disableTexture();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        int maxLineLen = 0;

        String line;
        for(Iterator var17 = text.iterator(); var17.hasNext(); maxLineLen = Math.max(maxLineLen, textRenderer.getStringWidth(line))) {
            line = (String)var17.next();
        }

        int strLenHalf = maxLineLen / 2;
        textRenderer.getClass();
        int textHeight = 9 * text.size() - 1;
        float bga = (float)(bgColor >>> 24 & 255) * 255.0F;
        float bgr = (float)(bgColor >>> 16 & 255) * 255.0F;
        float bgg = (float)(bgColor >>> 8 & 255) * 255.0F;
        float bgb = (float)(bgColor & 255) * 255.0F;
        buffer.begin(7, VertexFormats.POSITION_COLOR);
        buffer.vertex(-strLenHalf - 1, -1.0D, 0.0D).color(bgr, bgg, bgb, bga).next();
        buffer.vertex(-strLenHalf - 1, textHeight, 0.0D).color(bgr, bgg, bgb, bga).next();
        buffer.vertex(strLenHalf, textHeight, 0.0D).color(bgr, bgg, bgb, bga).next();
        buffer.vertex(strLenHalf, -1.0D, 0.0D).color(bgr, bgg, bgb, bga).next();
        tessellator.draw();
        RenderSystem.enableTexture();
        int textY = 0;
        if (!disableDepth) {
            RenderSystem.enablePolygonOffset();
            RenderSystem.polygonOffset(-0.6F, -1.2F);
        }

        for(Iterator var24 = text.iterator(); var24.hasNext(); textY += 9) {
            String line2 = (String)var24.next();
            if (disableDepth) {
                RenderSystem.depthMask(false);
                RenderSystem.disableDepthTest();
            }

            textRenderer.draw(line2, (float)(-strLenHalf), (float)textY, 536870912 | textColor & 16777215);
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            textRenderer.draw(line2, (float)(-strLenHalf), (float)textY, textColor);
            textRenderer.getClass();
        }

        if (!disableDepth) {
            RenderSystem.polygonOffset(0.0F, 0.0F);
            RenderSystem.disablePolygonOffset();
        }

        color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.popMatrix();
    }

    public static void renderBlockTargetingOverlay(Entity entity, BlockPos pos, Direction side, Vec3d hitVec, Color4f color, MinecraftClient mc) {
        Direction playerFacing = entity.getHorizontalFacing();
        HitPart part = PositionUtils.getHitPart(side, playerFacing, pos, hitVec);
        Vec3d cameraPos = mc.gameRenderer.getCamera().getPos();
        double x = (double)pos.getX() + 0.5D - cameraPos.x;
        double y = (double)pos.getY() + 0.5D - cameraPos.y;
        double z = (double)pos.getZ() + 0.5D - cameraPos.z;
        RenderSystem.pushMatrix();
        blockTargetingOverlayTranslations(x, y, z, side, playerFacing);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        float quadAlpha = 0.18F;
        float ha = color.a;
        float hr = color.r;
        float hg = color.g;
        float hb = color.b;
        buffer.begin(7, VertexFormats.POSITION_COLOR);
        buffer.vertex(x - 0.5D, y - 0.5D, z).color(1.0F, 1.0F, 1.0F, quadAlpha).next();
        buffer.vertex(x + 0.5D, y - 0.5D, z).color(1.0F, 1.0F, 1.0F, quadAlpha).next();
        buffer.vertex(x + 0.5D, y + 0.5D, z).color(1.0F, 1.0F, 1.0F, quadAlpha).next();
        buffer.vertex(x - 0.5D, y + 0.5D, z).color(1.0F, 1.0F, 1.0F, quadAlpha).next();
        switch(part) {
        case CENTER:
            buffer.vertex(x - 0.25D, y - 0.25D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x + 0.25D, y - 0.25D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x + 0.25D, y + 0.25D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x - 0.25D, y + 0.25D, z).color(hr, hg, hb, ha).next();
            break;
        case LEFT:
            buffer.vertex(x - 0.5D, y - 0.5D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x - 0.25D, y - 0.25D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x - 0.25D, y + 0.25D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x - 0.5D, y + 0.5D, z).color(hr, hg, hb, ha).next();
            break;
        case RIGHT:
            buffer.vertex(x + 0.5D, y - 0.5D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x + 0.25D, y - 0.25D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x + 0.25D, y + 0.25D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x + 0.5D, y + 0.5D, z).color(hr, hg, hb, ha).next();
            break;
        case TOP:
            buffer.vertex(x - 0.5D, y + 0.5D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x - 0.25D, y + 0.25D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x + 0.25D, y + 0.25D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x + 0.5D, y + 0.5D, z).color(hr, hg, hb, ha).next();
            break;
        case BOTTOM:
            buffer.vertex(x - 0.5D, y - 0.5D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x - 0.25D, y - 0.25D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x + 0.25D, y - 0.25D, z).color(hr, hg, hb, ha).next();
            buffer.vertex(x + 0.5D, y - 0.5D, z).color(hr, hg, hb, ha).next();
        }

        tessellator.draw();
        RenderSystem.lineWidth(1.6F);
        buffer.begin(2, VertexFormats.POSITION_COLOR);
        buffer.vertex(x - 0.25D, y - 0.25D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        buffer.vertex(x + 0.25D, y - 0.25D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        buffer.vertex(x + 0.25D, y + 0.25D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        buffer.vertex(x - 0.25D, y + 0.25D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        tessellator.draw();
        buffer.begin(1, VertexFormats.POSITION_COLOR);
        buffer.vertex(x - 0.5D, y - 0.5D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        buffer.vertex(x - 0.25D, y - 0.25D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        buffer.vertex(x - 0.5D, y + 0.5D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        buffer.vertex(x - 0.25D, y + 0.25D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        buffer.vertex(x + 0.5D, y - 0.5D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        buffer.vertex(x + 0.25D, y - 0.25D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        buffer.vertex(x + 0.5D, y + 0.5D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        buffer.vertex(x + 0.25D, y + 0.25D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        tessellator.draw();
        RenderSystem.popMatrix();
    }

    public static void renderBlockTargetingOverlaySimple(Entity entity, BlockPos pos, Direction side, Color4f color, MinecraftClient mc) {
        Direction playerFacing = entity.getHorizontalFacing();
        Vec3d cameraPos = mc.gameRenderer.getCamera().getPos();
        double x = (double)pos.getX() + 0.5D - cameraPos.x;
        double y = (double)pos.getY() + 0.5D - cameraPos.y;
        double z = (double)pos.getZ() + 0.5D - cameraPos.z;
        RenderSystem.pushMatrix();
        blockTargetingOverlayTranslations(x, y, z, side, playerFacing);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        float a = color.a;
        float r = color.r;
        float g = color.g;
        float b = color.b;
        buffer.begin(7, VertexFormats.POSITION_COLOR);
        buffer.vertex(x - 0.5D, y - 0.5D, z).color(r, g, b, a).next();
        buffer.vertex(x + 0.5D, y - 0.5D, z).color(r, g, b, a).next();
        buffer.vertex(x + 0.5D, y + 0.5D, z).color(r, g, b, a).next();
        buffer.vertex(x - 0.5D, y + 0.5D, z).color(r, g, b, a).next();
        tessellator.draw();
        RenderSystem.lineWidth(1.6F);
        buffer.begin(2, VertexFormats.POSITION_COLOR);
        buffer.vertex(x - 0.375D, y - 0.375D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        buffer.vertex(x + 0.375D, y - 0.375D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        buffer.vertex(x + 0.375D, y + 0.375D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        buffer.vertex(x - 0.375D, y + 0.375D, z).color(1.0F, 1.0F, 1.0F, 1.0F).next();
        tessellator.draw();
        RenderSystem.popMatrix();
    }

    private static void blockTargetingOverlayTranslations(double x, double y, double z, Direction side, Direction playerFacing) {
        RenderSystem.translated(x, y, z);
        switch(side) {
        case DOWN:
            RenderSystem.rotatef(180.0F - playerFacing.asRotation(), 0.0F, 1.0F, 0.0F);
            RenderSystem.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
            break;
        case UP:
            RenderSystem.rotatef(180.0F - playerFacing.asRotation(), 0.0F, 1.0F, 0.0F);
            RenderSystem.rotatef(-90.0F, 1.0F, 0.0F, 0.0F);
            break;
        case NORTH:
            RenderSystem.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
            break;
        case SOUTH:
            RenderSystem.rotatef(0.0F, 0.0F, 1.0F, 0.0F);
            break;
        case WEST:
            RenderSystem.rotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            break;
        case EAST:
            RenderSystem.rotatef(90.0F, 0.0F, 1.0F, 0.0F);
        }

        RenderSystem.translated(-x, -y, -z + 0.501D);
    }

    public static void renderMapPreview(ItemStack stack, int x, int y, int dimensions) {
        if (stack.getItem() instanceof FilledMapItem && Screen.hasShiftDown()) {
            RenderSystem.pushMatrix();
            RenderSystem.disableLighting();
            color(1.0F, 1.0F, 1.0F, 1.0F);
            int y1 = y - dimensions - 20;
            int y2 = y1 + dimensions;
            int x1 = x + 8;
            int x2 = x1 + dimensions;
            int z = 300;
            bindTexture(TEXTURE_MAP_BACKGROUND);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBufferBuilder();
            buffer.begin(7, VertexFormats.POSITION_UV);
            buffer.vertex(x1, y2, z).texture(0.0D, 1.0D).next();
            buffer.vertex(x2, y2, z).texture(1.0D, 1.0D).next();
            buffer.vertex(x2, y1, z).texture(1.0D, 0.0D).next();
            buffer.vertex(x1, y1, z).texture(0.0D, 0.0D).next();
            tessellator.draw();
            MapState mapdata = FilledMapItem.getMapState(stack, mc().world);
            if (mapdata != null) {
                x1 += 8;
                y1 += 8;
                z = 310;
                double scale = (double)(dimensions - 16) / 128.0D;
                RenderSystem.translatef((float)x1, (float)y1, (float)z);
                RenderSystem.scaled(scale, scale, 0.0D);
                mc().gameRenderer.getMapRenderer().draw(mapdata, false);
            }

            RenderSystem.enableLighting();
            RenderSystem.popMatrix();
        }

    }

    public static void renderShulkerBoxPreview(ItemStack stack, int x, int y, boolean useBgColors) {
        if (stack.hasTag()) {
            DefaultedList<ItemStack> items = InventoryUtils.getStoredItems(stack, -1);
            if (items.size() == 0) {
                return;
            }

            RenderSystem.pushMatrix();
            disableItemLighting();
            RenderSystem.translatef(0.0F, 0.0F, 700.0F);
            InventoryRenderType type = InventoryOverlay.getInventoryType(stack);
            InventoryProperties props = InventoryOverlay.getInventoryPropsTemp(type, items.size());
            x += 8;
            y -= props.height + 18;
            if (stack.getItem() instanceof BlockItem && ((BlockItem)stack.getItem()).getBlock() instanceof ShulkerBoxBlock) {
                setShulkerboxBackgroundTintColor((ShulkerBoxBlock)((BlockItem)stack.getItem()).getBlock(), useBgColors);
            } else {
                color(1.0F, 1.0F, 1.0F, 1.0F);
            }

            InventoryOverlay.renderInventoryBackground(type, x, y, props.slotsPerRow, items.size(), mc());
            enableGuiItemLighting();
            RenderSystem.enableDepthTest();
            RenderSystem.enableRescaleNormal();
            Inventory inv = InventoryUtils.getAsInventory(items);
            InventoryOverlay.renderInventoryStacks(type, inv, x + props.slotOffsetX, y + props.slotOffsetY, props.slotsPerRow, 0, -1, mc());
            RenderSystem.disableDepthTest();
            RenderSystem.popMatrix();
        }

    }

    public static void setShulkerboxBackgroundTintColor(@Nullable ShulkerBoxBlock block, boolean useBgColors) {
        if (block != null && useBgColors) {
            DyeColor dye = block.getColor() != null ? block.getColor() : DyeColor.PURPLE;
            float[] colors = dye.getColorComponents();
            color(colors[0], colors[1], colors[2], 1.0F);
        } else {
            color(1.0F, 1.0F, 1.0F, 1.0F);
        }

    }

    public static void renderModelInGui(int x, int y, BakedModel model, BlockState state, float zLevel) {
        if (state.getBlock() != Blocks.AIR) {
            RenderSystem.pushMatrix();
            bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
            mc().getTextureManager().getTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX).pushFilter(false, false);
            RenderSystem.enableRescaleNormal();
            RenderSystem.enableAlphaTest();
            RenderSystem.alphaFunc(516, 0.1F);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(class_4493.class_4535.SRC_ALPHA, class_4493.class_4534.ONE_MINUS_SRC_ALPHA);
            color(1.0F, 1.0F, 1.0F, 1.0F);
            setupGuiTransform(x, y, model.hasDepthInGui(), zLevel);
            RenderSystem.rotatef(30.0F, 1.0F, 0.0F, 0.0F);
            RenderSystem.rotatef(225.0F, 0.0F, 1.0F, 0.0F);
            RenderSystem.scalef(0.625F, 0.625F, 0.625F);
            renderModel(model, state);
            mc().getTextureManager().getTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX).popFilter();
            RenderSystem.disableAlphaTest();
            RenderSystem.disableRescaleNormal();
            RenderSystem.disableLighting();
            RenderSystem.popMatrix();
        }
    }

    public static void setupGuiTransform(int xPosition, int yPosition, boolean isGui3d, float zLevel) {
        RenderSystem.translatef((float)xPosition, (float)yPosition, 100.0F + zLevel);
        RenderSystem.translatef(8.0F, 8.0F, 0.0F);
        RenderSystem.scalef(1.0F, -1.0F, 1.0F);
        RenderSystem.scalef(16.0F, 16.0F, 16.0F);
        if (isGui3d) {
            RenderSystem.enableLighting();
        } else {
            RenderSystem.disableLighting();
        }

    }

    private static void renderModel(BakedModel model, BlockState state) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(-0.5F, -0.5F, -0.5F);
        int color = -1;
        if (!model.isBuiltin()) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBufferBuilder();
            bufferbuilder.begin(7, VertexFormats.POSITION_COLOR_UV_NORMAL);
            Direction[] var5 = Direction.values();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Direction face = var5[var7];
                RAND.setSeed(0L);
                renderQuads(bufferbuilder, model.getQuads(state, face, RAND), state, color);
            }

            RAND.setSeed(0L);
            renderQuads(bufferbuilder, model.getQuads(state, null, RAND), state, color);
            tessellator.draw();
        }

        RenderSystem.popMatrix();
    }

    private static void renderQuads(BufferBuilder renderer, List<BakedQuad> quads, BlockState state, int color) {
        int quadCount = quads.size();

        for(int i = 0; i < quadCount; ++i) {
            BakedQuad quad = quads.get(i);
            renderQuad(renderer, quad, state, -1);
        }

    }

    private static void renderQuad(BufferBuilder buffer, BakedQuad quad, BlockState state, int color) {
        buffer.putVertexData(quad.getVertexData());
        buffer.setQuadColor(color);
        if (quad.hasColor()) {
            BlockColors blockColors = mc().getBlockColorMap();
            int m = blockColors.getColorMultiplier(state, null, null, quad.getColorIndex());
            float r = (float)(m >>> 16 & 255) / 255.0F;
            float g = (float)(m >>> 8 & 255) / 255.0F;
            float b = (float)(m & 255) / 255.0F;
            buffer.multiplyColor(r, g, b, 4);
            buffer.multiplyColor(r, g, b, 3);
            buffer.multiplyColor(r, g, b, 2);
            buffer.multiplyColor(r, g, b, 1);
        }

        putQuadNormal(buffer, quad);
    }

    private static void putQuadNormal(BufferBuilder renderer, BakedQuad quad) {
        Vec3i direction = quad.getFace().getVector();
        renderer.normal((float)direction.getX(), (float)direction.getY(), (float)direction.getZ());
    }

    private static MinecraftClient mc() {
        return MinecraftClient.getInstance();
    }
}
