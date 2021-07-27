package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.api.print.IPrint;
import io.github.vampirestudios.hgm.init.HGMBlockEntities;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class PaperBlockEntity extends SyncBlockEntity {

    private IPrint print;
    private byte rotation;

    public PaperBlockEntity() {
        super(HGMBlockEntities.PAPER);
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

    public IPrint getPrint() {
        return print;
    }

    @Override
    public void fromTag(BlockState state, NbtCompound tag) {
        super.fromTag(state, tag);
        if (tag.contains("print", Constants.NBT.TAG_COMPOUND)) {
            print = IPrint.loadFromTag(tag.getCompound("print"));
        }
        if (tag.contains("rotation", Constants.NBT.TAG_BYTE)) {
            rotation = tag.getByte("rotation");
        }
    }

    @Override
    public NbtCompound toTag(NbtCompound compound) {
        super.toTag(compound);
        if (print != null) {
            compound.put("print", IPrint.writeToTag(print));
        }
        compound.putByte("rotation", rotation);
        return compound;
    }

    @Override
    public NbtCompound writeSyncTag() {
        NbtCompound tag = new NbtCompound();
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