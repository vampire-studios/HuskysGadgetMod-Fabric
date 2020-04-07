package io.github.vampirestudios.hgm_rewrite.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class FacingBlock extends ModContainerBlock {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public FacingBlock(Settings block$Settings_1) {
        super(block$Settings_1);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public void onBlockAdded(BlockState thisState, World world, BlockPos pos, BlockState state2, boolean idk) {
        this.setDefaultFacing(world, pos, thisState);
    }

    @Override
    public void onPlaced(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1) {
        world_1.setBlockState(blockPos_1, blockState_1.with(FACING, livingEntity_1.getHorizontalFacing().getOpposite()), 2);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, BlockState thisState) {
        if (!worldIn.isClient) {
            BlockState state = worldIn.getBlockState(pos.north());
            BlockState state1 = worldIn.getBlockState(pos.south());
            BlockState state2 = worldIn.getBlockState(pos.west());
            BlockState state3 = worldIn.getBlockState(pos.east());
            Direction direction = thisState.get(FACING);
            if (direction == Direction.NORTH && state.isOpaque() && !state1.isOpaque()) {
                direction = Direction.SOUTH;
            } else if (direction == Direction.SOUTH && state1.isOpaque() && !state.isOpaque()) {
                direction = Direction.NORTH;
            } else if (direction == Direction.WEST && state2.isOpaque() && !state3.isOpaque()) {
                direction = Direction.EAST;
            } else if (direction == Direction.EAST && state3.isOpaque() && !state2.isOpaque()) {
                direction = Direction.WEST;
            }

            worldIn.setBlockState(pos, thisState.with(FACING, direction), 2);
        }

    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateFactory$Builder_1) {
        stateFactory$Builder_1.add(FACING);
    }

}
