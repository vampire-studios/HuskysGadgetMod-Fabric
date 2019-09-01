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
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class PrinterRenderer extends BlockEntityRenderer<PrinterBlockEntity> {

    private static final ModelPaper MODEL_PAPER = new ModelPaper();

    @Override
    public void render(PrinterBlockEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        RenderSystem.pushMatrix();
        {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.translated(x, y, z);

            if (te.hasPaper()) {
                RenderSystem.pushMatrix();
                {
                    RenderSystem.translated(0.5, 0.5, 0.5);
                    BlockState state = te.getWorld().getBlockState(te.getPos());
                    RenderSystem.rotatef(state.get(PrinterBlock.FACING).getHorizontal() * -90F, 0, 1, 0);
                    RenderSystem.rotated(22.5F, 1, 0, 0);
                    RenderSystem.translated(0, 0.1, 0.35);
                    RenderSystem.translated(-11 * 0.015625, -13 * 0.015625, -0.5 * 0.015625);
                    MODEL_PAPER.render(null, 0F, 0F, 0F, 0F, 0F, 0.3F);
                }
                RenderSystem.popMatrix();
            }

            RenderSystem.pushMatrix();
            {
                if (te.isLoading()) {
                    RenderSystem.translated(0.5, 0.5, 0.5);
                    BlockState state1 = te.getWorld().getBlockState(te.getPos());
                    RenderSystem.rotated(state1.get(PrinterBlock.FACING).getHorizontal() * -90F, 0, 1, 0);
                    RenderSystem.rotated(22.5F, 1, 0, 0);
                    double progress = Math.max(-0.4, -0.4 + (0.4 * ((double) (te.getRemainingPrintTime() - 10) / 20)));
                    RenderSystem.translated(0, progress, 0.36875);
                    RenderSystem.translated(-11 * 0.015625, -13 * 0.015625, -0.5 * 0.015625);
                    MODEL_PAPER.render(null, 0F, 0F, 0F, 0F, 0F, 0.015625F);
                } else if (te.isPrinting()) {
                    RenderSystem.translated(0.5, 0.078125, 0.5);
                    BlockState state1 = te.getWorld().getBlockState(te.getPos());
                    RenderSystem.rotated(state1.get(PrinterBlock.FACING).getHorizontal() * -90F, 0, 1, 0);
                    RenderSystem.rotated(90F, 1, 0, 0);
                    double progress = -0.35 + (0.50 * ((double) (te.getRemainingPrintTime() - 20) / te.getTotalPrintTime()));
                    RenderSystem.translated(0, progress, 0);
                    RenderSystem.translated(-11 * 0.015625, -13 * 0.015625, -0.5 * 0.015625);
                    MODEL_PAPER.render(null, 0F, 0F, 0F, 0F, 0F, 0.015625F);

                    RenderSystem.translated(0.3225, 0.085, -0.001);
                    RenderSystem.rotated(180F, 0, 1, 0);
                    RenderSystem.scaled(0.3, 0.3, 0.3);

                    IPrint print = te.getPrint();
                    if (print != null) {
                        IPrint.Renderer renderer = PrintingManager.getRenderer(print);
                        renderer.render(print.toTag());
                    }
                }
            }
            RenderSystem.popMatrix();
        }
        RenderSystem.popMatrix();

        RenderSystem.pushMatrix();
        {
            RenderSystem.translated(0, -0.5, 0);
            super.render(te, x, y, z, partialTicks, destroyStage);
        }
        RenderSystem.popMatrix();
    }

    public static class ModelPaper extends EntityModel {
        public static final Identifier TEXTURE = new Identifier(HuskysGadgetMod.MOD_ID, "textures/model/paper.png");

        private ModelPart box = new ModelPart(this, 0, 0).addCuboid(0, 0, 0, 22, 30, 1);

        @Override
        public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE);
            box.render(scale);
        }
    }
}
