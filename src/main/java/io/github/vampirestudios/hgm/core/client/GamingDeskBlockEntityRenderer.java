package io.github.vampirestudios.hgm.core.client;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.entity.GamingDeskBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.model.BedEntityModel;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Arrays;
import java.util.Comparator;

@Environment(EnvType.CLIENT)
public class GamingDeskBlockEntityRenderer extends BlockEntityRenderer<GamingDeskBlockEntity> {
   private static final Identifier[] TEXTURES = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map((dyeColor_1) ->
           new Identifier(HuskysGadgetMod.MOD_ID, "textures/block/gaming_desk/" + dyeColor_1.getName() + ".png")).toArray(Identifier[]::new);
   private final BedEntityModel model = new BedEntityModel();

   @Override
   public void render(GamingDeskBlockEntity bedBlockEntity_1, double double_1, double double_2, double double_3, float float_1, int int_1) {
      if (int_1 >= 0) {
         this.bindTexture(DESTROY_STAGE_TEXTURES[int_1]);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.scalef(4.0F, 4.0F, 1.0F);
         GlStateManager.translatef(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.matrixMode(5888);
      } else {
         Identifier identifier_1 = TEXTURES[bedBlockEntity_1.getColor().getId()];
         if (identifier_1 != null) {
            this.bindTexture(identifier_1);
         }
      }

      if (bedBlockEntity_1.hasWorld()) {
         BlockState blockState_1 = bedBlockEntity_1.getCachedState();
         this.method_3558(blockState_1.get(BedBlock.PART) == BedPart.HEAD, double_1, double_2, double_3, blockState_1.get(BedBlock.FACING));
      } else {
         this.method_3558(true, double_1, double_2, double_3, Direction.SOUTH);
         this.method_3558(false, double_1, double_2, double_3 - 1.0D, Direction.SOUTH);
      }

      if (int_1 >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }

   }

   private void method_3558(boolean boolean_1, double double_1, double double_2, double double_3, Direction direction_1) {
      this.model.setVisible(boolean_1);
      GlStateManager.pushMatrix();
      GlStateManager.translatef((float)double_1, (float)double_2 + 0.5625F, (float)double_3);
      GlStateManager.rotatef(90.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.translatef(0.5F, 0.5F, 0.5F);
      GlStateManager.rotatef(180.0F + direction_1.asRotation(), 0.0F, 0.0F, 1.0F);
      GlStateManager.translatef(-0.5F, -0.5F, -0.5F);
      GlStateManager.enableRescaleNormal();
      this.model.render();
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }
}
