package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.api.print.IPrint;
import io.github.vampirestudios.hgm.init.GadgetTileEntities;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import javax.annotation.Nullable;

public class TileEntityPaper extends TileEntitySync {

    private IPrint print;
    private byte rotation;

    public TileEntityPaper() {
        super(GadgetTileEntities.PAPER.build(null));
    }

    public void nextRotation() {
        rotation++;
        if (rotation > 7) {
            rotation = 0;
        }
        pipeline.putByte("rotation", rotation);
        sync();
        playSound(SoundEvents.ENTITY_ITEM_FRAME_ROTATE_ITEM);
    }

    public float getRotation() {
        return rotation * 45F;
    }

    @Nullable
    public IPrint getPrint() {
        return print;
    }

    @Override
    public void fromTag(CompoundTag compound) {
        super.fromTag(compound);
        if (compound.containsKey("print", Constants.NBT.TAG_COMPOUND)) {
            print = IPrint.loadFromTag(compound.getCompound("print"));
        }
        if (compound.containsKey("rotation", Constants.NBT.TAG_BYTE)) {
            rotation = compound.getByte("rotation");
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag compound) {
        super.toTag(compound);
        if (print != null) {
            compound.put("print", IPrint.writeToTag(print));
        }
        compound.putByte("rotation", rotation);
        return compound;
    }

    @Override
    public CompoundTag writeSyncTag() {
        CompoundTag tag = new CompoundTag();
        if (print != null) {
            tag.put("print", IPrint.writeToTag(print));
        }
        tag.putByte("rotation", rotation);
        return tag;
    }

    private void playSound(SoundEvent sound) {
        world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }
}