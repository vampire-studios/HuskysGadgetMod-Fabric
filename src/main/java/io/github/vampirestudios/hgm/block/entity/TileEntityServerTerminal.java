package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.utils.Constants;
import io.github.vampirestudios.hgm.utils.IColored;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DyeColor;

public class TileEntityServerTerminal extends TileEntitySync implements IColored {

    private DyeColor color = DyeColor.WHITE;

    private byte rotation;

    public TileEntityServerTerminal() {
        super(null);
    }

    public void nextRotation() {
        rotation++;
        if (rotation > 7) {
            rotation = 0;
        }
        pipeline.putByte("rotation", rotation);
        sync();
    }

    public float getRotation() {
        return rotation * 45F;
    }

    @Override
    public void fromTag(CompoundTag compound) {
        super.fromTag(compound);
        if (compound.containsKey("rotation", Constants.NBT.TAG_BYTE)) {
            rotation = compound.getByte("rotation");
        }
        if (compound.containsKey("color", Constants.NBT.TAG_BYTE)) {
            this.color = DyeColor.byId(compound.getByte("color"));
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag compound) {
        super.toTag(compound);
        compound.putByte("rotation", rotation);
        compound.putByte("color", (byte) color.getId());
        return compound;
    }

    @Override
    public CompoundTag writeSyncTag() {
        CompoundTag tag = new CompoundTag();
        tag.putByte("color", (byte) color.getId());
        return tag;
    }

    @Override
    public DyeColor getColor() {
        return color;
    }

    @Override
    public void setColor(DyeColor color) {
        this.color = color;
    }

}