package io.github.vampirestudios.hgm.network.task;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class MessageSyncBlock {
    private static BlockPos routerPos;

    public MessageSyncBlock(BlockPos routerPosIn) {
        routerPos = routerPosIn;
    }

    public static MessageSyncBlock decode(PacketByteBuf buf) {
        return new MessageSyncBlock(BlockPos.fromLong(buf.readLong()));
    }

    public void encode(PacketByteBuf buf) {
        buf.writeLong(routerPos.asLong());
    }

    /*public void received(Supplier<NetworkEvent.Context> contextSupplier) {
        World world = contextSupplier.get().getSender().world;
        BlockEntity tileEntity = world.getBlockEntity(routerPos);
        if (tileEntity instanceof TileEntityRouter) {
            TileEntityRouter tileEntityRouter = (TileEntityRouter) tileEntity;
            tileEntityRouter.syncDevicesToClient();
        }
    }*/
}