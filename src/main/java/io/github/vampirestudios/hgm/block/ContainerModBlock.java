package io.github.vampirestudios.hgm.block;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public abstract class ContainerModBlock extends Block implements BlockEntityProvider {

    public ContainerModBlock(Material materialIn) {
        super(Settings.of(materialIn));
    }

    public void onBroken(WorldAccess worldIn, BlockPos pos, BlockState state) {
        super.onBroken(worldIn, pos, state);
        worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
    }

}
