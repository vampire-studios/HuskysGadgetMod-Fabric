package io.github.vampirestudios.hgm.core.print.task;

import io.github.vampirestudios.hgm.api.print.IPrint;
import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.block.entity.NetworkDeviceBlockEntity;
import io.github.vampirestudios.hgm.block.entity.PrinterBlockEntity;
import io.github.vampirestudios.hgm.core.network.NetworkDevice;
import io.github.vampirestudios.hgm.core.network.Router;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class TaskPrint extends Task {
    private BlockPos devicePos;
    private UUID printerId;
    private IPrint print;

    public TaskPrint() {
        super("print");
    }

    public TaskPrint(BlockPos devicePos, NetworkDevice printer, IPrint print) {
        this();
        this.devicePos = devicePos;
        this.printerId = printer.getId();
        this.print = print;
    }

    @Override
    public void prepareRequest(CompoundTag nbt) {
        nbt.putLong("devicePos", devicePos.asLong());
        nbt.putUuid("printerId", printerId);
        nbt.put("print", IPrint.writeToTag(print));
    }

    @Override
    public void processRequest(CompoundTag nbt, World world, PlayerEntity player) {
        BlockEntity tileEntity = world.getBlockEntity(BlockPos.fromLong(nbt.getLong("devicePos")));
        if (tileEntity instanceof NetworkDeviceBlockEntity) {
            NetworkDeviceBlockEntity device = (NetworkDeviceBlockEntity) tileEntity;
            Router router = device.getRouter();
            if (router != null) {
                NetworkDeviceBlockEntity printer = router.getDevice(world, nbt.getUuid("printerId"));
                if (printer != null && printer instanceof PrinterBlockEntity) {
                    IPrint print = IPrint.loadFromTag(nbt.getCompound("print"));
                    ((PrinterBlockEntity) printer).addToQueue(print);
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