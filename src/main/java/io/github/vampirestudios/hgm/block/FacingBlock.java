package io.github.vampirestudios.hgm.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public abstract class FacingBlock extends ContainerModBlock {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public FacingBlock(Material materialIn) {
        super(materialIn);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public void onBlockAdded(BlockState thisState, World world, BlockPos pos, BlockState state2, boolean idk) {
        this.setDefaultFacing(world, pos, thisState);
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.with(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, BlockState thisState) {
        if (!worldIn.isClient) {
            BlockState state = worldIn.getBlockState(pos.north());
            BlockState state1 = worldIn.getBlockState(pos.south());
            BlockState state2 = worldIn.getBlockState(pos.west());
            BlockState state3 = worldIn.getBlockState(pos.east());
            Direction enumfacing = thisState.get(FACING);
            if (enumfacing == Direction.NORTH && state.isOpaque() && !state1.isOpaque()) {
                enumfacing = Direction.SOUTH;
            } else if (enumfacing == Direction.SOUTH && state1.isOpaque() && !state.isOpaque()) {
                enumfacing = Direction.NORTH;
            } else if (enumfacing == Direction.WEST && state2.isOpaque() && !state3.isOpaque()) {
                enumfacing = Direction.EAST;
            } else if (enumfacing == Direction.EAST && state3.isOpaque() && !state2.isOpaque()) {
                enumfacing = Direction.WEST;
            }

            worldIn.setBlockState(pos, thisState.with(FACING, enumfacing), 2);
        }

    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        stateFactory$Builder_1.add(FACING);
    }

}
