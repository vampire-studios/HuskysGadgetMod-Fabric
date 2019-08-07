package io.github.vampirestudios.hgm.core.network.task;

import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.block.entity.TileEntityNetworkDevice;
import io.github.vampirestudios.hgm.block.entity.TileEntityRouter;
import io.github.vampirestudios.hgm.core.network.Router;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
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
    public void prepareRequest(CompoundTag nbt) {
        nbt.putLong("devicePos", devicePos.asLong());
        nbt.putLong("routerPos", routerPos.asLong());
    }

    @Override
    public void processRequest(CompoundTag nbt, World world, PlayerEntity player) {
        BlockEntity tileEntity = world.getBlockEntity(BlockPos.fromLong(nbt.getLong("routerPos")));
        if (tileEntity instanceof TileEntityRouter) {
            TileEntityRouter tileEntityRouter = (TileEntityRouter) tileEntity;
            Router router = tileEntityRouter.getRouter();

            BlockEntity tileEntity1 = world.getBlockEntity(BlockPos.fromLong(nbt.getLong("devicePos")));
            if (tileEntity1 instanceof TileEntityNetworkDevice) {
                TileEntityNetworkDevice TileEntityNetworkDevice = (TileEntityNetworkDevice) tileEntity1;
                if (router.addDevice(TileEntityNetworkDevice)) {
                    TileEntityNetworkDevice.connect(router);
                    this.setSuccessful();
                }
            }
        }
    }

    @Override
    public void prepareResponse(CompoundTag nbt) {

    }

    @Override
    public void processResponse(CompoundTag nbt) {

    }
}