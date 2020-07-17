package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.utils.TileEntityUtil;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;

public abstract class SyncBlockEntity extends ModBlockEntity {

    protected CompoundTag pipeline = new CompoundTag();

    public SyncBlockEntity(BlockEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public void sync() {
        TileEntityUtil.markBlockForUpdate(world, pos);
        markDirty();
    }

    @Override
    public final CompoundTag toInitialChunkDataTag() {
        if (!pipeline.isEmpty()) {
            CompoundTag updateTag = super.toTag(pipeline);
            pipeline = new CompoundTag();
            return updateTag;
        }
        return super.toTag(writeSyncTag());
    }

    public abstract CompoundTag writeSyncTag();

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(pos, 0, toInitialChunkDataTag());
    }

    public CompoundTag getPipeline() {
        return pipeline;
    }

}