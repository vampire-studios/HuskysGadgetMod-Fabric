package io.github.vampirestudios.hgm.core.io.drive;

import io.github.vampirestudios.hgm.core.io.ServerFolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public final class InternalDrive extends AbstractDrive {
    public InternalDrive(String name) {
        super(name);
    }

    @Nullable
    public static AbstractDrive fromTag(CompoundTag driveTag) {
        AbstractDrive drive = new InternalDrive(driveTag.getString("name"));
        if (driveTag.contains("root", Constants.NBT.TAG_COMPOUND)) {
            CompoundTag folderTag = driveTag.getCompound("root");
            drive.root = ServerFolder.fromTag(folderTag.getString("file_name"), folderTag.getCompound("data"));
        }
        return drive;
    }

    @Override
    public CompoundTag toTag() {
        CompoundTag driveTag = new CompoundTag();
        driveTag.putString("name", name);

        CompoundTag folderTag = new CompoundTag();
        folderTag.putString("file_name", root.getName());
        folderTag.put("data", root.toTag());
        driveTag.put("root", folderTag);

        return driveTag;
    }

    @Override
    public Type getType() {
        return Type.INTERNAL;
    }
}
