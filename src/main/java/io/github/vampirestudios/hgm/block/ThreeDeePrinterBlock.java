package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.block.entity.ThreeDeePrinterBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ThreeDeePrinterBlock extends ColoredDeviceBlock {

    public ThreeDeePrinterBlock(DyeColor color) {
        super(color);
    }

    @Override
    public boolean isTranslucent(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        return false;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView var1) {
        return new ThreeDeePrinterBlockEntity();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext blockItemUseContext) {
        BlockState state = super.getPlacementState(blockItemUseContext);
        return state.with(FACING, blockItemUseContext.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateFactory$Builder_1) {
        stateFactory$Builder_1.add(FACING);
    }

}