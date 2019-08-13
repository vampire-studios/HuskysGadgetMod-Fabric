package io.github.vampirestudios.hgm.core;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class ScreenDrawing {

    public static void rect(Identifier texture, int left, int top, int width, int height, int color) {
        rect(texture, left, top, width, height, 0, 0, 1, 1, color, 0);
    }

    public static void rect(Identifier texture, int left, int top, int width, int height, float u1, float v1, float u2, float v2, int color) {
        rect(texture, left, top, width, height, u1, v1, u2, v2, color, 0);
    }

    public static void rect(Identifier texture, int left, int top, int width, int height, float u1, float v1, float u2, float v2, int color, int z) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(texture);

        //float scale = 0.00390625F;

        if (width <= 0) width = 1;
        if (height <= 0) height = 1;

        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        GlStateManager.enableBlend();
        //GlStateManager.disableTexture2D();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(r, g, b, 1.0f);
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_UV); //I thought GL_QUADS was deprecated but okay, sure.
        buffer.vertex(left,         top + height, z).texture(u1, v2).next();
        buffer.vertex(left + width, top + height, z).texture(u2, v2).next();
        buffer.vertex(left + width, top,          z).texture(u2, v1).next();
        buffer.vertex(left,         top,          z).texture(u1, v1).next();
        tessellator.draw();
        //GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    /**
     * Draws an untextured rectangle of the specified RGB color.
     */
    public static void rect(int left, int top, int width, int height, int color) {
        if (width <= 0) width = 1;
        if (height <= 0) height = 1;

        float a = (color >> 24 & 255) / 255.0F;
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(r, g, b, a);
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION); //I thought GL_QUADS was deprecated but okay, sure.
        buffer.vertex(left, top + height, 0.0D).next();
        buffer.vertex(left + width, top + height, 0.0D).next();
        buffer.vertex(left + width, top, 0.0D).next();
        buffer.vertex(left, top, 0.0D).next();
        tessellator.draw();
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }

    public static void colorFill(int x, int y, int width, int height, int color) {
        float r = (color >> 16) & 0xFF; r /= 255f;
        float g = (color >> 8) & 0xFF; g /= 255f;
        float b = (color) & 0xFF; b /= 255f;
        
        colorFill(x, y, width, height, 0.0f, r, g, b);
    }
    
    public static void colorFill(int x, int y, int width, int height, double z, float r, float g, float b) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBufferBuilder();
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(x, y+height, z).color(r, g, b, 1.0f).next();
        buffer.vertex(x+width, y+height, z).color(r, g, b, 1.0f).next();
        buffer.vertex(x+width, y, z).color(r, g, b, 1.0f).next();
        buffer.vertex(x, y, z).color(r, g, b, 1.0f).next();
        tess.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
    
    public static void colorHollowRect(int x, int y, int width, int height, int color) {
        colorFill(x, y, width, 1, color);
        colorFill(x, y+1, 1, height-2, color);
        colorFill(x+width-1, y+1, 1, height-2, color);
        colorFill(x, y+height-1, width, 1, color);
    }
    
    
    public static void textureFill(int x, int y, int width, int height, Identifier tex, float u1, float v1, float u2, float v2) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(tex);
        
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBufferBuilder();
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_UV);
        buffer.vertex(x, y+height, 0.0f).texture(u1, v2).next();
        buffer.vertex(x+width, y+height, 0.0f).texture(u2, v2).next();
        buffer.vertex(x+width, y, 0.0f).texture(u2, v1).next();
        buffer.vertex(x, y, 0.0f).texture(u1, v1).next();
        tess.draw();
    }
    
    
    /**
     * Draws a rectangle on the screen, using as much of the top-left part of the texture that's visible in the width / height.
     * 
     *  <p>Assumes the texture is 256px
     */
    public static void textureFillGui(int x, int y, int width, int height, Identifier tex) {
        textureFillGui(x, y, width, height, tex, 0, 0);
    }
    
    /**
     * Draws a rectangle out of a texture, at one texture-pixel per minecraft gui pixel.
     * 
     * <p>Assumes the texture is 256px
     * 
     * @param x left edge of the rectangle
     * @param y top edge of the rectangle
     * @param width width of the rectangle on the screen
     * @param height height of the rectangle on the screen
     * @param tex the texture identifier to use
     * @param tex_x the x-offset into the texture
     * @param tex_y the y-offset into the texture
     */
    public static void textureFillGui(int x, int y, int width, int height, Identifier tex, int tex_x, int tex_y) {
        float px = 1/256f;
        float u1 = tex_x*px;
        float v1 = tex_y*px;
        float u2 = u1 + (width*px);
        float v2 = v1 + (height*px);
        textureFill(x, y, width, height, tex, u1, v1, u2, v2);
    }
    
    public static void textureFillGui(int x, int y, int width, int height, Identifier tex, int tex_x, int tex_y, int tex_width, int tex_height) {
        float px = 1/256f;
        float u1 = tex_x*px;
        float v1 = tex_y*px;
        float u2 = u1 + (tex_width*px);
        float v2 = v1 + (tex_height*px);
        textureFill(x, y, width, height, tex, u1, v1, u2, v2);
    }

    /**
     * Draws a beveled, round rectangle that is substantially similar to default Minecraft UI panels.
     */
    public static void drawGuiPanel(int x, int y, int width, int height) {
        /*if (LibGuiClient.config.darkMode) drawGuiPanel(x, y, width, height, 0xFF0B0B0B, 0xFF2F2F2F, 0xFF414141, 0xFF000000);
        else drawGuiPanel(x, y, width, height, 0xFF555555, 0xFFC6C6C6, 0xFFFFFFFF, 0xFF000000);*/
        drawGuiPanel(x, y, width, height, 0xFF0B0B0B, 0xFF2F2F2F, 0xFF414141, 0xFF000000);
    }

    public static void drawGuiPanel(int x, int y, int width, int height, int panelColor) {
        int shadowColor = multiplyColor(panelColor, 0.50f);
        int hilightColor = multiplyColor(panelColor, 1.25f);

        drawGuiPanel(x, y, width, height, shadowColor, panelColor, hilightColor, 0xFF000000);
    }

    public static void drawGuiPanel(int x, int y, int width, int height, int shadow, int panel, int hilight, int outline) {
        rect(x + 3,         y + 3,          width - 6, height - 6, panel); //Main panel area

        rect(x + 2,         y + 1,          width - 4, 2,          hilight); //Top hilight
        rect(x + 2,         y + height - 3, width - 4, 2,          shadow); //Bottom shadow
        rect(x + 1,         y + 2,          2,         height - 4, hilight); //Left hilight
        rect(x + width - 3, y + 2,          2,         height - 4, shadow); //Right shadow
        rect(x + width - 3, y + 2,          1,         1,          panel); //Topright non-hilight/non-shadow transition pixel
        rect(x + 2,         y + height - 3, 1,         1,          panel); //Bottomleft non-hilight/non-shadow transition pixel
        rect(x + 3,         y + 3,          1,         1,          hilight); //Topleft round hilight pixel
        rect(x + width - 4, y + height - 4, 1,         1,          shadow); //Bottomright round shadow pixel

        rect(x + 2,         y,              width - 4, 1,          outline); //Top outline
        rect(x,             y + 2,          1,         height - 4, outline); //Left outline
        rect(x + width - 1, y + 2,          1,         height - 4, outline); //Right outline
        rect(x + 2,         y + height - 1, width - 4, 1,          outline); //Bottom outline
        rect(x + 1,         y + 1,          1,         1,          outline); //Topleft round pixel
        rect(x + 1,         y + height - 2, 1,         1,          outline); //Bottomleft round pixel
        rect(x + width - 2, y + 1,          1,         1,          outline); //Topright round pixel
        rect(x + width - 2, y + height - 2, 1,         1,          outline); //Bottomright round pixel
    }

    /**
     * Draws a default-sized recessed itemslot panel
     */
    public static void drawBeveledPanel(int x, int y) {
        drawBeveledPanel(x, y, 18, 18, 0xFF373737, 0xFF8b8b8b, 0xFFFFFFFF);
    }

    /**
     * Draws a default-color recessed itemslot panel of variable size
     */
    public static void drawBeveledPanel(int x, int y, int width, int height) {
        drawBeveledPanel(x, y, width, height, 0xFF373737, 0xFF8b8b8b, 0xFFFFFFFF);
    }

    /**
     * Draws a generalized-case beveled panel. Can be inset or outset depending on arguments.
     * @param x				x coordinate of the topleft corner
     * @param y				y coordinate of the topleft corner
     * @param width			width of the panel
     * @param height		height of the panel
     * @param topleft		color of the top/left bevel
     * @param panel			color of the panel area
     * @param bottomright	color of the bottom/right bevel
     */
    public static void drawBeveledPanel(int x, int y, int width, int height, int topleft, int panel, int bottomright) {
        rect(x,             y,              width,     height,     panel); //Center panel
        rect(x,             y,              width - 1, 1,          topleft); //Top shadow
        rect(x,             y + 1,          1,         height - 2, topleft); //Left shadow
        rect(x + width - 1, y + 1,          1,         height - 1, bottomright); //Right hilight
        rect(x + 1,         y + height - 1, width - 1, 1,          bottomright); //Bottom hilight
    }

    public static void drawString(String s, int x, int y, int color) {
        MinecraftClient.getInstance().getFontManager().getTextRenderer(MinecraftClient.DEFAULT_TEXT_RENDERER_ID).draw(s, x, y, color);
    }

    public static void drawCenteredWithShadow(String s, int x, int y, int color) {
        TextRenderer render = MinecraftClient.getInstance().getFontManager().getTextRenderer(MinecraftClient.DEFAULT_TEXT_RENDERER_ID);
        render.drawWithShadow(s, (float)(x - render.getStringWidth(s) / 2), (float)y, color);
    }

    public static void drawTooltip(String s, int x, int y) {

    }

    public static int colorAtOpacity(int opaque, float opacity) {
        if (opacity<0.0f) opacity=0.0f;
        if (opacity>1.0f) opacity=1.0f;

        int a = (int)(opacity * 255.0f);

        return (opaque & 0xFFFFFF) | (a << 24);
    }

    public static int multiplyColor(int color, float amount) {
        int a = color & 0xFF000000;
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8  & 255) / 255.0F;
        float b = (color       & 255) / 255.0F;

        r = Math.min(r*amount, 1.0f);
        g = Math.min(g*amount, 1.0f);
        b = Math.min(b*amount, 1.0f);

        int ir = (int)(r*255);
        int ig = (int)(g*255);
        int ib = (int)(b*255);

        return
                a |
                        (ir << 16) |
                        (ig <<  8) |
                        ib;
    }

}