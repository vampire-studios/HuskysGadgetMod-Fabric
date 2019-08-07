package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.utils.VanillaPacketDispatcher;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.network.packet.BlockEntityUpdateS2CPacket;
import net.minecraft.nbt.CompoundTag;

public abstract class ModBlockEntity extends BlockEntity {

    public ModBlockEntity(BlockEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

	/*@Override
	public boolean shouldRefresh(World world, BlockPos pos, BlockState oldState, BlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}*/

    @Override
    public CompoundTag toTag(CompoundTag par1nbtTagCompound) {
        CompoundTag nbt = super.toTag(par1nbtTagCompound);

        writeSharedNBT(par1nbtTagCompound);
        return nbt;
    }

    @Override
    public void fromTag(CompoundTag par1nbtTagCompound) {
        super.fromTag(par1nbtTagCompound);

        readSharedNBT(par1nbtTagCompound);
    }

    public void writeSharedNBT(CompoundTag cmp) {
        // NO-OP
    }

    public void readSharedNBT(CompoundTag cmp) {
        // NO-OP
    }

    public void sync() {
        VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }

    @Override
    public CompoundTag toInitialChunkDataTag() {
        return toTag(new CompoundTag());
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        CompoundTag cmp = new CompoundTag();
        writeSharedNBT(cmp);
        return new BlockEntityUpdateS2CPacket(getPos(), 0, cmp);
    }

}
