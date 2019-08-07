package io.github.vampirestudios.hgm.core.network;

import io.github.vampirestudios.hgm.HuskysGadgetMod;
import io.github.vampirestudios.hgm.block.entity.NetworkDeviceBlockEntity;
import io.github.vampirestudios.hgm.utils.Constants;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Router {
    private final Map<UUID, NetworkDevice> NETWORK_DEVICES = new HashMap<>();

    private int timer;
    private UUID routerId;
    private BlockPos pos;

    public Router(BlockPos pos) {
        this.pos = pos;
    }

    public static Router fromTag(BlockPos pos, CompoundTag tag) {
        Router router = new Router(pos);
        router.routerId = tag.getUuid("id");

        ListTag deviceList = tag.getList("network_devices", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < deviceList.size(); i++) {
            NetworkDevice device = NetworkDevice.fromTag(deviceList.getCompoundTag(i));
            router.NETWORK_DEVICES.put(device.getId(), device);
        }
        return router;
    }

    public void update(World world) {
        if (++timer >= HuskysGadgetMod.config.routerSettings.beaconInterval) {
            sendBeacon(world);
            timer = 0;
        }
    }

    public boolean addDevice(UUID id, String name) {
        if (NETWORK_DEVICES.size() >= HuskysGadgetMod.config.routerSettings.maxDevices) {
            return NETWORK_DEVICES.containsKey(id);
        }
        if (!NETWORK_DEVICES.containsKey(id)) {
            NETWORK_DEVICES.put(id, new NetworkDevice(id, name, this));
        }
        timer += HuskysGadgetMod.config.routerSettings.beaconInterval;
        return true;
    }

    public boolean addDevice(NetworkDeviceBlockEntity device) {
        if (NETWORK_DEVICES.size() >= HuskysGadgetMod.config.routerSettings.maxDevices) {
            return NETWORK_DEVICES.containsKey(device.getId());
        }
        if (!NETWORK_DEVICES.containsKey(device.getId())) {
            NETWORK_DEVICES.put(device.getId(), new NetworkDevice(device, this));
        }
        return true;
    }

    public boolean hasDevice(NetworkDeviceBlockEntity device) {
        return NETWORK_DEVICES.containsKey(device.getId());
    }

    public void removeDevice(NetworkDeviceBlockEntity device) {
        NETWORK_DEVICES.remove(device.getId());
    }

    @Nullable
    public NetworkDeviceBlockEntity getDevice(World world, UUID id) {
        return NETWORK_DEVICES.containsKey(id) ? NETWORK_DEVICES.get(id).getDevice(world) : null;
    }

    public Collection<NetworkDevice> getNetworkDevices() {
        return NETWORK_DEVICES.values();
    }

    public Collection<NetworkDevice> getConnectedDevices(World world) {
        sendBeacon(world);
        return NETWORK_DEVICES.values().stream().filter(networkDevice -> networkDevice.getPos() != null).collect(Collectors.toList());
    }

    public Collection<NetworkDevice> getConnectedDevices(final World world, Class<? extends NetworkDeviceBlockEntity> type) {
        final Predicate<NetworkDevice> DEVICE_TYPE = networkDevice ->
        {
            if (networkDevice.getPos() == null)
                return false;

            BlockEntity tileEntity = world.getBlockEntity(networkDevice.getPos());
            if (tileEntity instanceof NetworkDeviceBlockEntity) {
                return tileEntity.getClass().isAssignableFrom(type);
            }
            return false;
        };
        return getConnectedDevices(world).stream().filter(DEVICE_TYPE).collect(Collectors.toList());
    }

    private void sendBeacon(World world) {
        if (world.isClient)
            return;

        NETWORK_DEVICES.forEach((id, device) -> device.setPos(null));
        int range = HuskysGadgetMod.config.routerSettings.signalRange;
        for (int y = -range; y < range + 1; y++) {
            for (int z = -range; z < range + 1; z++) {
                for (int x = -range; x < range + 1; x++) {
                    BlockPos currentPos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
                    BlockEntity tileEntity = world.getBlockEntity(currentPos);
                    if (tileEntity instanceof NetworkDeviceBlockEntity) {
                        NetworkDeviceBlockEntity TileEntityNetworkDevice = (NetworkDeviceBlockEntity) tileEntity;
                        if (!NETWORK_DEVICES.containsKey(TileEntityNetworkDevice.getId()))
                            continue;
                        if (TileEntityNetworkDevice.receiveBeacon(this)) {
                            NETWORK_DEVICES.get(TileEntityNetworkDevice.getId()).setPos(currentPos);
                        } else {
                            NETWORK_DEVICES.remove(TileEntityNetworkDevice.getId());
                        }
                    }
                }
            }
        }
    }

    public UUID getId() {
        if (routerId == null) {
            routerId = UUID.randomUUID();
        }
        return routerId;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public CompoundTag toTag(boolean includePos) {
        CompoundTag tag = new CompoundTag();
        tag.putUuid("id", getId());

        ListTag deviceList = new ListTag();
        NETWORK_DEVICES.forEach((id, device) -> deviceList.add(device.toTag(includePos)));
        tag.put("network_devices", deviceList);

        return tag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Router))
            return false;
        Router router = (Router) obj;
        return router.getId().equals(routerId);
    }
}