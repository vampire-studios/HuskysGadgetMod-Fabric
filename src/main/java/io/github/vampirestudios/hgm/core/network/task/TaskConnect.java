package io.github.vampirestudios.hgm.core.network.task;

import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.block.entity.NetworkDeviceBlockEntity;
import io.github.vampirestudios.hgm.block.entity.RouterBlockEntity;
import io.github.vampirestudios.hgm.core.network.Router;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TaskConnect extends Task {
    private BlockPos devicePos;
    private BlockPos routerPos;

    public TaskConnect() {
        super("connect");
    }

    public TaskConnect(BlockPos devicePos, BlockPos routerPos) {
        this();
        this.devicePos = devicePos;
        this.routerPos = routerPos;
    }

    @Override
    public void prepareRequest(NbtCompound nbt) {
        nbt.putLong("devicePos", devicePos.asLong());
        nbt.putLong("routerPos", routerPos.asLong());
    }

    @Override
    public void processRequest(NbtCompound nbt, World world, PlayerEntity player) {
        BlockEntity tileEntity = world.getBlockEntity(BlockPos.fromLong(nbt.getLong("routerPos")));
        if (tileEntity instanceof RouterBlockEntity) {
            RouterBlockEntity tileEntityRouter = (RouterBlockEntity) tileEntity;
            Router router = tileEntityRouter.getRouter();

            BlockEntity tileEntity1 = world.getBlockEntity(BlockPos.fromLong(nbt.getLong("devicePos")));
            if (tileEntity1 instanceof NetworkDeviceBlockEntity) {
                NetworkDeviceBlockEntity TileEntityNetworkDevice = (NetworkDeviceBlockEntity) tileEntity1;
                if (router.addDevice(TileEntityNetworkDevice)) {
                    TileEntityNetworkDevice.connect(router);
                    this.setSuccessful();
                }
            }
        }
    }

    @Override
    public void prepareResponse(NbtCompound nbt) {

    }

    @Override
    public void processResponse(NbtCompound nbt) {

    }
}