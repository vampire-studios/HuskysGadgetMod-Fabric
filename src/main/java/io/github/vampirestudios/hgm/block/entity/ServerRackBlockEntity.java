package io.github.vampirestudios.hgm.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ServerRackBlockEntity extends ModBlockEntity {

    @Environment(EnvType.CLIENT)
    public float rotation;
    private boolean hasServers = false, hasConnectedPower = false;

    public ServerRackBlockEntity(BlockEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    //TODO
    /*@Override
    public void tick() {
        if (world.isClient) {
            if (rotation > 0) {
                rotation -= 10F;
            } else if (rotation < 110) {
                rotation += 10F;
            }
        }
    }*/

    @Override
    public NbtCompound toTag(NbtCompound compound) {
        super.toTag(compound);
        if (compound.contains("hasServers")) {
            this.hasServers = compound.getBoolean("hasServers");
        }
        if (compound.contains("hasConnectedPower")) {
            this.hasConnectedPower = compound.getBoolean("hasConnectedPower");
        }
        return compound;
    }

    @Override
    public void fromTag(BlockState blockState, NbtCompound compound) {
        super.fromTag(blockState, compound);
        compound.putBoolean("hasServers", hasServers);
        compound.putBoolean("hasConnectedPower", hasConnectedPower);
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