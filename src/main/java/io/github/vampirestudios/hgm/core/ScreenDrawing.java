package io.github.vampirestudios.hgm.core;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class ScreenDrawing {
    public static void colorFill(int x, int y, int width, int height, int color) {
        float r = (color >> 16) & 0xFF; r /= 255f;
        float g = (color >>  8) & 0xFF; g /= 255f;
        float b = (color >>  0) & 0xFF; b /= 255f;
        
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
}