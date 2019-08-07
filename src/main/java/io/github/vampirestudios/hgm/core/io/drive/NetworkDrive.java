package io.github.vampirestudios.hgm.core.io.drive;

import io.github.vampirestudios.hgm.core.io.FileSystem;
import io.github.vampirestudios.hgm.core.io.ServerFolder;
import io.github.vampirestudios.hgm.core.io.action.FileAction;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public final class NetworkDrive extends AbstractDrive {

    private static final Predicate<CompoundTag> PREDICATE_DRIVE_TAG = tag ->
            tag.containsKey("name", Constants.NBT.TAG_STRING)
                    && tag.containsKey("uuid", Constants.NBT.TAG_STRING)
                    && tag.containsKey("root", Constants.NBT.TAG_COMPOUND);

    private BlockPos pos;

    private NetworkDrive() {
    }

    public NetworkDrive(String name, BlockPos pos) {
        super(name);
        this.pos = pos;
        this.root = null;
    }

    @Nullable
    public static AbstractDrive fromTag(CompoundTag driveTag) {
        if (!PREDICATE_DRIVE_TAG.test(driveTag))
            return null;

        AbstractDrive drive = new NetworkDrive();
        drive.name = driveTag.getString("name");
        drive.uuid = UUID.fromString(driveTag.getString("uuid"));

        CompoundTag folderTag = driveTag.getCompound("root");
        drive.root = ServerFolder.fromTag(folderTag.getString("file_name"), folderTag.getCompound("data"));

        return drive;
    }

    @Nullable
    @Override
    public ServerFolder getRoot(World world) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof Interface) {
            Interface impl = (Interface) tileEntity;
            AbstractDrive drive = impl.getDrive();
            if (drive != null) {
                return drive.getRoot(world);
            }
        }
        return null;
    }

    @Override
    public FileSystem.Response handleFileAction(FileSystem fileSystem, FileAction action, World world) {
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof Interface) {
            Interface impl = (Interface) tileEntity;
            AbstractDrive drive = impl.getDrive();
            if (drive.handleFileAction(fileSystem, action, world).getStatus() == FileSystem.Status.SUCCESSFUL) {
                tileEntity.markDirty();
                return FileSystem.createSuccessResponse();
            }
        }
        return FileSystem.createResponse(FileSystem.Status.DRIVE_UNAVAILABLE, "The network drive could not be found");
    }

    @Nullable
    @Override
    public ServerFolder getFolder(String path) {
        return null;
    }

    @Override
    public CompoundTag toTag() {
        CompoundTag driveTag = new CompoundTag();
        driveTag.putString("name", name);
        driveTag.putString("uuid", uuid.toString());

        CompoundTag folderTag = new CompoundTag();
        folderTag.putString("file_name", root.getName());
        folderTag.put("data", root.toTag());
        driveTag.put("root", folderTag);

        return driveTag;
    }

    @Override
    public Type getType() {
        return Type.NETWORK;
    }

    public interface Interface {
        AbstractDrive getDrive();

        boolean canAccessDrive();
    }
}
