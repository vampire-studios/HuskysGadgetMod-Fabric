package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.init.HGMBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;

import java.util.Random;

public class EasterEggBlockEntity extends BlockEntity {

    static Random rng = new Random();
    private int color0, color1;

    public EasterEggBlockEntity() {
        super(HGMBlockEntities.EASTER_EGG);
        this.color0 = rng.nextInt(0xFFFFFF);
        this.color1 = rng.nextInt(0xFFFFFF);
    }

    @Override
    public CompoundTag toTag(CompoundTag compound) {
        compound = super.toTag(compound);
        this.writeColorsToNBT(compound);
        return compound;
    }

    public CompoundTag writeColorsToNBT(CompoundTag compound) {
        for (int i = 0; i < 2; i++) {
            compound.putInt("color" + i, this.getColor(i));
        }
        return compound;
    }

    @Override
    public void fromTag(BlockState blockState, CompoundTag compound) {
        super.fromTag(blockState, compound);
        this.readColorsFromNBT(compound);
    }

    private void readColorsFromNBT(CompoundTag compound) {
        for (int i = 0; i < 2; i++) {
            if (compound.contains("color" + i)) {
                this.setColor(i, compound.getInt("color" + i));
            }
        }
    }

    @Override
    public CompoundTag toInitialChunkDataTag() {
        return this.writeColorsToNBT(new CompoundTag());
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, 0, this.toInitialChunkDataTag());
    }

    public int getColor(int index) {
        return index == 0 ? color0 : (index == 1 ? color1 : 0xFFFFFF);
    }

    private void setColor(int index, int color) {
        if (index == 0) {
            this.color0 = color;
        } else if (index == 1) {
            this.color1 = color;
        }
    }

}
