package io.github.vampirestudios.hgm.core;

import io.github.vampirestudios.hgm.block.entity.DeviceBlockEntity;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Author: MrCrayfish
 */
public class Device {
    protected UUID id;
    protected String name;
    protected BlockPos pos;

    protected Device() {
    }

    public Device(DeviceBlockEntity device) {
        this.id = device.getId();
        update(device);
    }

    public Device(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Device fromTag(CompoundTag tag) {
        Device device = new Device();
        device.id = UUID.fromString(tag.getString("id"));
        device.name = tag.getString("name");
        if (tag.contains("pos", Constants.NBT.TAG_LONG)) {
            device.pos = BlockPos.fromLong(tag.getLong("pos"));
        }
        return device;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public void update(DeviceBlockEntity device) {
        name = device.getCustomName();
        pos = device.getPos();
    }

    public DeviceBlockEntity getDevice(World world) {
        if (pos == null)
            return null;

        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof DeviceBlockEntity) {
            DeviceBlockEntity tileEntityDevice = (DeviceBlockEntity) tileEntity;
            if (tileEntityDevice.getId().equals(getId())) {
                return tileEntityDevice;
            }
        }
        return null;
    }

    public CompoundTag toTag(boolean includePos) {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", id.toString());
        tag.putString("name", name);
        if (includePos && pos != null) {
            tag.putLong("pos", pos.asLong());
        }
        return tag;
    }
}
