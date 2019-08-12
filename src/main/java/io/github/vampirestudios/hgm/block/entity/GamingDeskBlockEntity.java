package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.block.GamingDeskBlock;
import io.github.vampirestudios.hgm.init.HGMBlockEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.packet.BlockEntityUpdateS2CPacket;
import net.minecraft.util.DyeColor;

public class GamingDeskBlockEntity extends BlockEntity {
   private DyeColor color;

   public GamingDeskBlockEntity() {
      super(HGMBlockEntities.GAMING_DESK);
   }

   public GamingDeskBlockEntity(DyeColor dyeColor_1) {
      this();
      this.setColor(dyeColor_1);
   }

   public BlockEntityUpdateS2CPacket toUpdatePacket() {
      return new BlockEntityUpdateS2CPacket(this.pos, 11, this.toInitialChunkDataTag());
   }

   @Environment(EnvType.CLIENT)
   public DyeColor getColor() {
      if (this.color == null) {
         this.color = ((GamingDeskBlock)this.getCachedState().getBlock()).getDyeColor();
      }

      return this.color;
   }

   public void setColor(DyeColor dyeColor_1) {
      this.color = dyeColor_1;
   }

}
