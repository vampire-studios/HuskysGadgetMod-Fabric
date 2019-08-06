package io.github.vampirestudios.hgm.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public abstract class BlockDecoration extends BlockFacing {

    public BlockDecoration(Material materialIn) {
        super(materialIn);
    }

    @Override
    public boolean isOpaque(BlockState blockState_1) {
        return false;
    }

    @Override
    public boolean isSimpleFullBlock(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

}
