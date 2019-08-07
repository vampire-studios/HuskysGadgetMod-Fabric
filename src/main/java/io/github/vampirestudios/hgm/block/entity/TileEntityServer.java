package io.github.vampirestudios.hgm.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;

public class TileEntityServer extends TileEntityBaseDevice {

    @Environment(EnvType.CLIENT)
    public float rotation;
    private boolean
            inServerRack = false,
            connected = false;

    public TileEntityServer() {
        super("Server", false, null);
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
        if (compound.containsKey("connected")) {
            this.connected = compound.getBoolean("connected");
        }
        if (compound.containsKey("inServerRack")) {
            this.inServerRack = compound.getBoolean("inServerRack");
        }
        return compound;
    }

    @Override
    public void fromTag(CompoundTag compound) {
        super.fromTag(compound);
        compound.putBoolean("connected", connected);
        compound.putBoolean("inServerRack", inServerRack);
    }

    @Override
    public CompoundTag writeSyncTag() {
        CompoundTag tag = new CompoundTag();
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