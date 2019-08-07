package io.github.vampirestudios.hgm.system.tasks;

import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.block.entity.TileEntityLaptop;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TaskUpdateApplicationData extends Task {
    private BlockPos pos;
    private String appId;
    private CompoundTag data;

    public TaskUpdateApplicationData() {
        super("update_application_data");
    }

    public TaskUpdateApplicationData(BlockPos pos, String appId, CompoundTag data) {
        this();
        this.pos = pos;
        this.appId = appId;
        this.data = data;
    }

    @Override
    public void prepareRequest(CompoundTag tag) {
        tag.putLong("pos", this.pos.asLong());
        tag.putString("appId", this.appId);
        tag.put("appData", this.data);
    }

    @Override
    public void processRequest(CompoundTag tag, World world, PlayerEntity player) {
        BlockPos pos = BlockPos.fromLong(tag.getLong("pos"));
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof TileEntityLaptop) {
            TileEntityLaptop laptop = (TileEntityLaptop) tileEntity;
            laptop.setApplicationData(tag.getString("appId"), tag.getCompound("appData"));
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
