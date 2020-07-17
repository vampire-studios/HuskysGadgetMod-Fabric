package io.github.vampirestudios.hgm.utils;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class VanillaPacketDispatcher {

    public static void dispatchTEToNearbyPlayers(BlockEntity tile) {
        BlockEntityUpdateS2CPacket packet = tile.toUpdatePacket();

        if (packet != null && tile.getWorld() instanceof ServerWorld) {
			/*PlayerChunkMapEntry chunk = ((ServerWorld) tile.getWorld()).getPlayerChunkMap().getEntry(tile.getPos().getX() >> 4, tile.getPos().getZ() >> 4);
			if(chunk != null) {
				chunk.sendPacket(packet);
			}*/
        }
    }

    public static void dispatchTEToNearbyPlayers(World world, BlockPos pos) {
        BlockEntity tile = world.getBlockEntity(pos);
        if (tile != null)
            dispatchTEToNearbyPlayers(tile);
    }

    public static float pointDistancePlane(double x1, double y1, double x2, double y2) {
        return (float) Math.hypot(x1 - x2, y1 - y2);
    }

}