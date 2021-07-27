package io.github.vampirestudios.hgm.core.io.drive;

import io.github.vampirestudios.hgm.core.io.ServerFolder;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.nbt.NbtCompound;

public final class InternalDrive extends AbstractDrive {
    public InternalDrive(String name) {
        super(name);
    }

    public static AbstractDrive fromTag(NbtCompound driveTag) {
        AbstractDrive drive = new InternalDrive(driveTag.getString("name"));
        if (driveTag.contains("root", Constants.NBT.TAG_COMPOUND)) {
            NbtCompound folderTag = driveTag.getCompound("root");
            drive.root = ServerFolder.fromTag(folderTag.getString("file_name"), folderTag.getCompound("data"));
        }
        return drive;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound driveTag = new NbtCompound();
        driveTag.putString("name", name);

        NbtCompound folderTag = new NbtCompound();
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
