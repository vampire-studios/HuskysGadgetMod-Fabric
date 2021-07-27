package io.github.vampirestudios.hgm.core.io.task;

import io.github.vampirestudios.hgm.api.io.Drive;
import io.github.vampirestudios.hgm.api.io.Folder;
import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.block.entity.BaseDeviceBlockEntity;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.core.io.FileSystem;
import io.github.vampirestudios.hgm.core.io.drive.AbstractDrive;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TaskGetMainDrive extends Task {
    private BlockPos pos;

    private AbstractDrive mainDrive;

    private TaskGetMainDrive() {
        super("get_main_drive");
    }

    public TaskGetMainDrive(BlockPos pos) {
        this();
        this.pos = pos;
    }

    @Override
    public void prepareRequest(NbtCompound nbt) {
        nbt.putLong("pos", pos.asLong());
    }

    @Override
    public void processRequest(NbtCompound nbt, World world, PlayerEntity player) {
        BlockEntity tileEntity = world.getBlockEntity(BlockPos.fromLong(nbt.getLong("pos")));
        if (tileEntity instanceof BaseDeviceBlockEntity) {
            BaseDeviceBlockEntity laptop = (BaseDeviceBlockEntity) tileEntity;
            FileSystem fileSystem = laptop.getFileSystem();
            mainDrive = fileSystem.getMainDrive();
            this.setSuccessful();
        }
    }

    @Override
    public void prepareResponse(NbtCompound nbt) {
        if (this.isSucessful()) {
            NbtCompound mainDriveTag = new NbtCompound();
            mainDriveTag.putString("name", mainDrive.getName());
            mainDriveTag.putString("uuid", mainDrive.getUUID().toString());
            mainDriveTag.putString("type", mainDrive.getType().toString());
            nbt.put("main_drive", mainDriveTag);
            nbt.put("structure", mainDrive.getDriveStructure().toTag());
        }
    }

    @Override
    public void processResponse(NbtCompound nbt) {
        if (this.isSucessful()) {
            if (MinecraftClient.getInstance().currentScreen instanceof BaseDevice) {
                NbtCompound structureTag = nbt.getCompound("structure");
                Drive drive = new Drive(nbt.getCompound("main_drive"));
                drive.syncRoot(Folder.fromTag(FileSystem.LAPTOP_DRIVE_NAME, structureTag));
                drive.getRoot().validate();

                if (BaseDevice.getMainDrive() == null) {
                    BaseDevice.setMainDrive(drive);
                }
            }
        }
    }
}
