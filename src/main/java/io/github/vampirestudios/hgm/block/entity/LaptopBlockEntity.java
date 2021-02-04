package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.init.HGMBlockEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundTag;

public class LaptopBlockEntity extends BaseDeviceBlockEntity {

    private static final int OPENED_ANGLE = 102;

    private boolean open = false, powered = false, hasBattery = false;

    @Environment(EnvType.CLIENT)
    private int rotation;

    @Environment(EnvType.CLIENT)
    private int prevRotation;

    public LaptopBlockEntity() {
        super("Laptop", HGMBlockEntities.LAPTOPS);
    }

    @Override
    public void tick() {
        super.tick();
        if (world.isClient) {
            prevRotation = rotation;
            if (!open) {
                if (rotation > 0) {
                    rotation -= 10F;
                }
            } else {
                if (rotation < OPENED_ANGLE) {
                    rotation += 10F;
                }
            }
            //System.out.println(this);
        }
    }

    @Override
    public void fromTag(BlockState blockState, CompoundTag compound) {
        super.fromTag(blockState, compound);
        if (compound.contains("open")) {
            this.open = compound.getBoolean("open");
        }
        if (compound.contains("powered")) {
            this.powered = compound.getBoolean("powered");
        }
        if (compound.contains("hasBattery")) {
            this.hasBattery = compound.getBoolean("hasBattery");
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag compound) {
        super.toTag(compound);
        compound.putBoolean("open", open);
        compound.putBoolean("powered", powered);
        compound.putBoolean("hasBattery", hasBattery);
        return compound;
    }

    @Override
    public CompoundTag writeSyncTag() {
        CompoundTag tag = super.writeSyncTag();
        tag.putBoolean("open", open);
        tag.putBoolean("powered", powered);
        tag.putBoolean("hasBattery", hasBattery);
        return tag;
    }

    @Override
    public double getSquaredRenderDistance() {
        return 16384;
    }

    public void openClose() {
        open = !open;
        pipeline.putBoolean("open", open);
        sync();
    }

    public void powerUnpower() {
        powered = !powered;
        pipeline.putBoolean("powered", powered);
        sync();
    }

    public void hasBatteryHasNotBattery() {
        hasBattery = !hasBattery;
        pipeline.putBoolean("hasBattery", hasBattery);
        sync();
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isPowered() {
        return powered;
    }

    public boolean hasBattery() {
        return hasBattery;
    }

    @Environment(EnvType.CLIENT)
    public float getScreenAngle(float partialTicks) {
        return -OPENED_ANGLE * ((prevRotation + (rotation - prevRotation) * partialTicks) / OPENED_ANGLE);
    }

}
