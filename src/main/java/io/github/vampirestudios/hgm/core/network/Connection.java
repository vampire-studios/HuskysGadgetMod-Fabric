package io.github.vampirestudios.hgm.core.network;

import io.github.vampirestudios.hgm.block.entity.TileEntityDevice;
import io.github.vampirestudios.hgm.block.entity.TileEntityRouter;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public class Connection {
    private UUID routerId;
    private BlockPos routerPos;

    private Connection() {
    }

    public Connection(Router router) {
        this.routerId = router.getId();
        this.routerPos = router.getPos();
    }

    public static Connection fromTag(TileEntityDevice device, CompoundTag tag) {
        Connection connection = new Connection();
        connection.routerId = UUID.fromString(tag.getString("id"));

        return connection;
    }

    public UUID getRouterId() {
        return routerId;
    }

    @Nullable
    public BlockPos getRouterPos() {
        return routerPos;
    }

    public void setRouterPos(BlockPos routerPos) {
        this.routerPos = routerPos;
    }

    @Nullable
    public Router getRouter(World world) {
        if (routerPos == null)
            return null;

        BlockEntity tileEntity = world.getBlockEntity(routerPos);
        if (tileEntity instanceof TileEntityRouter) {
            TileEntityRouter router = (TileEntityRouter) tileEntity;
            if (router.getRouter().getId().equals(routerId)) {
                return router.getRouter();
            }
        }
        return null;
    }

    public boolean isConnected() {
        return routerPos != null;
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", routerId.toString());
        return tag;
    }

}