package io.github.vampirestudios.hgm.core.client;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.BlockLaptop;
import io.github.vampirestudios.hgm.block.entity.TileEntityLaptop;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class LaptopRenderer extends BlockEntityRenderer<TileEntityLaptop> {

    private MinecraftClient mc = MinecraftClient.getInstance();

    private ItemEntity entityItem = new ItemEntity(MinecraftClient.getInstance().world, 0D, 0D, 0D);

    @Override
    public void render(TileEntityLaptop te, double x, double y, double z, float partialTicks, int destroyStage) {
        BlockPos pos = te.getPos();
        BlockState state = te.getWorld().getBlockState(pos).with(BlockLaptop.TYPE, BlockLaptop.Type.SCREEN);

        bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
        GlStateManager.pushMatrix();
        {
            GlStateManager.translated(x, y, z);

            if (te.isExternalDriveAttached()) {
                GlStateManager.pushMatrix();
                {
                    GlStateManager.translated(0.5, 0, 0.5);
                    GlStateManager.rotated(-100F - 90F, 0, 1, 0);
                    GlStateManager.translated(-0.5, 0, -0.5);
                    GlStateManager.translated(0.595, -0.2075, -0.005);
                    entityItem.setStack(new ItemStack(Registry.ITEM.get(new Identifier(HuskysGadgetMod.MOD_ID + "flash_drive_" + te.getExternalDriveColor().getName())), 1));
                    MinecraftClient.getInstance().getEntityRenderManager().render(entityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
                    GlStateManager.translated(0.1, 0, 0);
                }
                GlStateManager.popMatrix();
            }

            GlStateManager.pushMatrix();
            {
                GlStateManager.translated(0.5, 0, 0.5);
                GlStateManager.rotated(-90F + 180F, 0, 1, 0);
                GlStateManager.translated(-0.5, 0, -0.5);
                GlStateManager.translated(0, 0.07, 0.12 + 1.0 / 16.0);
                GlStateManager.rotated(te.getScreenAngle(partialTicks), 1, 0, 0);
                GlStateManager.translated(0, -0.04, -1.0 / 16.0);

                GlStateManager.disableLighting();
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.getBufferBuilder();
                buffer.begin(7, VertexFormats.POSITION);
                buffer.setOffset(-pos.getX(), -pos.getY(), -pos.getZ());

                BlockRenderManager blockrendererdispatcher = MinecraftClient.getInstance().getBlockRenderManager();

                BakedModel ibakedmodel = mc.getBakedModelManager().getBlockStateMaps().getModel(state);
                blockrendererdispatcher.getModelRenderer().tesselate(getWorld(), ibakedmodel, state, pos, buffer, false, getWorld().random, state.getRenderingSeed(pos));

                buffer.setOffset(0.0D, 0.0D, 0.0D);
                tessellator.draw();
                GlStateManager.enableLighting();
            }
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
    }
}