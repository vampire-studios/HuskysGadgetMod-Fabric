package io.github.vampirestudios.hgm.core.network.task;

import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.block.entity.NetworkDeviceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TaskPing extends Task {
    private BlockPos sourceDevicePos;
    private int strength;

    public TaskPing() {
        super("ping");
    }

    public TaskPing(BlockPos sourceDevicePos) {
        this();
        this.sourceDevicePos = sourceDevicePos;
    }

    @Override
    public void prepareRequest(CompoundTag nbt) {
        nbt.putLong("sourceDevicePos", sourceDevicePos.asLong());
    }

    @Override
    public void processRequest(CompoundTag nbt, World world, PlayerEntity player) {
        BlockEntity tileEntity = world.getBlockEntity(BlockPos.fromLong(nbt.getLong("sourceDevicePos")));
        if (tileEntity instanceof NetworkDeviceBlockEntity) {
            NetworkDeviceBlockEntity TileEntityNetworkDevice = (NetworkDeviceBlockEntity) tileEntity;
            if (TileEntityNetworkDevice.isConnected()) {
                this.strength = TileEntityNetworkDevice.getSignalStrength();
                this.setSuccessful();
            }
        }
    }

    @Override
    public void prepareResponse(CompoundTag nbt) {
        if (this.isSucessful()) {
            nbt.putLong("strength", strength);
        }
    }

    @Override
    public void processResponse(CompoundTag nbt) {

    }

}