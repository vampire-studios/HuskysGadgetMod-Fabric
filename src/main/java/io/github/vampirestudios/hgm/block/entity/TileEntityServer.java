package io.github.vampirestudios.hgm.block.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
        if (world.isRemote) {
            if (rotation > 0) {
                rotation -= 10F;
            } else if (rotation < 110) {
                rotation += 10F;
            }
        }
    }

    @Override
    public CompoundTag write(CompoundTag compound) {
        super.write(compound);
        if (compound.contains("connected")) {
            this.connected = compound.getBoolean("connected");
        }
        if (compound.contains("inServerRack")) {
            this.inServerRack = compound.getBoolean("inServerRack");
        }
        return compound;
    }

    @Override
    public void read(CompoundTag compound) {
        super.read(compound);
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
    public double getMaxRenderDistanceSquared() {
        return 16384;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
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