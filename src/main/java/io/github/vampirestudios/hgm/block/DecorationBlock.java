package io.github.vampirestudios.hgm.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public abstract class DecorationBlock extends FacingBlock {

    public DecorationBlock(Material materialIn) {
        super(materialIn);
    }

    @Override
    public boolean isSimpleFullBlock(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

}
