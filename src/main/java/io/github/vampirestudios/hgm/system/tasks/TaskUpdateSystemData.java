package io.github.vampirestudios.hgm.system.tasks;

import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.block.entity.TileEntityBaseDevice;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TaskUpdateSystemData extends Task {
    private BlockPos pos;
    private CompoundTag data;

    public TaskUpdateSystemData() {
        super("update_system_data");
    }

    public TaskUpdateSystemData(BlockPos pos, CompoundTag data) {
        this();
        this.pos = pos;
        this.data = data;
    }

    @Override
    public void prepareRequest(CompoundTag tag) {
        tag.putLong("pos", pos.toLong());
        tag.put("data", this.data);
    }

    @Override
    public void processRequest(CompoundTag tag, World world, PlayerEntity player) {
        BlockPos pos = BlockPos.fromLong(tag.getLong("pos"));
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntityBaseDevice) {
            TileEntityBaseDevice laptop = (TileEntityBaseDevice) tileEntity;
            laptop.setSystemData(tag.getCompound("data"));
        }
        this.setSuccessful();
    }

    @Override
    public void prepareResponse(CompoundTag tag) {

    }

    @Override
    public void processResponse(CompoundTag tag) {

    }
}