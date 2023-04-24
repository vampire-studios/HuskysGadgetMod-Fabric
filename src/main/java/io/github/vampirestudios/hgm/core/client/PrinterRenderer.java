package io.github.vampirestudios.hgm.core.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.api.print.IPrint;
import io.github.vampirestudios.hgm.api.print.PrintingManager;
import io.github.vampirestudios.hgm.block.PrinterBlock;
import io.github.vampirestudios.hgm.block.entity.PrinterBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Quaternion;

public class PrinterRenderer implements BlockEntityRenderer<PrinterBlockEntity> {

    private static final ModelPaper MODEL_PAPER = new ModelPaper();

    @Override
    public void render(PrinterBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockPos pos = blockEntity.getPos();
        matrices.push();
        {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            matrices.translate(pos.getX(), pos.getY(), pos.getZ());

            if (blockEntity.hasPaper()) {
                matrices.push();
                {
                    matrices.translate(0.5, 0.5, 0.5);
                    BlockState state = blockEntity.getWorld().getBlockState(blockEntity.getPos());
                    matrices.multiply(new Quaternion(state.get(PrinterBlock.FACING).getHorizontal() * -90F, 0, 1, 0));
                    matrices.multiply(new Quaternion(22.5F, 1, 0, 0));
                    matrices.translate(0, 0.1, 0.35);
                    matrices.translate(-11 * 0.015625, -13 * 0.015625, -0.5 * 0.015625);
                    MODEL_PAPER.render(null, null, 15, 0, 0F, 0F, 0F, 0.3F);
                }
                matrices.pop();
            }

            matrices.push();
            {
                if (blockEntity.isLoading()) {
                    matrices.translate(0.5, 0.5, 0.5);
                    BlockState state1 = blockEntity.getWorld().getBlockState(blockEntity.getPos());
                    matrices.multiply(new Quaternion(state1.get(PrinterBlock.FACING).getHorizontal() * -90F, 0, 1, 0));
                    matrices.multiply(new Quaternion(22.5F, 1, 0, 0));
                    double progress = Math.max(-0.4, -0.4 + (0.4 * ((double) (blockEntity.getRemainingPrintTime() - 10) / 20)));
                    matrices.translate(0, progress, 0.36875);
                    matrices.translate(-11 * 0.015625, -13 * 0.015625, -0.5 * 0.015625);
                    MODEL_PAPER.render(null, null, 15, 0, 0F, 0F, 0F, 0.015625F);
                } else if (blockEntity.isPrinting()) {
                    matrices.translate(0.5, 0.078125, 0.5);
                    BlockState state1 = blockEntity.getWorld().getBlockState(blockEntity.getPos());
                    matrices.multiply(new Quaternion(state1.get(PrinterBlock.FACING).getHorizontal() * -90F, 0, 1, 0));
                    matrices.multiply(new Quaternion(90F, 1, 0, 0));
                    double progress = -0.35 + (0.50 * ((double) (blockEntity.getRemainingPrintTime() - 20) / blockEntity.getTotalPrintTime()));
                    matrices.translate(0, progress, 0);
                    matrices.translate(-11 * 0.015625, -13 * 0.015625, -0.5 * 0.015625);
                    MODEL_PAPER.render(null, null, 15, 0, 0F, 0F, 0F, 0.015625F);

                    matrices.translate(0.3225, 0.085, -0.001);
                    matrices.multiply(new Quaternion(180F, 0, 1, 0));
                    matrices.scale(0.3F, 0.3F, 0.3F);

                    IPrint print = blockEntity.getPrint();
                    if (print != null) {
                        IPrint.Renderer renderer = PrintingManager.getRenderer(print);
                        renderer.render(print.toTag());
                    }
                }
            }
            matrices.pop();
        }
        matrices.pop();

        matrices.push();
        {
            matrices.translate(0, -0.5, 0);
        }
        matrices.pop();
    }

    public static class ModelPaper extends EntityModel {
        public static final Identifier TEXTURE = new Identifier(HuskysGadgetMod.MOD_ID, "textures/model/paper.png");

        private ModelPart box = new ModelPart(this, 0, 0).addCuboid(0, 0, 0, 22, 30, 1);

        @Override
        public void setAngles(Entity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {

        }

        @Override
        public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE);
            box.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        }
    }
}
