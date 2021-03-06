package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.block.entity.PrinterBlockEntity;
import io.github.vampirestudios.hgm.object.Bounds;
import io.github.vampirestudios.hgm.utils.CollisionHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BooleanBiFunction;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PrinterBlock extends ColoredDeviceBlock {

    private static final Bounds BODY_BOUNDS = new Bounds(5 * 0.0625, 0.0, 1 * 0.0625, 14 * 0.0625, 5 * 0.0625, 15 * 0.0625);
    private static final Box BODY_BOX_NORTH = CollisionHelper.getBlockBounds(Direction.NORTH, BODY_BOUNDS);
    private static final Box BODY_BOX_EAST = CollisionHelper.getBlockBounds(Direction.EAST, BODY_BOUNDS);
    private static final Box BODY_BOX_SOUTH = CollisionHelper.getBlockBounds(Direction.SOUTH, BODY_BOUNDS);
    private static final Box BODY_BOX_WEST = CollisionHelper.getBlockBounds(Direction.WEST, BODY_BOUNDS);
    private static final Box[] BODY_BOUNDING_BOX = {BODY_BOX_SOUTH, BODY_BOX_WEST, BODY_BOX_NORTH, BODY_BOX_EAST};

    private static final Bounds TRAY_BOUNDS = new Bounds(0.5 * 0.0625, 0.0, 3.5 * 0.0625, 5 * 0.0625, 1 * 0.0625, 12.5 * 0.0625);
    private static final Box TRAY_BOX_NORTH = CollisionHelper.getBlockBounds(Direction.NORTH, TRAY_BOUNDS);
    private static final Box TRAY_BOX_EAST = CollisionHelper.getBlockBounds(Direction.EAST, TRAY_BOUNDS);
    private static final Box TRAY_BOX_SOUTH = CollisionHelper.getBlockBounds(Direction.SOUTH, TRAY_BOUNDS);
    private static final Box TRAY_BOX_WEST = CollisionHelper.getBlockBounds(Direction.WEST, TRAY_BOUNDS);
    private static final Box[] TRAY_BOUNDING_BOX = {TRAY_BOX_SOUTH, TRAY_BOX_WEST, TRAY_BOX_NORTH, TRAY_BOX_EAST};

    private static final Bounds PAPER_BOUNDS = new Bounds(13 * 0.0625, 0.0, 4 * 0.0625, 1.0, 9 * 0.0625, 12 * 0.0625);
    private static final Box PAPER_BOX_NORTH = CollisionHelper.getBlockBounds(Direction.NORTH, PAPER_BOUNDS);
    private static final Box PAPER_BOX_EAST = CollisionHelper.getBlockBounds(Direction.EAST, PAPER_BOUNDS);
    private static final Box PAPER_BOX_SOUTH = CollisionHelper.getBlockBounds(Direction.SOUTH, PAPER_BOUNDS);
    private static final Box PAPER_BOX_WEST = CollisionHelper.getBlockBounds(Direction.WEST, PAPER_BOUNDS);
    private static final Box[] PAPER_BOUNDING_BOX = {PAPER_BOX_SOUTH, PAPER_BOX_WEST, PAPER_BOX_NORTH, PAPER_BOX_EAST};

    public PrinterBlock(DyeColor color) {
        super(color);
        this.setDefaultState(this.getStateFactory().getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        return makeShape(state);
    }

    public VoxelShape makeShape(BlockState state) {
        Direction facing = state.get(FACING);
        VoxelShape SHAPE_1 = VoxelShapes.cuboid(BODY_BOUNDING_BOX[facing.getHorizontal()]);
        VoxelShape SHAPE_2 = VoxelShapes.cuboid(TRAY_BOUNDING_BOX[facing.getHorizontal()]);
        VoxelShape SHAPE_3 = VoxelShapes.cuboid(PAPER_BOUNDING_BOX[facing.getHorizontal()]);
        VoxelShape SHAPE = VoxelShapes.combine(SHAPE_1, SHAPE_2, BooleanBiFunction.OR);
        return VoxelShapes.combine(SHAPE, SHAPE_3, BooleanBiFunction.OR);
    }

    @Override
    public boolean activate(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        if (worldIn.isClient) {
            return false;
        } else {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            ItemStack heldItem = player.getStackInHand(handIn);

            if (tileentity instanceof PrinterBlockEntity) {
                if (((PrinterBlockEntity) tileentity).addPaper(heldItem, player.isSneaking())) {
                    return true;
                }
            }

            return true;
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new PrinterBlockEntity();
    }

}