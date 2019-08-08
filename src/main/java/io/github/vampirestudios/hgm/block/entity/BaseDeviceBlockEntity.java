package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.core.BaseDevice;
import io.github.vampirestudios.hgm.core.io.FileSystem;
import io.github.vampirestudios.hgm.utils.Constants;
import io.github.vampirestudios.hgm.utils.TileEntityUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DyeColor;

public class BaseDeviceBlockEntity extends NetworkDeviceBlockEntity.Colored {

    @Environment(EnvType.CLIENT)
    public float rotation;
    @Environment(EnvType.CLIENT)
    public float prevRotation;
    private String deviceName;
    private boolean powered = false, powerConnected = false, wifiConnected = false;
    private CompoundTag applicationData;
    private CompoundTag systemData;
    private FileSystem fileSystem;
    @Environment(EnvType.CLIENT)
    private boolean hasExternalDrive;

    @Environment(EnvType.CLIENT)
    private DyeColor externalDriveColor;

    public BaseDeviceBlockEntity() {
        super(null);
        this.deviceName = "Base";
    }

    public BaseDeviceBlockEntity(String deviceName, BlockEntityType<?> tileEntityType) {
        super(tileEntityType);
        this.deviceName = deviceName;
    }

    @Override
    public String getDeviceName() {
        return deviceName;
    }

    @Override
    public void tick() {
        if (world.isClient) {
            prevRotation = rotation;
            if (rotation > 0) {
                rotation -= 10F;
            } else {
                if (rotation < 110) {
                    rotation += 10F;
                }
            }
        }
        if (this.systemData != null && this.systemData.containsKey("boottimer") && this.systemData.containsKey("bootmode")) {
            BaseDevice.BootMode bootmode = BaseDevice.BootMode.getBootMode(this.systemData.getInt("bootmode"));
            if (bootmode != null && bootmode != BaseDevice.BootMode.NOTHING) {
                int boottimer = Math.max(this.systemData.getInt("boottimer") - 1, 0);
                if (boottimer == 0) {
                    bootmode = bootmode == BaseDevice.BootMode.BOOTING ? BaseDevice.BootMode.NOTHING : null;
                    this.systemData.putInt("bootmode", BaseDevice.BootMode.ordinal(bootmode));
                }
                this.systemData.putInt("boottimer", boottimer);
            }
        }
    }

    @Override
    public void fromTag(CompoundTag compound) {
        super.fromTag(compound);
        if (compound.containsKey("device_name", Constants.NBT.TAG_STRING)) {
            this.deviceName = compound.getString("device_name");
        }
        if (compound.containsKey("system_data", Constants.NBT.TAG_COMPOUND)) {
            this.systemData = compound.getCompound("system_data");
        }
        if (compound.containsKey("application_data", Constants.NBT.TAG_COMPOUND)) {
            this.applicationData = compound.getCompound("application_data");
        }
        if (compound.containsKey("has_external_drive")) {
            this.hasExternalDrive = compound.getBoolean("has_external_drive");
        }
        if (compound.containsKey("powered")) {
            this.powered = compound.getBoolean("powered");
        }
        if (compound.containsKey("powerConnected")) {
            this.powerConnected = compound.getBoolean("powerConnected");
        }
        if (compound.containsKey("wifiConnected")) {
            this.wifiConnected = compound.getBoolean("wifiConnected");
        }
        if (compound.containsKey("file_system")) {
            this.fileSystem = new FileSystem(this, compound.getCompound("file_system"));
        }
        if (compound.containsKey("external_drive_color", Constants.NBT.TAG_BYTE)) {
            this.externalDriveColor = null;
            if (compound.getByte("external_drive_color") != -1) {
                this.externalDriveColor = DyeColor.byId(compound.getByte("external_drive_color"));
            }
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag compound) {
        super.toTag(compound);
        compound.putString("device_name", deviceName);

        if (systemData != null) {
            compound.put("system_data", systemData);
        }

        if (applicationData != null) {
            compound.put("application_data", applicationData);
        }

        if (fileSystem != null) {
            compound.put("file_system", fileSystem.toTag());
        }
        compound.putBoolean("powered", powered);
        compound.putBoolean("wifiConnected", wifiConnected);
        compound.putBoolean("powerConnected", powerConnected);
        return compound;
    }

    @Override
    public CompoundTag writeSyncTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("device_name", deviceName);
        tag.putBoolean("powered", powered);
        tag.putBoolean("wifiConnected", wifiConnected);
        tag.putBoolean("powerConnected", powerConnected);
        tag.put("system_data", getSystemData());
        tag.put("application_data", getApplicationData());

        if (getFileSystem().getAttachedDrive() != null) {
            tag.putByte("external_drive_color", (byte) getFileSystem().getAttachedDriveColor().getId());
        } else {
            tag.putByte("external_drive_color", (byte) -1);
        }
        return tag;
    }

    @Override
    public double getSquaredRenderDistance() {
        return 16384;
    }

    public void powerUnpower() {
        powered = !powered;
        pipeline.putBoolean("powered", powered);
        sync();
    }

    public boolean isPowered() {
        return powered;
    }

    public boolean isPowerConnected() {
        return powerConnected;
    }

    public boolean isWifiConnected() {
        return wifiConnected;
    }

    public void wifiOnlineOffline() {
        wifiConnected = !wifiConnected;
        pipeline.putBoolean("wifiConnected", wifiConnected);
        sync();
    }

    public void powerOnOff() {
        powerConnected = !powerConnected;
        pipeline.putBoolean("powerConnected", powerConnected);
        sync();
    }

    public CompoundTag getApplicationData() {
        return applicationData != null ? applicationData : new CompoundTag();
    }

    public CompoundTag getSystemData() {
        return systemData != null ? systemData : new CompoundTag();
    }

    public void setSystemData(CompoundTag systemData) {
        this.systemData = systemData;
        markDirty();
        TileEntityUtil.markBlockForUpdate(world, pos);
    }

    public DyeColor getExternalDriveColor() {
        return externalDriveColor;
    }

    public FileSystem getFileSystem() {
        return fileSystem != null ? fileSystem : new FileSystem(this, new CompoundTag());
    }

    public void setApplicationData(String appId, CompoundTag applicationData) {
        this.applicationData.put(appId, applicationData);
        markDirty();
        TileEntityUtil.markBlockForUpdate(world, pos);
    }

    public boolean isExternalDriveAttached() {
        return hasExternalDrive;
    }

}
