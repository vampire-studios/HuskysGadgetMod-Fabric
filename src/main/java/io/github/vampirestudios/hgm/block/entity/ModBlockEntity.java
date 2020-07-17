package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.utils.VanillaPacketDispatcher;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;

public abstract class ModBlockEntity extends BlockEntity {

    public ModBlockEntity(BlockEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public CompoundTag toTag(CompoundTag par1nbtTagCompound) {
        super.toTag(par1nbtTagCompound);
        return par1nbtTagCompound;
    }

    @Override
    public void fromTag(CompoundTag par1nbtTagCompound) {
        super.fromTag(par1nbtTagCompound);
    }

    public void sync() {
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }

    @Override
    public CompoundTag toInitialChunkDataTag() {
        CompoundTag compound = super.toTag(new CompoundTag());
        toTag(compound);
        return compound;
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(getPos(), 0, toInitialChunkDataTag());
    }

}
