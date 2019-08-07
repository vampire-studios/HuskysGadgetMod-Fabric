package io.github.vampirestudios.hgm.block.entity;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.core.network.Connection;
import io.github.vampirestudios.hgm.core.network.Router;
import io.github.vampirestudios.hgm.utils.Constants;
import io.github.vampirestudios.hgm.utils.IColored;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public abstract class NetworkDeviceBlockEntity extends DeviceBlockEntity implements Tickable {
    private int counter;
    private Connection connection;

    public NetworkDeviceBlockEntity(BlockEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        if (world.isClient)
            return;

        if (connection != null) {
            if (++counter >= HuskysGadgetMod.config.routerSettings.beaconInterval * 2) {
                connection.setRouterPos(null);
                counter = 0;
            }
        }
    }

    public void connect(Router router) {
        if (router == null) {
            if (connection != null) {
                Router connectedRouter = connection.getRouter(world);
                if (connectedRouter != null) {
                    connectedRouter.removeDevice(this);
                }
            }
            connection = null;
            return;
        }
        connection = new Connection(router);
        counter = 0;
        this.markDirty();
    }

    public Connection getConnection() {
        return connection;
    }

    public Router getRouter() {
        return connection != null ? connection.getRouter(world) : null;
    }

    public boolean isConnected() {
        return connection != null && connection.isConnected();
    }

    public boolean receiveBeacon(Router router) {
        if (counter >= HuskysGadgetMod.config.routerSettings.beaconInterval * 2) {
            connect(router);
            return true;
        }
        if (connection != null && connection.getRouterId().equals(router.getId())) {
            connection.setRouterPos(router.getPos());
            counter = 0;
            return true;
        }
        return false;
    }

    public int getSignalStrength() {
        BlockPos routerPos = connection.getRouterPos();
        if (routerPos != null) {
            double distance = Math.sqrt(pos.getSquaredDistance(new Vec3i(routerPos.getX() + 0.5, routerPos.getY() + 0.5, routerPos.getZ() + 0.5)));
            double level = HuskysGadgetMod.config.routerSettings.signalRange / 3.0;
            return distance > level * 2 ? 2 : distance > level ? 1 : 0;
        }
        return -1;
    }

    @Override
    public CompoundTag toTag(CompoundTag compound) {
        super.toTag(compound);
        if (connection != null) {
            compound.put("connection", connection.toTag());
        }
        return compound;
    }

    @Override
    public void fromTag(CompoundTag compound) {
        super.fromTag(compound);
        if (compound.containsKey("connection", Constants.NBT.TAG_COMPOUND)) {
            connection = Connection.fromTag(this, compound.getCompound("connection"));
        }
    }

    public static abstract class Colored extends NetworkDeviceBlockEntity implements IColored {
        private DyeColor color = DyeColor.WHITE;

        public Colored(BlockEntityType<?> tileEntityTypeIn) {
            super(tileEntityTypeIn);
        }

        @Override
        public void fromTag(CompoundTag compound) {
            super.fromTag(compound);
            if (compound.containsKey("color", Constants.NBT.TAG_BYTE)) {
                this.color = DyeColor.byId(compound.getByte("color"));
            }
        }

        @Override
        public CompoundTag toTag(CompoundTag compound) {
            super.toTag(compound);
            compound.putByte("color", (byte) color.getId());
            return compound;
        }

        @Override
        public CompoundTag writeSyncTag() {
            CompoundTag tag = super.writeSyncTag();
            tag.putByte("color", (byte) color.getId());
            return tag;
        }

        @Override
        public final DyeColor getColor() {
            return color;
        }

        @Override
        public final void setColor(DyeColor color) {
            this.color = color;
        }
    }
}