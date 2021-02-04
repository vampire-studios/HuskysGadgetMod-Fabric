package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.block.entity.GamingDeskBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class GamingDeskBlock extends ColoredFacingBlock {

    public static final EnumProperty<DeskPart> PART = EnumProperty.of("part", DeskPart.class);

    protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0D, 3.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    protected static final VoxelShape LEG_1_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 3.0D, 3.0D);
    protected static final VoxelShape LEG_2_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 13.0D, 3.0D, 3.0D, 16.0D);
    protected static final VoxelShape LEG_3_SHAPE = Block.createCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 3.0D, 3.0D);
    protected static final VoxelShape LEG_4_SHAPE = Block.createCuboidShape(13.0D, 0.0D, 13.0D, 16.0D, 3.0D, 16.0D);
    protected static final VoxelShape NORTH_SHAPE = VoxelShapes.union(TOP_SHAPE, LEG_1_SHAPE, LEG_3_SHAPE);
    protected static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(TOP_SHAPE, LEG_2_SHAPE, LEG_4_SHAPE);
    protected static final VoxelShape WEST_SHAPE = VoxelShapes.union(TOP_SHAPE, LEG_1_SHAPE, LEG_2_SHAPE);
    protected static final VoxelShape EAST_SHAPE = VoxelShapes.union(TOP_SHAPE, LEG_3_SHAPE, LEG_4_SHAPE);

    private DyeColor color;

    public GamingDeskBlock(DyeColor color) {
        super(color);
        this.color = color;
        this.setDefaultState(this.getStateManager().getDefaultState().with(PART, DeskPart.RIGHT));
    }

    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        Direction direction_1 = itemPlacementContext_1.getPlayerFacing();
        BlockPos blockPos_1 = itemPlacementContext_1.getBlockPos();
        BlockPos blockPos_2 = blockPos_1.offset(direction_1);
        return itemPlacementContext_1.getWorld().getBlockState(blockPos_2).canReplace(itemPlacementContext_1) ? this.getDefaultState().with(FACING, direction_1) : null;
    }

    public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, ShapeContext entityContext_1) {
        Direction direction_1 = blockState_1.get(FACING);
        Direction direction_2 = blockState_1.get(PART) == DeskPart.LEFT ? direction_1 : direction_1;
        switch(direction_2) {
            case NORTH:
                return NORTH_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return EAST_SHAPE;
        }
    }

    public PistonBehavior getPistonBehavior(BlockState blockState_1) {
        return PistonBehavior.DESTROY;
    }

    public BlockRenderType getRenderType(BlockState blockState_1) {
        return BlockRenderType.MODEL;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> stateFactory$Builder_1) {
        stateFactory$Builder_1.add(FACING, PART);
    }

    public void onPlaced(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1) {
        super.onPlaced(world_1, blockPos_1, blockState_1, livingEntity_1, itemStack_1);
        if (!world_1.isClient) {
            BlockPos blockPos_2 = blockPos_1.offset(blockState_1.get(FACING));
            world_1.setBlockState(blockPos_2, blockState_1.with(PART, DeskPart.LEFT), 3);
            world_1.updateNeighbors(blockPos_1, Blocks.AIR);
            blockState_1.updateNeighbors(world_1, blockPos_1, 3);
        }

    }

    @Override
    public BlockEntity createBlockEntity(BlockView var1) {
        return new GamingDeskBlockEntity(this.color);
    }

    @Environment(EnvType.CLIENT)
    public long getRenderingSeed(BlockState blockState_1, BlockPos blockPos_1) {
        BlockPos blockPos_2 = blockPos_1.offset(blockState_1.get(FACING), blockState_1.get(PART) == DeskPart.LEFT ? 0 : 1);
        return MathHelper.hashCode(blockPos_2.getX(), blockPos_1.getY(), blockPos_2.getZ());
    }

    public boolean canPathfindThrough(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, NavigationType blockPlacementEnvironment_1) {
        return false;
    }

    public enum DeskPart implements StringIdentifiable {
        LEFT("left"),
        RIGHT("right");

        private final String name;

        DeskPart(String string_1) {
            this.name = string_1;
        }

        public String toString() {
            return this.name;
        }

        public String asString() {
            return this.name;
        }
    }

}