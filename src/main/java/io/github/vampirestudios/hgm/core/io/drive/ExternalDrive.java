package io.github.vampirestudios.hgm.core.io.drive;

import io.github.vampirestudios.hgm.core.io.ServerFolder;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public final class ExternalDrive extends AbstractDrive {
    private static final Predicate<CompoundTag> PREDICATE_DRIVE_TAG = tag ->
            tag.contains("name", Constants.NBT.TAG_STRING)
                    && tag.contains("uuid", Constants.NBT.TAG_STRING)
                    && tag.contains("root", Constants.NBT.TAG_COMPOUND);

    private ExternalDrive() {
    }

    public ExternalDrive(String displayName) {
        super(displayName);
    }

    @Nullable
    public static AbstractDrive fromTag(CompoundTag driveTag) {
        if (!PREDICATE_DRIVE_TAG.test(driveTag))
            return null;

        AbstractDrive drive = new ExternalDrive();
        drive.name = driveTag.getString("name");
        drive.uuid = UUID.fromString(driveTag.getString("uuid"));

        CompoundTag folderTag = driveTag.getCompound("root");
        drive.root = ServerFolder.fromTag(folderTag.getString("file_name"), folderTag.getCompound("data"));

        return drive;
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
        return Type.EXTERNAL;
    }
}
