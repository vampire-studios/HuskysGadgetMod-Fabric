package io.github.vampirestudios.hgm.api.io;

import io.github.vampirestudios.hgm.api.task.Callback;
import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.api.task.TaskManager;
import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.core.io.FileSystem;
import io.github.vampirestudios.hgm.core.io.action.FileAction;
import io.github.vampirestudios.hgm.core.io.task.TaskGetFiles;
import io.github.vampirestudios.hgm.system.component.FileBrowser;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.DefaultedList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Folder extends File {
    protected List<File> files = new ArrayList<>();

    private boolean synced = false;

    /**
     * The default constructor for a folder
     *
     * @param name the name for the folder
     */
    public Folder(String name) {
        this(name, false);
    }

    private Folder(String name, boolean protect) {
        this.name = name;
        this.protect = protect;
    }

    /**
     * Converts a tag compound to a folder instance.
     *
     * @param name      the name of the folder
     * @param folderTag the tag compound from {@link #toTag()}
     * @return a folder instance
     */
    public static Folder fromTag(String name, CompoundTag folderTag) {
        Folder folder = new Folder(name);

        if (folderTag.contains("protected", Constants.NBT.TAG_BYTE))
            folder.protect = folderTag.getBoolean("protected");

        CompoundTag fileList = folderTag.getCompound("files");
        for (String fileName : fileList.getKeys()) {
            CompoundTag fileTag = fileList.getCompound(fileName);
            if (fileTag.contains("files")) {
                File file = Folder.fromTag(fileName, fileTag);
                file.parent = folder;
                folder.files.add(file);
            } else {
                File file = File.fromTag(fileName, fileTag);
                file.parent = folder;
                folder.files.add(file);
            }
        }
        return folder;
    }

    /**
     * Adds a file to the folder. The folder must be in the file system before you can add files to
     * it. If the file with the same name exists, it will not overridden. This method does not
     * verify if the file was added successfully. See {@link #add(File, Callback)} to determine if
     * it was successful or not.
     *
     * @param file the file to add
     */
    public void add(File file) {
        add(file, false, null);
    }

    /**
     * Adds a file to the folder. The folder must be in the file system before you can add files to
     * it. If the file with the same name exists, it will not overridden. This method allows the
     * specification of a {@link Callback}, and will return a
     * {@link FileSystem.Response} indicating if the file was
     * successfully added to the folder or an error occurred.
     *
     * @param file     the file to add
     * @param callback the response callback
     */
    public void add(File file, Callback<FileSystem.Response> callback) {
        add(file, false, callback);
    }

    /**
     * Adds a file to the folder. The folder must be in the file system before you can add files to
     * it. If the file with the same name exists, it can be overridden by passing true to the
     * override parameter. This method also allows the specification of a {@link Callback}, and will
     * return a {@link FileSystem.Response} indicating if the file was
     * successfully added to the folder or an error occurred.
     *
     * @param file     the file to add
     * @param override if should override existing file
     * @param callback the response callback
     */
    public void add(File file, boolean override, Callback<FileSystem.Response> callback) {
        if (!valid)
            throw new IllegalStateException("Folder must be added to the system before you can add files to it");

        if (file == null) {
            if (callback != null) {
                callback.execute(FileSystem.createResponse(FileSystem.Status.FILE_INVALID, "Illegal file"), false);
            }
            return;
        }

        if (!FileSystem.PATTERN_FILE_NAME.matcher(file.name).matches()) {
            if (callback != null) {
                callback.execute(FileSystem.createResponse(FileSystem.Status.FILE_INVALID_NAME, "Invalid file name"), true);
            }
            return;
        }

        if (hasFile(file.name)) {
            if (!override) {
                if (callback != null) {
                    callback.execute(FileSystem.createResponse(FileSystem.Status.FILE_EXISTS, "A file with that name already exists"), true);
                }
                return;
            } else if (getFile(file.name).isProtected()) {
                if (callback != null) {
                    callback.execute(FileSystem.createResponse(FileSystem.Status.FILE_IS_PROTECTED, "Unable to override protected files"), true);
                }
                return;
            }
        }

        FileSystem.sendAction(drive, FileAction.Factory.makeNew(this, file, override), (response, success) ->
        {
            if (success) {
                file.setDrive(drive);
                file.valid = true;
                file.parent = this;
                files.add(file);
                FileBrowser.refreshList = true;
            }
            if (callback != null) {
                callback.execute(response, success);
            }
        });
    }

    /**
     * Deletes the specified file name from the folder. The folder must be in the file system before
     * you can delete files from it. If the file is not found, it will just fail silently. This
     * method does not return a response if the file was deleted successfully. See
     * {@link #delete(String, Callback)} (File, Callback)} to determine if it was successful or not.
     *
     * @param name the file name
     */
    public void delete(String name) {
        delete(name, null);
    }

    /**
     * Deletes the specified file name from the folder. The folder must be in the file system before
     * you can delete files from it. This method also allows the specification of a {@link Callback}
     * , and will return a {@link FileSystem.Response} indicating if
     * the file was successfully deleted from the folder or an error occurred.
     *
     * @param name
     * @param callback
     */
    public void delete(String name, Callback<FileSystem.Response> callback) {
        delete(getFile(name), callback);
    }

    /**
     * Delete the specified file from the folder. The folder must be in the file system before
     * you can delete files from it. If the file is not in this folder, it will just fail silently.
     * This method does not return a response if the file was deleted successfully. See
     * {@link #delete(String, Callback)} (File, Callback)} to determine if it was successful or not.
     *
     * @param file a file in this folder
     */
    public void delete(File file) {
        delete(file, null);
    }

    /**
     * Delete the specified file from the folder. The folder must be in the file system before
     * you can delete files from it. The file must be in this folder, otherwise it will fail. This
     * method also allows the specification of a {@link Callback}, and will return a
     * {@link FileSystem.Response} indicating if the file was
     * successfully deleted from the folder or an error occurred.
     *
     * @param file     a file in this folder
     * @param callback the response callback
     */
    public void delete(File file, Callback<FileSystem.Response> callback) {
        if (!valid)
            throw new IllegalStateException("Folder must be added to the system before you can delete files");

        if (file == null) {
            if (callback != null) {
                callback.execute(FileSystem.createResponse(FileSystem.Status.FILE_INVALID, "Illegal file"), false);
            }
            return;
        }

        if (!files.contains(file)) {
            if (callback != null) {
                callback.execute(FileSystem.createResponse(FileSystem.Status.FILE_INVALID, "The file does not exist in this folder"), false);
            }
            return;
        }

        if (file.isProtected()) {
            if (callback != null) {
                callback.execute(FileSystem.createResponse(FileSystem.Status.FILE_IS_PROTECTED, "Cannot delete protected files"), false);
            }
            return;
        }

        FileSystem.sendAction(drive, FileAction.Factory.makeDelete(file), (response, success) ->
        {
            if (success) {
                file.drive = null;
                file.valid = false;
                file.parent = null;
                files.remove(file);
                FileBrowser.refreshList = true;
            }
            if (callback != null) {
                callback.execute(response, success);
            }
        });
    }

    public void copyInto(File file, boolean override, boolean cut, Callback<FileSystem.Response> callback) {
        if (file == null) {
            if (callback != null) {
                callback.execute(FileSystem.createResponse(FileSystem.Status.FILE_INVALID, "Illegal file"), false);
            }
            return;
        }

        if (!file.valid || file.drive == null) {
            if (callback != null) {
                callback.execute(FileSystem.createResponse(FileSystem.Status.FILE_INVALID, "Source file is invalid"), false);
            }
            return;
        }

        if (hasFile(file.name)) {
            if (!override) {
                if (callback != null) {
                    callback.execute(FileSystem.createResponse(FileSystem.Status.FILE_EXISTS, "A file with that name already exists"), true);
                }
                return;
            } else if (getFile(file.name).isProtected()) {
                if (callback != null) {
                    callback.execute(FileSystem.createResponse(FileSystem.Status.FILE_IS_PROTECTED, "Unable to override protected files"), true);
                }
                return;
            }
        }

        FileSystem.sendAction(file.drive, FileAction.Factory.makeCopyCut(file, this, false, cut), (response, success) ->
        {
            if (response.getStatus() == FileSystem.Status.SUCCESSFUL) {
                if (file.isFolder()) {
                    file.copy();
                }
            }
        });
    }

    /**
     * Checks if the folder contains a file for the specified name.
     *
     * @param name the name of the file to find
     * @return if the file exists
     */
    public boolean hasFile(String name) {
        return synced && files.stream().anyMatch(file -> file.name.equalsIgnoreCase(name));
    }

    /**
     * Gets a file from this folder for the specified name. If the file is not found, it will return
     * null.
     *
     * @param name the name of the file to get
     * @return the found file
     */
    public File getFile(String name) {
        return files.stream().filter(file -> file.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Checks if the folder contains a folder for the specified name.
     *
     * @param name the name of the folder to find
     * @return if the folder exists
     */
    public boolean hasFolder(String name) {
        return synced && files.stream().anyMatch(file -> file.isFolder() && file.name.equalsIgnoreCase(name));
    }

    /**
     * Gets a folder from this folder for the specified name. If the folder is not found, it will
     * return null.
     *
     * @param name the name of the folder to get
     * @return the found folder
     */
    public Folder getFolder(String name) {
        return (Folder) files.stream().filter(file -> file.isFolder() && file.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void getFolder(String name, Callback<Folder> callback) {
        Folder folder = getFolder(name);

        if (folder == null) {
            callback.execute(null, false);
            return;
        }

        if (!folder.isSynced()) {
            Task task = new TaskGetFiles(folder, BaseDevice.getPos());
            task.setCallback((nbt, success) ->
            {
                if (success && nbt.contains("files", Constants.NBT.TAG_LIST)) {
                    ListTag files = nbt.getList("files", Constants.NBT.TAG_COMPOUND);
                    folder.syncFiles(files);
                    callback.execute(folder, true);
                } else {
                    callback.execute(null, false);
                }
            });
            TaskManager.sendTask(task);
        } else {
            callback.execute(folder, true);
        }
    }

    /**
     * Gets all the files in the folder.
     *
     * @return a list of files
     */
    public List<File> getFiles() {
        return files;
    }

    /**
     * Allows you to search this folder for files using a specified predicate. This only searches
     * the folder itself and does not include any sub-folders. Once found, it will a list of all the
     * files that matched the predicate.
     *
     * @param conditions the conditions of the file
     * @return a list of found files
     */
    public List<File> search(Predicate<File> conditions) {
        List<File> found = DefaultedList.of();
        search(found, conditions);
        return found;
    }

    private void search(List<File> results, Predicate<File> conditions) {
        files.stream().forEach(file ->
        {
            if (conditions.test(file)) {
                results.add(file);
            }
        });
    }

    /**
     * Gets whether this file is actually folder
     *
     * @return is this file is a folder
     */
    @Override
    public boolean isFolder() {
        return true;
    }

    /**
     * Sets the data for this file. This does not work on folders and will fail silently.
     *
     * @param data the data to set
     */
    @Override
    public void setData(Tag data) {
    }

    /**
     * Sets the data for this file. This does not work on folders and will fail silently. A callback
     * can be specified but will be a guaranteed fail for folders.
     *
     * @param data     the data to set
     * @param callback the response callback
     */
    @Override
    public void setData(Tag data, Callback<FileSystem.Response> callback) {
        if (callback != null) {
            callback.execute(FileSystem.createResponse(FileSystem.Status.FAILED, "Can not set data of a folder"), false);
        }
    }

    @Override
    void setDrive(Drive drive) {
        this.drive = drive;
        files.forEach(f -> f.setDrive(drive));
    }

    /**
     * Do not use! Syncs files from the file system
     *
     * @param tagList
     */
    public void syncFiles(ListTag tagList) {
        files.removeIf(f -> !f.isFolder());
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag fileTag = tagList.getCompound(i);
            File file = File.fromTag(fileTag.getString("file_name"), fileTag.getCompound("data"));
            file.drive = drive;
            file.valid = true;
            file.parent = this;
            files.add(file);
        }
        synced = true;
    }

    /**
     * Do not use! Used for checking if folder is synced with file system
     *
     * @return is folder synced
     */
    public boolean isSynced() {
        return synced;
    }

    public void refresh() {
        synced = false;
    }

    /**
     * Do not use! Used for validating files against file system
     */
    public void validate() {
        if (!synced) {
            valid = true;
            files.forEach(f ->
            {
                if (f.isFolder()) {
                    ((Folder) f).validate();
                } else {
                    f.valid = true;
                }
            });
        }
    }

    /**
     * Converts this folder into a tag compound. Due to how the file system works, this tag does not
     * include the name of the folder and will have to be set manually for any storage.
     *
     * @return the folder tag
     */
    @Override
    public CompoundTag toTag() {
        CompoundTag folderTag = new CompoundTag();

        CompoundTag fileList = new CompoundTag();
        files.forEach(file -> fileList.put(file.getName(), file.toTag()));
        folderTag.put("files", fileList);

        if (protect) folderTag.putBoolean("protected", true);

        return folderTag;
    }

    /**
     * Returns a copy of this folder. The copied folder is considered invalid and changes to it can
     * not be made until it is added into the file system.
     *
     * @return copy of this folder
     */
    @Override
    public File copy() {
        Folder folder = new Folder(name);
        files.forEach(f -> {
            File copy = f.copy();
            copy.protect = false;
            folder.files.add(copy);
        });
        return folder;
    }

    /**
     * Returns a copy of this folder with a different name. The copied folder is considered invalid
     * and changes to it can not be made until it is added into the file system.
     *
     * @param newName the new name for the folder
     * @return copy of this folder
     */
    @Override
    public File copy(String newName) {
        Folder folder = new Folder(newName);
        files.forEach(f -> {
            File copy = f.copy();
            copy.protect = false;
            folder.files.add(copy);
        });
        return folder;
    }
}
