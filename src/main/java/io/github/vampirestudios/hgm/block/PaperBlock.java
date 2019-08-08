package io.github.vampirestudios.hgm.block;

import io.github.vampirestudios.hgm.api.print.IPrint;
import io.github.vampirestudios.hgm.block.entity.PaperBlockEntity;
import io.github.vampirestudios.hgm.object.Bounds;
import io.github.vampirestudios.hgm.utils.CollisionHelper;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PaperBlock extends FacingBlock {

    private static final Bounds SELECTION_BOUNDS = new Bounds(15 * 0.0625, 0.0, 0.0, 16 * 0.0625, 16 * 0.0625, 16 * 0.0625);
    private static final Box SELECTION_BOX_NORTH = CollisionHelper.getBlockBounds(Direction.NORTH, SELECTION_BOUNDS);
    private static final Box SELECTION_BOX_EAST = CollisionHelper.getBlockBounds(Direction.EAST, SELECTION_BOUNDS);
    private static final Box SELECTION_BOX_SOUTH = CollisionHelper.getBlockBounds(Direction.SOUTH, SELECTION_BOUNDS);
    private static final Box SELECTION_BOX_WEST = CollisionHelper.getBlockBounds(Direction.WEST, SELECTION_BOUNDS);
    private static final Box[] SELECTION_BOUNDING_BOX = {SELECTION_BOX_SOUTH, SELECTION_BOX_WEST, SELECTION_BOX_NORTH, SELECTION_BOX_EAST};

    public PaperBlock() {
        super(Material.WOOL);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getRayTraceShape(BlockState state, BlockView worldIn, BlockPos pos) {
        return VoxelShapes.cuboid(SELECTION_BOUNDING_BOX[state.get(FACING).getHorizontal()]);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState state = super.getPlacementState(context);
        return state.with(FACING, context.getSide().getAxis() == Direction.Axis.Y ? context.getPlayer().getHorizontalFacing() : context.getSide().getOpposite());
    }

    @Override
    public boolean activate(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        if (!worldIn.isClient) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof PaperBlockEntity) {
                PaperBlockEntity paper = (PaperBlockEntity) tileEntity;
                paper.nextRotation();
            }
        }
        return true;
    }

    @Override
    public void onBreak(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof PaperBlockEntity) {
            PaperBlockEntity paper = (PaperBlockEntity) tileEntity;
            ItemStack drop = IPrint.generateItem(paper.getPrint());
            worldIn.spawnEntity(new ItemEntity(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop));
        }
        super.onBreak(worldIn, pos, state, player);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new PaperBlockEntity();
    }

}
