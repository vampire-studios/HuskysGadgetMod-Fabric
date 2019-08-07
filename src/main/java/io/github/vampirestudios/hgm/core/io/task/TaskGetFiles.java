package io.github.vampirestudios.hgm.core.io.task;

import io.github.vampirestudios.hgm.api.io.Folder;
import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.block.entity.LaptopBlockEntity;
import io.github.vampirestudios.hgm.core.io.FileSystem;
import io.github.vampirestudios.hgm.core.io.ServerFile;
import io.github.vampirestudios.hgm.core.io.ServerFolder;
import io.github.vampirestudios.hgm.core.io.drive.AbstractDrive;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TaskGetFiles extends Task {
    private String uuid;
    private String path;
    private BlockPos pos;

    private List<ServerFile> files;

    private TaskGetFiles() {
        super("get_files");
    }

    public TaskGetFiles(Folder folder, BlockPos pos) {
        this();
        this.uuid = folder.getDrive().getUUID().toString();
        this.path = folder.getPath();
        this.pos = pos;
    }

    protected static String compileDirectory(ServerFile file) {
        if (file.getParent() == null || file.getParent().getParent() == null)
            return "/";

        StringBuilder builder = new StringBuilder();
        ServerFolder parent = file.getParent();
        while (parent != null) {
            builder.insert(0, "/" + parent.getName());
            if (parent.getParent() != null) {
                return builder.toString();
            }
            parent = parent.getParent();
        }
        return builder.toString();
    }

    @Override
    public void prepareRequest(CompoundTag nbt) {
        nbt.putString("uuid", uuid);
        nbt.putString("path", path);
        nbt.putLong("pos", pos.asLong());
    }

    @Override
    public void processRequest(CompoundTag nbt, World world, PlayerEntity player) {
        BlockEntity tileEntity = world.getBlockEntity(BlockPos.fromLong(nbt.getLong("pos")));
        if (tileEntity instanceof LaptopBlockEntity) {
            LaptopBlockEntity laptop = (LaptopBlockEntity) tileEntity;
            FileSystem fileSystem = laptop.getFileSystem();
            UUID uuid = UUID.fromString(nbt.getString("uuid"));
            AbstractDrive serverDrive = fileSystem.getAvailableDrives(world, true).get(uuid);
            if (serverDrive != null) {
                ServerFolder found = serverDrive.getFolder(nbt.getString("path"));
                if (found != null) {
                    this.files = found.getFiles().stream().filter(f -> !f.isFolder()).collect(Collectors.toList());
                    this.setSuccessful();
                }
            }
        }
    }

    @Override
    public void prepareResponse(CompoundTag nbt) {
        if (this.files != null) {
            ListTag list = new ListTag();
            this.files.forEach(f -> {
                CompoundTag fileTag = new CompoundTag();
                fileTag.putString("file_name", f.getName());
                fileTag.put("data", f.toTag());
                list.add(fileTag);
            });
            nbt.put("files", list);
        }
    }

    @Override
    public void processResponse(CompoundTag nbt) {

    }

}
