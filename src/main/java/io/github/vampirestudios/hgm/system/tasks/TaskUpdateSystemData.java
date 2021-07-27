package io.github.vampirestudios.hgm.system.tasks;

import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.block.entity.BaseDeviceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TaskUpdateSystemData extends Task {
    private BlockPos pos;
    private NbtCompound data;

    public TaskUpdateSystemData() {
        super("update_system_data");
    }

    public TaskUpdateSystemData(BlockPos pos, NbtCompound data) {
        this();
        this.pos = pos;
        this.data = data;
    }

    @Override
    public void prepareRequest(NbtCompound tag) {
        tag.putLong("pos", pos.asLong());
        tag.put("data", this.data);
    }

    @Override
    public void processRequest(NbtCompound tag, World world, PlayerEntity player) {
        BlockPos pos = BlockPos.fromLong(tag.getLong("pos"));
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof BaseDeviceBlockEntity) {
            BaseDeviceBlockEntity laptop = (BaseDeviceBlockEntity) tileEntity;
            laptop.setSystemData(tag.getCompound("data"));
        }
        this.setSuccessful();
    }

    @Override
    public void prepareResponse(NbtCompound tag) {

    }

    @Override
    public void processResponse(NbtCompound tag) {

    }
}