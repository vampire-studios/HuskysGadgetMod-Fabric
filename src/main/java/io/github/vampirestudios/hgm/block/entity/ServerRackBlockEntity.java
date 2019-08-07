package io.github.vampirestudios.hgm.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;

public class ServerRackBlockEntity extends ModBlockEntity implements Tickable {

    @Environment(EnvType.CLIENT)
    public float rotation;
    private boolean hasServers = false, hasConnectedPower = false;

    public ServerRackBlockEntity(BlockEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        if (world.isClient) {
            if (rotation > 0) {
                rotation -= 10F;
            } else if (rotation < 110) {
                rotation += 10F;
            }
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag compound) {
        super.toTag(compound);
        if (compound.containsKey("hasServers")) {
            this.hasServers = compound.getBoolean("hasServers");
        }
        if (compound.containsKey("hasConnectedPower")) {
            this.hasConnectedPower = compound.getBoolean("hasConnectedPower");
        }
        return compound;
    }

    @Override
    public void fromTag(CompoundTag compound) {
        super.fromTag(compound);
        compound.putBoolean("hasServers", hasServers);
        compound.putBoolean("hasConnectedPower", hasConnectedPower);
    }

    @Override
    public double getSquaredRenderDistance() {
        return 16384;
    }

    public boolean hasConnectedPower() {
        return hasConnectedPower;
    }

    public void connectUnconnectPower() {
        hasConnectedPower = !hasConnectedPower;
    }

    public boolean hasServers() {
        return hasServers;
    }
}