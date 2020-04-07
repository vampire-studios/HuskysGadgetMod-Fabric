package io.github.vampirestudios.hgm.core.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.api.print.IPrint;
import io.github.vampirestudios.hgm.api.print.PrintingManager;
import io.github.vampirestudios.hgm.block.PaperBlock;
import io.github.vampirestudios.hgm.block.entity.PaperBlockEntity;
import io.github.vampirestudios.hgm.init.HGMBlocks;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.lwjgl.opengl.GL11;

public class PaperRenderer extends BlockEntityRenderer<PaperBlockEntity> {

    public PaperRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    private static void drawCuboid(float x, float y, float z, float width, float height) {
        x /= 16;
        y /= 16;
        z /= 16;
        width /= 16;
        height /= 16;
        RenderSystem.disableLighting();
        RenderSystem.enableRescaleNormal();
        RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
        drawQuad(x + (1 - width), y, z, x + width + (1 - width), y + height, z, Direction.NORTH);
        drawQuad(x + 1, y, z, x + 1, y + height, z + (float) 1, Direction.EAST);
        float xFrom = x + width + 1 - (width + width);
        drawQuad(xFrom, y, z + (float) 1, xFrom, y + height, z, Direction.WEST);
        drawQuad(x + (1 - width), y, z + (float) 1, x + width + (1 - width), y, z, Direction.DOWN);
        drawQuad(x + (1 - width), y + height, z, x + width + (1 - width), y, z + (float) 1, Direction.UP);
        RenderSystem.disableRescaleNormal();
        RenderSystem.enableLighting();
    }

    private static void drawQuad(float xFrom, float yFrom, float zFrom, float xTo, float yTo, float zTo, Direction facing) {
        float textureWidth = Math.abs(xTo - xFrom);
        float textureHeight = Math.abs(yTo - yFrom);
        float textureDepth = Math.abs(zTo - zFrom);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE);
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
        float scale = 16 / resolution;
        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                float a = (float) Math.floor((pixels[j + i * resolution] >> 24 & 255) / 255.0F);
                if (a < 1.0F) {
                    if (cut) continue;
                    RenderSystem.color3f(1.0F, 1.0F, 1.0F);
                } else {
                    float r = (float) (pixels[j + i * resolution] >> 16 & 255) / 255.0F;
                    float g = (float) (pixels[j + i * resolution] >> 8 & 255) / 255.0F;
                    float b = (float) (pixels[j + i * resolution] & 255) / 255.0F;
                    RenderSystem.color4f(r, g, b, a);
                }
                drawCuboid(j * scale - (resolution - 1) * scale, -i * scale + (resolution - 1) * scale, -1, scale, scale);
            }
        }
    }

    @Override
    public void render(PaperBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        RenderSystem.pushMatrix();
        {
            BlockPos pos = blockEntity.getPos();
            RenderSystem.translated(pos.getX(), pos.getY(), pos.getZ());
            RenderSystem.translated(0.5, 0.5, 0.5);
            BlockState state = blockEntity.getWorld().getBlockState(blockEntity.getPos());
            if (state.getBlock() != HGMBlocks.PAPER) return;
            RenderSystem.rotatef(state.get(PaperBlock.FACING).getHorizontal() * -90F + 180F, 0, 1, 0);
            RenderSystem.rotatef(-blockEntity.getRotation(), 0, 0, 1);
            RenderSystem.translated(-0.5, -0.5, -0.5);

            IPrint print = blockEntity.getPrint();
            if (print != null) {
                CompoundTag data = print.toTag();
                if (data.contains("pixels", Constants.NBT.TAG_INT_ARRAY) && data.contains("resolution", Constants.NBT.TAG_INT)) {
                    MinecraftClient.getInstance().getTextureManager().bindTexture(PrinterRenderer.ModelPaper.TEXTURE);
                    if (HuskysGadgetMod.config.applicationSettings.renderPrintedIn3D && !data.getBoolean("cut")) {
                        drawCuboid(0, 0, 0, 16, 16);
                    }

                    RenderSystem.translated(0, 0, HuskysGadgetMod.config.applicationSettings.renderPrintedIn3D ? 0.0625 : 0.001);

                    RenderSystem.pushMatrix();
                    {
                        IPrint.Renderer renderer = PrintingManager.getRenderer(print);
                        renderer.render(data);
                    }
                    RenderSystem.popMatrix();

                    RenderSystem.pushMatrix();
                    {
                        if (HuskysGadgetMod.config.applicationSettings.renderPrintedIn3D && data.getBoolean("cut")) {
                            CompoundTag tag = print.toTag();
                            drawPixels(tag.getIntArray("pixels"), tag.getInt("resolution"), tag.getBoolean("cut"));
                        }
                    }
                    RenderSystem.popMatrix();
                }
            }
        }
        RenderSystem.popMatrix();
    }

}