package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public abstract class ColoredDeviceBlockEntity extends SyncBlockEntity implements Tickable {

    private UUID deviceId;
    private String name;

    public ColoredDeviceBlockEntity(BlockEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public final UUID getId() {
        if (deviceId == null) {
            deviceId = UUID.randomUUID();
        }
        return deviceId;
    }

    public abstract String getDeviceName();

    public String getCustomName() {
        return hasCustomName() ? name : getDeviceName();
    }

    public void setCustomName(String name) {
        this.name = name;
    }

    public boolean hasCustomName() {
        return !StringUtils.isEmpty(name);
    }

    @Override
    public CompoundTag toTag(CompoundTag compound) {
        super.toTag(compound);
        compound.putString("deviceId", getId().toString());
        if (hasCustomName()) {
            compound.putString("name", name);
        }
        return compound;
    }

    @Override
    public void fromTag(BlockState blockState, CompoundTag compound) {
        super.fromTag(blockState, compound);
        if (compound.contains("deviceId", Constants.NBT.TAG_STRING)) {
            deviceId = UUID.fromString(compound.getString("deviceId"));
        }
        if (compound.contains("name", Constants.NBT.TAG_STRING)) {
            name = compound.getString("name");
        }
    }

    @Override
    public CompoundTag writeSyncTag() {
        CompoundTag tag = new CompoundTag();
        if (hasCustomName()) {
            tag.putString("name", name);
        }
        return tag;
    }

}