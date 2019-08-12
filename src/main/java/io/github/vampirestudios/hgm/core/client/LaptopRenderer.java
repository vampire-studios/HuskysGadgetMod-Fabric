package io.github.vampirestudios.hgm.core.client;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.LaptopBlock;
import io.github.vampirestudios.hgm.block.entity.LaptopBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class LaptopRenderer extends BlockEntityRenderer<LaptopBlockEntity> {

    private MinecraftClient mc = MinecraftClient.getInstance();

    //private ItemEntity entityItem = new ItemEntity(MinecraftClient.getInstance().world, 0D, 0D, 0D);
    private double delta = 0;
    @Override
    public void render(LaptopBlockEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        delta+=partialTicks;
        
        //BlockPos pos = te.getPos();
        //BlockState state = te.getWorld().getBlockState(pos).with(LaptopBlock.TYPE, LaptopBlock.Type.SCREEN);

        bindTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEX);
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

            GlStateManager.pushMatrix();
            {
                //Transform our coordinates into worldspace
                GlStateManager.translated(x, y, z);
                
                
                //Our corner sits at the origin right now, let's really get that origin lodged in the center of the cube
                //Note: This is backwards! by rights, we should have to translate negative coords! No idea what's going on there.
                //Maybe the matrices are applied backwards
                GlStateManager.translated(0.5, 0, 0.5);
                //Rotate us out to the direction we're facing
                Direction facing = te.getCachedState().get(Properties.HORIZONTAL_FACING);
                GlStateManager.rotated(-facing.asRotation(), 0, 1, 0);
                //Undo some of the transformations now. Move us back to corner-on
                GlStateManager.translated(-0.5, 0, -0.5);
                
                //open the lid
                float screenAngle = te.getScreenAngle(partialTicks);
                //Change lid angle for testing
                screenAngle+=(Math.sin(delta * 0.03)-1)*40;
                
                GlStateManager.translated(0, 0.07, 0.12 + 1.0 / 16.0);
                GlStateManager.rotated(screenAngle, 1, 0, 0);
                GlStateManager.translated(0, -0.04, -1.0 / 16.0);
                
                //GlStateManager.translated(-pos.getX(), -pos.getY(), -pos.getZ());
                
                
                
                
                //GlStateManager.translated(0.5, 0, 0.5);
                //GlStateManager.rotated(-90F + 180F, 0, 1, 0);
               
                //GlStateManager.translated(0, 0.07, 0.12 + 1.0 / 16.0);
                //GlStateManager.rotated(te.getScreenAngle(partialTicks), 1, 0, 0);
                //GlStateManager.translated(0, -0.04, -1.0 / 16.0);

                GlStateManager.disableLighting();
                //Tessellator tessellator = Tessellator.getInstance();
                //BufferBuilder buffer = tessellator.getBufferBuilder();
                //buffer.begin(7, VertexFormats.POSITION_COLOR_UV_NORMAL);
                //buffer.setOffset(-pos.getX(), -pos.getY(), -pos.getZ());

                BlockRenderManager blockrendererdispatcher = MinecraftClient.getInstance().getBlockRenderManager();
                BakedModel ibakedmodel = mc.getBakedModelManager().getModel(new ModelIdentifier(new Identifier(HuskysGadgetMod.MOD_ID, "laptop_screen"), ""));
                if (ibakedmodel==null) return;
                //BakedModel ibakedmodel = mc.getBakedModelManager().getBlockStateMaps().getModel(state);
                BlockModelRenderer render = blockrendererdispatcher.getModelRenderer();
                render.render(null, ibakedmodel, /* brightness */ 1f, /*r*/ 1f, /*g*/ 1f, /*b*/ 1f);
                //blockrendererdispatcher.getModelRenderer().tesselate(getWorld(), ibakedmodel, state, pos, buffer, false, getWorld().random, state.getRenderingSeed(pos));

                //buffer.setOffset(0.0D, 0.0D, 0.0D);
                //tessellator.draw();
                GlStateManager.enableLighting();
            }
            GlStateManager.popMatrix();
        //}
        //GlStateManager.popMatrix();
    }
}