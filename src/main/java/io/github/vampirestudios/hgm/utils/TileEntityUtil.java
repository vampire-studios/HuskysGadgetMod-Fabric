package io.github.vampirestudios.hgm.utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityUtil {
    public static void markBlockForUpdate(World world, BlockPos pos) {
        world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    }
}
