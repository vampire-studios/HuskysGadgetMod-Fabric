package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.utils.TileEntityUtil;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;

public abstract class SyncBlockEntity extends ModBlockEntity {

    protected NbtCompound pipeline = new NbtCompound();

    public SyncBlockEntity(BlockEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public void sync() {
        TileEntityUtil.markBlockForUpdate(world, pos);
        markDirty();
    }

    @Override
    public final NbtCompound toInitialChunkDataTag() {
        if (!pipeline.isEmpty()) {
            NbtCompound updateTag = super.toTag(pipeline);
            pipeline = new NbtCompound();
            return updateTag;
        }
        return super.toTag(writeSyncTag());
    }

    public abstract NbtCompound writeSyncTag();

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(pos, 0, toInitialChunkDataTag());
    }

    public NbtCompound getPipeline() {
        return pipeline;
    }

}