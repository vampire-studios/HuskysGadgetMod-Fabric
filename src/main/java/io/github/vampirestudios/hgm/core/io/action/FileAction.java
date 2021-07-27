package io.github.vampirestudios.hgm.core.io.action;

import io.github.vampirestudios.hgm.api.io.File;
import io.github.vampirestudios.hgm.api.io.Folder;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public class FileAction {
    private Type type;
    private NbtCompound data;

    private FileAction(Type type, NbtCompound data) {
        this.type = type;
        this.data = data;
    }

    public static FileAction fromTag(NbtCompound tag) {
        Type type = Type.values()[tag.getInt("type")];
        NbtCompound data = tag.getCompound("data");
        return new FileAction(type, data);
    }

    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.putInt("type", type.ordinal());
        tag.put("data", data);
        return tag;
    }

    public Type getType() {
        return type;
    }

    public NbtCompound getData() {
        return data;
    }

    public enum Type {
        NEW, DELETE, RENAME, DATA, COPY_CUT
    }

    public static class Factory {
        public static FileAction makeNew(Folder parent, File file, boolean override) {
            NbtCompound vars = new NbtCompound();
            vars.putString("directory", parent.getPath());
            vars.putString("file_name", file.getName());
            vars.putBoolean("override", override);
            vars.put("data", file.toTag());
            return new FileAction(Type.NEW, vars);
        }

        public static FileAction makeDelete(File file) {
            NbtCompound vars = new NbtCompound();
            vars.putString("directory", file.getLocation());
            vars.putString("file_name", file.getName());
            return new FileAction(Type.DELETE, vars);
        }

        public static FileAction makeRename(File file, String newFileName) {
            NbtCompound vars = new NbtCompound();
            vars.putString("directory", file.getLocation());
            vars.putString("file_name", file.getName());
            vars.putString("new_file_name", newFileName);
            return new FileAction(Type.RENAME, vars);
        }

        public static FileAction makeData(File file, NbtElement data) {
            NbtCompound vars = new NbtCompound();
            vars.putString("directory", file.getLocation());
            vars.putString("file_name", file.getName());
            vars.put("data", data);
            return new FileAction(Type.DATA, vars);
        }

        public static FileAction makeCopyCut(File source, Folder destination, boolean override, boolean cut) {
            NbtCompound vars = new NbtCompound();
            vars.putString("directory", source.getLocation());
            vars.putString("file_name", source.getName());
            vars.putString("destination_drive", destination.getDrive().getUUID().toString());
            vars.putString("destination_folder", destination.getPath());
            vars.putBoolean("override", override);
            vars.putBoolean("cut", cut);
            return new FileAction(Type.COPY_CUT, vars);
        }
    }
}
