package io.github.vampirestudios.hgm.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;

public class ServerBlockEntity extends BaseDeviceBlockEntity {

    @Environment(EnvType.CLIENT)
    public float rotation;
    private boolean
            inServerRack = false,
            connected = false;

    public ServerBlockEntity() {
        super("Server", null);
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
    public NbtCompound toTag(NbtCompound compound) {
        super.toTag(compound);
        if (compound.contains("connected")) {
            this.connected = compound.getBoolean("connected");
        }
        if (compound.contains("inServerRack")) {
            this.inServerRack = compound.getBoolean("inServerRack");
        }
        return compound;
    }

    @Override
    public void fromTag(BlockState blockState, NbtCompound compound) {
        super.fromTag(blockState, compound);
        compound.putBoolean("connected", connected);
        compound.putBoolean("inServerRack", inServerRack);
    }

    @Override
    public NbtCompound writeSyncTag() {
        NbtCompound tag = new NbtCompound();
        tag.putBoolean("connected", connected);
        tag.putBoolean("inServerRack", inServerRack);
        return tag;
    }

    @Override
    public double getSquaredRenderDistance() {
        return 16384;
    }

    public boolean isInServerRack() {
        return inServerRack;
    }

    public void connectedNotConnected() {
        connected = !connected;
        pipeline.putBoolean("connected", connected);
        sync();
    }

    public boolean isConnected() {
        return connected;
    }

}