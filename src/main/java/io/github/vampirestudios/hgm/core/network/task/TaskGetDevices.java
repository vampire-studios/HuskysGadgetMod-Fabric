package io.github.vampirestudios.hgm.core.network.task;

import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.block.entity.TileEntityNetworkDevice;
import io.github.vampirestudios.hgm.core.network.NetworkDevice;
import io.github.vampirestudios.hgm.core.network.Router;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;

public class TaskGetDevices extends Task {
    private BlockPos devicePos;
    private Class<? extends TileEntityNetworkDevice> targetDeviceClass;

    private Collection<NetworkDevice> foundDevices;

    public TaskGetDevices() {
        super("get_network_devices");
    }

    public TaskGetDevices(BlockPos devicePos) {
        this();
        this.devicePos = devicePos;
    }

    public TaskGetDevices(BlockPos devicePos, Class<? extends TileEntityNetworkDevice> targetDeviceClass) {
        this();
        this.devicePos = devicePos;
        this.targetDeviceClass = targetDeviceClass;
    }

    @Override
    public void prepareRequest(CompoundTag nbt) {
        nbt.putLong("devicePos", devicePos.asLong());
        if (targetDeviceClass != null) {
            nbt.putString("targetClass", targetDeviceClass.getName());
        }
    }

    @Override
    public void processRequest(CompoundTag nbt, World world, PlayerEntity player) {
        BlockPos devicePos = BlockPos.fromLong(nbt.getLong("devicePos"));
        Class targetDeviceClass = null;
        try {
            Class targetClass = Class.forName(nbt.getString("targetClass"));
            if (TileEntityNetworkDevice.class.isAssignableFrom(targetClass)) {
                targetDeviceClass = targetClass;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        BlockEntity tileEntity = world.getBlockEntity(devicePos);
        if (tileEntity instanceof TileEntityNetworkDevice) {
            TileEntityNetworkDevice TileEntityNetworkDevice = (io.github.vampirestudios.hgm.block.entity.TileEntityNetworkDevice) tileEntity;
            if (TileEntityNetworkDevice.isConnected()) {
                Router router = TileEntityNetworkDevice.getRouter();
                if (router != null) {
                    if (targetDeviceClass != null) {
                        foundDevices = router.getConnectedDevices(world, targetDeviceClass);
                    } else {
                        foundDevices = router.getConnectedDevices(world);
                    }
                    this.setSuccessful();
                }
            }
        }
    }

    @Override
    public void prepareResponse(CompoundTag nbt) {
        if (this.isSucessful()) {
            ListTag deviceList = new ListTag();
            foundDevices.forEach(device -> deviceList.add(device.toTag(true)));
            nbt.put("network_devices", deviceList);
        }
    }

    @Override
    public void processResponse(CompoundTag nbt) {

    }
}