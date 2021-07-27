package io.github.vampirestudios.hgm.core.client;

import java.util.Random;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.entity.LaptopBlockEntity;
import static io.github.vampirestudios.hgm.utils.RenderingUtils.bindTexture;
import io.github.vampirestudios.hgm_rewrite.block.LaptopBlock;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;

public class LaptopRenderer implements BlockEntityRenderer<LaptopBlockEntity> {
    private final MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void render(LaptopBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
        matrices.push();
        {
            //Transform our coordinates into worldspace
            matrices.translate(blockEntity.getPos().getX(), blockEntity.getPos().getY(), blockEntity.getPos().getZ());


            //Our corner sits at the origin right now, let's really get that origin lodged in the center of the cube
            //Note: This is backwards! by rights, we should have to translate negative coords! No idea what's going on there.
            //Maybe the matrices are applied backwards
            matrices.translate(0.5, 0, 0.5);
            //Rotate us out to the direction we're facing
            Direction facing = blockEntity.getCachedState().get(Properties.HORIZONTAL_FACING);
            Quaternion quaternion = Quaternion.IDENTITY;
            quaternion.set(-facing.asRotation(), 0, 1, 0);
            matrices.multiply(quaternion);
            //Undo some of the transformations now. Move us back to corner-on
            matrices.translate(-0.5, 0, -0.5);

            //open the lid
            float screenAngle = blockEntity.getScreenAngle(tickDelta);
            //Change lid angle for testing
//                screenAngle+=(Math.sin(delta * 0.03)-1)*40;

            matrices.translate(0, 0.07, 0.12 + 1.0 / 16.0);
            quaternion = Quaternion.IDENTITY;
            quaternion.set(screenAngle, 1, 0, 0);
            matrices.multiply(quaternion);
            matrices.translate(0, -0.04, -1.0 / 16.0);

            //GlStateManager.translated(-pos.getX(), -pos.getY(), -pos.getZ());




            //GlStateManager.translated(0.5, 0, 0.5);
            //GlStateManager.rotated(-90F + 180F, 0, 1, 0);

            //GlStateManager.translated(0, 0.07, 0.12 + 1.0 / 16.0);
            //GlStateManager.rotated(te.getScreenAngle(partialTicks), 1, 0, 0);
            //GlStateManager.translated(0, -0.04, -1.0 / 16.0);

            DiffuseLighting.disableGuiDepthLighting();

            BlockRenderManager blockrendererdispatcher = MinecraftClient.getInstance().getBlockRenderManager();
            BakedModel ibakedmodel = mc.getBakedModelManager().getModel(new ModelIdentifier(new Identifier(HuskysGadgetMod.MOD_ID, "laptop_screen"), ""));
            if (ibakedmodel==null) return;
            BlockModelRenderer render = blockrendererdispatcher.getModelRenderer();
            BlockState blockState = blockEntity.getCachedState().with(LaptopBlock.FACING, facing);
            RenderLayer renderLayer = RenderLayers.getBlockLayer(blockState);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
            render.render(blockEntity.getWorld(), ibakedmodel, blockState, blockEntity.getPos(), matrices, vertexConsumer, false, new Random(),
                    blockState.getRenderingSeed(blockEntity.getPos()), overlay);

            DiffuseLighting.enableGuiDepthLighting();
        }
        matrices.pop();
    }

}