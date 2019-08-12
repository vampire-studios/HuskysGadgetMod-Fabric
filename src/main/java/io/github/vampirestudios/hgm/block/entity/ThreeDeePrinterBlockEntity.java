package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.init.HGMBlockEntities;
import io.github.vampirestudios.hgm.utils.Constants;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;

public class ThreeDeePrinterBlockEntity extends ColoredDeviceBlockEntity {

    @Environment(EnvType.CLIENT)
    public float rotation;
    private String name = "3D Printer";
    private boolean powered = false;
    @Environment(EnvType.CLIENT)
    private float prevRotation;

    public ThreeDeePrinterBlockEntity() {
        super(HGMBlockEntities.THREE_DEE_PRINTER);
    }

    @Override
    public String getDeviceName() {
        return name;
    }

    @Override
    public void tick() {
        if (world.isClient) {
            prevRotation = rotation;
            if (!powered) {
                if (rotation > 0) {
                    rotation -= 10F;
                }
            } else {
                if (rotation < 110) {
                    rotation += 10F;
                }
            }
        }
    }

    @Override
    public void fromTag(CompoundTag compound) {
        super.fromTag(compound);
        if (compound.containsKey("powered")) {
            this.powered = compound.getBoolean("powered");
        }
        if (compound.containsKey("device_name", Constants.NBT.TAG_STRING)) {
            this.name = compound.getString("device_name");
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag compound) {
        super.toTag(compound);
        compound.putBoolean("powered", powered);
        compound.putString("device_name", name);

        return compound;
    }

    @Override
    public CompoundTag writeSyncTag() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("powered", powered);
        tag.putString("device_name", name);
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

}
