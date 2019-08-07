package io.github.vampirestudios.hgm.core.client;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.api.print.IPrint;
import io.github.vampirestudios.hgm.api.print.PrintingManager;
import io.github.vampirestudios.hgm.block.BlockPaper;
import io.github.vampirestudios.hgm.block.entity.PaperBlockEntity;
import io.github.vampirestudios.hgm.init.GadgetBlocks;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.Direction;
import org.lwjgl.opengl.GL11;

public class PaperRenderer extends BlockEntityRenderer<PaperBlockEntity> {
    private static void drawCuboid(double x, double y, double z, double width, double height, double depth) {
        x /= 16;
        y /= 16;
        z /= 16;
        width /= 16;
        height /= 16;
        depth /= 16;
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.normal3f(0.0F, 1.0F, 0.0F);
        drawQuad(x + (1 - width), y, z, x + width + (1 - width), y + height, z, Direction.NORTH);
        drawQuad(x + 1, y, z, x + 1, y + height, z + depth, Direction.EAST);
        drawQuad(x + width + 1 - (width + width), y, z + depth, x + width + 1 - (width + width), y + height, z, Direction.WEST);
        drawQuad(x + (1 - width), y, z + depth, x + width + (1 - width), y, z, Direction.DOWN);
        drawQuad(x + (1 - width), y + height, z, x + width + (1 - width), y, z + depth, Direction.UP);
        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
    }

    private static void drawQuad(double xFrom, double yFrom, double zFrom, double xTo, double yTo, double zTo, Direction facing) {
        double textureWidth = Math.abs(xTo - xFrom);
        double textureHeight = Math.abs(yTo - yFrom);
        double textureDepth = Math.abs(zTo - zFrom);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBufferBuilder();
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_UV);
        switch (facing.getAxis()) {
            case X:
                buffer.vertex(xFrom, yFrom, zFrom).texture(1 - xFrom + textureDepth, 1 - yFrom + textureHeight).next();
                buffer.vertex(xFrom, yTo, zFrom).texture(1 - xFrom + textureDepth, 1 - yFrom).next();
                buffer.vertex(xTo, yTo, zTo).texture(1 - xFrom, 1 - yFrom).next();
                buffer.vertex(xTo, yFrom, zTo).texture(1 - xFrom, 1 - yFrom + textureHeight).next();
                break;
            case Y:
                buffer.vertex(xFrom, yFrom, zFrom).texture(1 - xFrom + textureWidth, 1 - yFrom + textureDepth).next();
                buffer.vertex(xFrom, yFrom, zTo).texture(1 - xFrom + textureWidth, 1 - yFrom).next();
                buffer.vertex(xTo, yFrom, zTo).texture(1 - xFrom, 1 - yFrom).next();
                buffer.vertex(xTo, yFrom, zFrom).texture(1 - xFrom, 1 - yFrom + textureDepth).next();
                break;
            case Z:
                buffer.vertex(xFrom, yFrom, zFrom).texture(1 - xFrom + textureWidth, 1 - yFrom + textureHeight).next();
                buffer.vertex(xFrom, yTo, zFrom).texture(1 - xFrom + textureWidth, 1 - yFrom).next();
                buffer.vertex(xTo, yTo, zTo).texture(1 - xFrom, 1 - yFrom).next();
                buffer.vertex(xTo, yFrom, zTo).texture(1 - xFrom, 1 - yFrom + textureHeight).next();
                break;
        }
        tessellator.draw();
    }

    private static void drawPixels(int[] pixels, int resolution, boolean cut) {
        double scale = 16 / (double) resolution;
        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                float a = (float) Math.floor((pixels[j + i * resolution] >> 24 & 255) / 255.0F);
                if (a < 1.0F) {
                    if (cut) continue;
                    GlStateManager.color3f(1.0F, 1.0F, 1.0F);
                } else {
                    float r = (float) (pixels[j + i * resolution] >> 16 & 255) / 255.0F;
                    float g = (float) (pixels[j + i * resolution] >> 8 & 255) / 255.0F;
                    float b = (float) (pixels[j + i * resolution] & 255) / 255.0F;
                    GlStateManager.color4f(r, g, b, a);
                }
                drawCuboid(j * scale - (resolution - 1) * scale, -i * scale + (resolution - 1) * scale, -1, scale, scale, 1);
            }
        }
    }

    @Override
    public void render(PaperBlockEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        {
            GlStateManager.translated(x, y, z);
            GlStateManager.translated(0.5, 0.5, 0.5);
            BlockState state = te.getWorld().getBlockState(te.getPos());
            if (state.getBlock() != GadgetBlocks.PAPER) return;
            GlStateManager.rotatef(state.get(BlockPaper.FACING).getHorizontal() * -90F + 180F, 0, 1, 0);
            GlStateManager.rotatef(-te.getRotation(), 0, 0, 1);
            GlStateManager.translated(-0.5, -0.5, -0.5);

            IPrint print = te.getPrint();
            if (print != null) {
                CompoundTag data = print.toTag();
                if (data.containsKey("pixels", Constants.NBT.TAG_INT_ARRAY) && data.containsKey("resolution", Constants.NBT.TAG_INT)) {
                    MinecraftClient.getInstance().getTextureManager().bindTexture(PrinterRenderer.ModelPaper.TEXTURE);
                    if (HuskysGadgetMod.config.applicationSettings.renderPrintedIn3D && !data.getBoolean("cut")) {
                        drawCuboid(0, 0, 0, 16, 16, 1);
                    }

                    GlStateManager.translated(0, 0, HuskysGadgetMod.config.applicationSettings.renderPrintedIn3D ? 0.0625 : 0.001);

                    GlStateManager.pushMatrix();
                    {
                        IPrint.Renderer renderer = PrintingManager.getRenderer(print);
                        renderer.render(data);
                    }
                    GlStateManager.popMatrix();

                    GlStateManager.pushMatrix();
                    {
                        if (HuskysGadgetMod.config.applicationSettings.renderPrintedIn3D && data.getBoolean("cut")) {
                            CompoundTag tag = print.toTag();
                            drawPixels(tag.getIntArray("pixels"), tag.getInt("resolution"), tag.getBoolean("cut"));
                        }
                    }
                    GlStateManager.popMatrix();
                }
            }
        }
        GlStateManager.popMatrix();
    }
}