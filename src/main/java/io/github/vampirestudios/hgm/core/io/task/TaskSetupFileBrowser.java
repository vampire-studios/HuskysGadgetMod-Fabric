package io.github.vampirestudios.hgm.core.io.task;

import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.block.entity.LaptopBlockEntity;
import io.github.vampirestudios.hgm.core.io.FileSystem;
import io.github.vampirestudios.hgm.core.io.drive.AbstractDrive;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;
import java.util.UUID;

public class TaskSetupFileBrowser extends Task {
    private BlockPos pos;
    private boolean includeMain;

    private AbstractDrive mainDrive;
    private Map<UUID, AbstractDrive> availableDrives;

    public TaskSetupFileBrowser() {
        super("get_file_system");
    }

    public TaskSetupFileBrowser(BlockPos pos, boolean includeMain) {
        this();
        this.pos = pos;
        this.includeMain = includeMain;
    }

    @Override
    public void prepareRequest(CompoundTag nbt) {
        nbt.putLong("pos", pos.asLong());
        nbt.putBoolean("include_main", includeMain);
    }

    @Override
    public void processRequest(CompoundTag nbt, World world, PlayerEntity player) {
        BlockEntity tileEntity = world.getBlockEntity(BlockPos.fromLong(nbt.getLong("pos")));
        if (tileEntity instanceof LaptopBlockEntity) {
            LaptopBlockEntity laptop = (LaptopBlockEntity) tileEntity;
            FileSystem fileSystem = laptop.getFileSystem();
            if (nbt.getBoolean("include_main")) {
                mainDrive = fileSystem.getMainDrive();
            }
            availableDrives = fileSystem.getAvailableDrives(world, false);
            this.setSuccessful();
        }
    }

    @Override
    public void prepareResponse(CompoundTag nbt) {
        if (this.isSucessful()) {
            if (mainDrive != null) {
                CompoundTag mainDriveTag = new CompoundTag();
                mainDriveTag.putString("name", mainDrive.getName());
                mainDriveTag.putString("uuid", mainDrive.getUUID().toString());
                mainDriveTag.putString("type", mainDrive.getType().toString());
                nbt.put("main_drive", mainDriveTag);
                nbt.put("structure", mainDrive.getDriveStructure().toTag());
            }

            ListTag driveList = new ListTag();
            availableDrives.forEach((k, v) -> {
                CompoundTag driveTag = new CompoundTag();
                driveTag.putString("name", v.getName());
                driveTag.putString("uuid", v.getUUID().toString());
                driveTag.putString("type", v.getType().toString());
                driveList.add(driveTag);
            });
            nbt.put("available_drives", driveList);
        }
    }

    @Override
    public void processResponse(CompoundTag nbt) {

    }

}
