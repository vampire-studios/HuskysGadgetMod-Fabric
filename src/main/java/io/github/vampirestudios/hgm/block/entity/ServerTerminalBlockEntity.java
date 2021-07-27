package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.utils.Constants;
import io.github.vampirestudios.hgm.utils.IColored;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;

public class ServerTerminalBlockEntity extends SyncBlockEntity implements IColored {

    private DyeColor color = DyeColor.WHITE;

    private byte rotation;

    public ServerTerminalBlockEntity() {
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
    public void fromTag(BlockState blockState, NbtCompound compound) {
        super.fromTag(blockState, compound);
        if (compound.contains("rotation", Constants.NBT.TAG_BYTE)) {
            rotation = compound.getByte("rotation");
        }
        if (compound.contains("color", Constants.NBT.TAG_BYTE)) {
            this.color = DyeColor.byId(compound.getByte("color"));
        }
    }

    @Override
    public NbtCompound toTag(NbtCompound compound) {
        super.toTag(compound);
        compound.putByte("rotation", rotation);
        compound.putByte("color", (byte) color.getId());
        return compound;
    }

    @Override
    public NbtCompound writeSyncTag() {
        NbtCompound tag = new NbtCompound();
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