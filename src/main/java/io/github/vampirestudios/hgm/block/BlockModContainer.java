package io.github.vampirestudios.hgm.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public abstract class BlockModContainer extends Block {

    public BlockModContainer(Material materialIn) {
        super(Settings.of(materialIn));
    }

    public void onBroken(IWorld worldIn, BlockPos pos, BlockState state) {
        super.onBroken(worldIn, pos, state);
        worldIn.breakBlock(pos, false);
    }

}
