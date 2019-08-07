package io.github.vampirestudios.hgm.core.tasks;

import io.github.vampirestudios.hgm.api.AppInfo;
import io.github.vampirestudios.hgm.api.task.Task;
import io.github.vampirestudios.hgm.block.entity.BaseDeviceBlockEntity;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author: MrCrayfish
 */
public class TaskInstallApp extends Task {
    private String appId;
    private BlockPos laptopPos;
    private boolean install;

    private TaskInstallApp() {
        super("install_app");
    }

    public TaskInstallApp(AppInfo info, BlockPos laptopPos, boolean install) {
        this();
        this.appId = info.getFormattedId();
        this.laptopPos = laptopPos;
        this.install = install;
    }

    @Override
    public void prepareRequest(CompoundTag nbt) {
        nbt.putString("appId", appId);
        nbt.putLong("pos", laptopPos.asLong());
        nbt.putBoolean("install", install);
    }

    @Override
    public void processRequest(CompoundTag nbt, World world, PlayerEntity player) {
        String appId = nbt.getString("appId");
        BlockEntity tileEntity = world.getBlockEntity(BlockPos.fromLong(nbt.getLong("pos")));
        if (tileEntity instanceof BaseDeviceBlockEntity) {
            BaseDeviceBlockEntity laptop = (BaseDeviceBlockEntity) tileEntity;
            CompoundTag systemData = laptop.getSystemData();
            ListTag tagList = systemData.getList("InstalledApps", Constants.NBT.TAG_STRING);

            System.out.println("Before the task: ");
            for (int i = 0; i < tagList.size(); i++) {
                System.out.println("\t- " + tagList.getString(i));
            }

            if (nbt.getBoolean("install")) {
                for (int i = 0; i < tagList.size(); i++) {
                    if (tagList.getString(i).equals(appId)) {
                        return;
                    }
                }
                tagList.add(new StringTag(appId));
                this.setSuccessful();
            } else {
                for (int i = 0; i < tagList.size(); i++) {
                    if (tagList.getString(i).equals(appId)) {
                        tagList.remove(i);
                        this.setSuccessful();
                    }
                }
            }
            systemData.put("InstalledApps", tagList);

            System.out.println("After the task: ");
            for (int i = 0; i < tagList.size(); i++) {
                System.out.println("\t- " + tagList.getString(i));
            }
        }
    }

    @Override
    public void prepareResponse(CompoundTag nbt) {

    }

    @Override
    public void processResponse(CompoundTag nbt) {

    }
}