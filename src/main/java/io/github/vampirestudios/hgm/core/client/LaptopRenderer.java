package io.github.vampirestudios.hgm.core.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.entity.LaptopBlockEntity;
import io.github.vampirestudios.hgm_rewrite.block.LaptopBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Random;

import static io.github.vampirestudios.hgm.utils.RenderingUtils.bindTexture;

public class LaptopRenderer extends BlockEntityRenderer<LaptopBlockEntity> {

    private MinecraftClient mc = MinecraftClient.getInstance();

    public LaptopRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    //private ItemEntity entityItem = new ItemEntity(MinecraftClient.getInstance().world, 0D, 0D, 0D);
    @Override
    public void render(LaptopBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
        //GlStateManager.pushMatrix();
        //{
            
            /*
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
            }*/

            RenderSystem.pushMatrix();
            {
                //Transform our coordinates into worldspace
                RenderSystem.translated(blockEntity.getPos().getX(), blockEntity.getPos().getY(), blockEntity.getPos().getZ());


                //Our corner sits at the origin right now, let's really get that origin lodged in the center of the cube
                //Note: This is backwards! by rights, we should have to translate negative coords! No idea what's going on there.
                //Maybe the matrices are applied backwards
                RenderSystem.translated(0.5, 0, 0.5);
                //Rotate us out to the direction we're facing
                Direction facing = blockEntity.getCachedState().get(Properties.HORIZONTAL_FACING);
                RenderSystem.rotatef(-facing.asRotation(), 0, 1, 0);
                //Undo some of the transformations now. Move us back to corner-on
                RenderSystem.translated(-0.5, 0, -0.5);

                //open the lid
                float screenAngle = blockEntity.getScreenAngle(tickDelta);
                //Change lid angle for testing
//                screenAngle+=(Math.sin(delta * 0.03)-1)*40;

                RenderSystem.translated(0, 0.07, 0.12 + 1.0 / 16.0);
                RenderSystem.rotatef(screenAngle, 1, 0, 0);
                RenderSystem.translated(0, -0.04, -1.0 / 16.0);

                //GlStateManager.translated(-pos.getX(), -pos.getY(), -pos.getZ());




                //GlStateManager.translated(0.5, 0, 0.5);
                //GlStateManager.rotated(-90F + 180F, 0, 1, 0);

                //GlStateManager.translated(0, 0.07, 0.12 + 1.0 / 16.0);
                //GlStateManager.rotated(te.getScreenAngle(partialTicks), 1, 0, 0);
                //GlStateManager.translated(0, -0.04, -1.0 / 16.0);

                RenderSystem.disableLighting();

                BlockRenderManager blockrendererdispatcher = MinecraftClient.getInstance().getBlockRenderManager();
                BakedModel ibakedmodel = mc.getBakedModelManager().getModel(new ModelIdentifier(new Identifier(HuskysGadgetMod.MOD_ID, "laptop_screen"), ""));
                if (ibakedmodel==null) return;
                BlockModelRenderer render = blockrendererdispatcher.getModelRenderer();
                BlockState blockState = blockEntity.getCachedState().with(LaptopBlock.FACING, facing);
                RenderLayer renderLayer = RenderLayers.getBlockLayer(blockState);
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
                render.render(blockEntity.getWorld(), ibakedmodel, blockState, blockEntity.getPos(), matrices, vertexConsumer, false, new Random(),
                        blockState.getRenderingSeed(blockEntity.getPos()), overlay);

                RenderSystem.enableLighting();
            }
            RenderSystem.popMatrix();
        //}
        //GlStateManager.popMatrix();
    }

}