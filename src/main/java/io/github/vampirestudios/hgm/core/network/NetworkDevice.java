package io.github.vampirestudios.hgm.core.network;

import io.github.vampirestudios.hgm.block.entity.NetworkDeviceBlockEntity;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public class NetworkDevice {
    private Router router;
    private UUID id;
    private String name;
    private BlockPos pos;

    private NetworkDevice() {
    }

    public NetworkDevice(NetworkDeviceBlockEntity device, Router router) {
        this.router = router;
        this.id = device.getId();
        update(device);
    }

    public NetworkDevice(UUID id, String name, Router router) {
        this.id = id;
        this.name = name;
        this.router = router;
    }

    public static NetworkDevice fromTag(CompoundTag tag) {
        NetworkDevice device = new NetworkDevice();
        device.id = UUID.fromString(tag.getString("id"));
        device.name = tag.getString("name");
        if (tag.containsKey("pos", Constants.NBT.TAG_LONG)) {
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

    @Nullable
    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public boolean isConnected(World world) {
        if (pos == null)
            return false;

        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof NetworkDeviceBlockEntity) {
            NetworkDeviceBlockEntity device = (NetworkDeviceBlockEntity) tileEntity;
            Router router = device.getRouter();
            return router != null && router.getId().equals(router.getId());
        }
        return false;
    }

    public void update(NetworkDeviceBlockEntity device) {
        name = device.getDeviceName();
        pos = device.getPos();
    }

    @Nullable
    public NetworkDeviceBlockEntity getDevice(World world) {
        if (pos == null)
            return null;

        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof NetworkDeviceBlockEntity) {
            NetworkDeviceBlockEntity TileEntityNetworkDevice = (NetworkDeviceBlockEntity) tileEntity;
            if (TileEntityNetworkDevice.getId().equals(getId())) {
                return TileEntityNetworkDevice;
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